from __future__ import print_function

from vagrant import Vagrant

v = Vagrant()


def mock_create(config):
    try:
        if config:
            v.init(box_name=config['box_name'], box_url=config['box_url'])
        else:
            v.init()

        return "SUCCESS"

    except Exception as e:
        return "FAILED"


def mock_vagrantfile():
    with open('Vagrantfile', 'r') as myfile:
        vagrantfile = myfile.read()
    return vagrantfile


def mock_deploy(config):
    with open('./src/mockdata/create_instance') as myfile:
        status_file = myfile.read()
    return status_file


def mock_monitor():
    with open('./src/mockdata/monitor') as myfile:
        status_file = myfile.read()
    return status_file


def mock_health():
    with open('./src/mockdata/health') as myfile:
        status_file = myfile.read()
    return status_file
