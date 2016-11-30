from __future__ import print_function
import json
import re
from pdb import set_trace
from slackbot.bot import respond_to, listen_to
import urllib
import validators
import subprocess
from utils import *
from mocking_vm import mock_create, mock_vagrantfile, mock_deploy, mock_monitor, mock_health
import requests

base_boxes = {
    "precise32": "https://hashicorp-files.hashicorp.com/precise32.box",
    "precise64": "https://hashicorp-files.hashicorp.com/precise64.box",
    "trusty32": "https://hashicorp-files.hashicorp.com/trusty32.box",
    "trusty64": "https://hashicorp-files.hashicorp.com/trusty64.box"}

ami_keys = {"trusty32": "ami-fce3c696",
            "trusty64": "ami-fce3c696",
            "precise32": "ami-fce3c696",
            "precise64": "ami-fce3c696"}

communication_cache = []

created_ami = ""


@respond_to('help', re.IGNORECASE)
def help(message):
    reply_string = "Greetings developer. Here's how I can help you-" \
                   "\n\n" \
                   "1. Create a vargrant box: To do this say: create [os_name (precise32, precise64, trusty32, trusty64) ] playbook [link_to_playbook_yml]\n" \
                   "2. Deploy a vagrant box: The create command\ " \
                   " gives you an ami id, with that to deploy say: deploy [ami-id]\n" \
                   "3. Monitor a running box: On successful deploy, a public IP is generated, using this, say: monitor 54.174.121.233 [optional: process name, ex: ps]"

    message.reply(reply_string)
    pass


@listen_to('create box precise32 with pip install selenium, mkdir myproject', re.IGNORECASE)
@respond_to('create box precise32 with pip install selenium, mkdir myproject', re.IGNORECASE)
def create_box(message):
    create_config = {
        'box_name': 'precise32',
        'box_url': 'http://files.vagrantup.com/precise32.box'
    }

    if mock_create(create_config) == 'SUCCESS':
        message.reply('A vagrant box for ubuntu-precise32 has been created. '
                      'Installed python. Installed selenium. Directory myproject created. '
                      '\n\n Box now ready to be deployed.')
    else:
        message.reply('Failed to create precise32. A box with this name has already been created.')


@listen_to('Create (.*)', re.IGNORECASE)
@respond_to('Create (.*)', re.IGNORECASE)
def create_box(message, something):
    text = something.split()

    try:

        assert text[0] in ami_keys.keys()
        ami = text[0]
        urls = re.findall('http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\(\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+', something)
        if len(urls) > 1:
            message.reply("I expected 1 url. Got {} urls.".format(len(urls)))
        elif len(urls) == 0:
            message.reply("I expected 1 url. Got no url.")
        else:
            m = create_instance(ami_keys[ami], playbook=urls[0][:-1])  # Refactor this
            message.reply("Creating AMI using the playbook file.\nThis may take a while. Please wait...\n")
            stdout = m.communicate()
            created_ami = stdout[0].split('\n')[-2].split(": ")[-1]

            if len(created_ami) > 0:
                message.reply("==> Builds finished. The artifacts of successful builds are:")
                message.reply("--> amazon-ebs: AMIs were created:")
                message.reply(created_ami)
            else:
                for content in stdout: message.reply(content)

    except AssertionError:
        message.reply("I am not able to find a box called {}.\n\nDid you mean:".format(text[0]))
        message.reply("\t" + "\n".join(base_boxes.keys()))


@listen_to('Deploy (.*)', re.IGNORECASE)
@respond_to('Deploy (.*)', re.IGNORECASE)
def deploy_box(message, something):
    if "ami-" in something:
        created_ami = "ami-" + something.split("ami-")[1].split(" ")[0]
        result = deploy_instance(ami_key=created_ami)
        running_ips = result.split("}\n")[-1].split("\n")
        message.reply("An ec2 t1.mirco instance has been created with public IP {0}. "
                      "To access the instance, use - \"ssh  -i  key.pem ubuntu@{0}".format(running_ips[-2]))
    else:
        message.reply("This AMI id was incorrect. Try again with correct format - \"ami-xxxxxxxx\".")


@listen_to('Running (.*)', re.IGNORECASE)
@respond_to('Running (.*)', re.IGNORECASE)
def running_instances(message, something):
    if "ami-" in something:
        created_ami = "ami-" + something.split("ami-")[1].split(" ")[0]
        result = describe_instance(ami_key=created_ami)
        running_ips = result.split("\n")
        # message.reply("An instance with an has created with Public IP Address: " + running_ips[-2])
        message.reply("Current you have the following instances running:\n" + "\n".join(running_ips))
    else:
        message.reply("No ami key was found. Please provide an ami key.")


@listen_to('monitor(.*)', re.IGNORECASE)
@respond_to('monitor(.*)', re.IGNORECASE)
def monitor(message, something):
    arguments = something.split(" ")[1:]

    try:
        ip = arguments[0]

        if ip == arguments[-1]:
            task = None
        else:
            task = arguments[-1]

        if len(arguments) == 1:
            message.reply("No task name provided. Showing basics stats:\n")
            stat = monitor_instance(ip, task_name=None)
            message.reply(stat)
        else:
            stat = monitor_instance(ip, task_name=task)
            message.reply(stat)

    except IndexError:
        message.reply("No IP address provided. Please provide IP address.\n")
