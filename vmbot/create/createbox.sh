#!/bin/bash

#echo "hello abbas how is your day"
#sudo apt-get install software-properties-common
#sudo apt-add-repository ppa:ansible/ansible
#sudo apt-get update
#sudo apt-get install ansible

pwd
echo "Fetching the playbook..."
wget $2 -O packer.yml
echo "Playbook downloaded"

echo
echo "Creating the custom AMI from given playbook and base ubuntu AMI"
packer build exampl.json -var 'ami-id=$1'