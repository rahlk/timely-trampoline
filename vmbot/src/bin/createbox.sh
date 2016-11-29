#!/bin/bash

#echo "hello abbas how is your day"
#sudo apt-get install software-properties-common
#sudo apt-add-repository ppa:ansible/ansible
#sudo apt-get update
#sudo apt-get install ansible

pwd

echo "Playbook url $2"
echo "AMI ID: $1"

echo "Downloading Playbook file..."
wget $2 -O /Users/rkrsn/git/csc510/timely-trampoline/vmbot/src/bin/packer.yml

echo "Creating the custom AMI from given playbook and base ubuntu AMI"
packer build -var "ami-id=$1" /Users/rkrsn/git/csc510/timely-trampoline/vmbot/src/bin/example.json
