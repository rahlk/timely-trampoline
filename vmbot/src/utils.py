from __future__ import print_function

import os
import subprocess
import sys
from pdb import set_trace

root = os.path.join(os.getcwd().split('src')[0], 'src')
# bin = os.path.join(os.getcwd().split('src')[0], 'bin')

if root not in sys.path:
    sys.path.append(root)


def create_instance(ami_key, playbook):
    cmd = [os.path.join(root, "bin", "createbox.sh"), ami_key, playbook]
    try:
        return subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    except:
        set_trace()


def deploy_instance(ami_key):
    cmd = [os.path.join(root, "bin", "deploy.sh"), ami_key]
    output = subprocess.check_output(cmd)
    return output


def describe_instance(ami_key):
    cmd = [os.path.join(root, "bin", "describe.sh"), ami_key]
    try:
        output = subprocess.check_output(cmd)
        return output
    except:
        set_trace()


def monitor_instance(ip_addr, task_name):
    if task_name is None:
        cmd = [os.path.join(root, "bin", "monitor_basic.sh"), ip_addr]
    else:
        cmd = [os.path.join(root, "bin", "monitor.sh"), ip_addr, task_name]

    try:
        output = subprocess.check_output(cmd)
        return output
    except:
        set_trace()


def _deploy_popen(ami_key, playbook):
    # set_trace()
    cmd = [os.path.join(root, "bin", "createbox.sh"), ami_key, playbook]  # , json_file]
    p = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    print(p.communicate())
    set_trace()


def __test_Popen():
    ami_key = "ami-fce3c696"
    playbook = "https://goo.gl/VjJumx"
    _deploy_popen(ami_key, playbook)
    return


def __test_deploy():
    ami_key = "ami-fce3c696"
    playbook = "https://goo.gl/VjJumx"
    create_instance(ami_key, playbook)
    return


if __name__ == "__main__":
    # __test_deploy()
    describe_instance("ami-867f5391")
    # __test_Popen()
