#!/bin/bash

aws ec2 run-instances --image-id $1 --count 1 --instance-type t2.micro --key-name MyKeyPair
aws ec2 describe-instances
sleep 1m
aws ec2 describe-instances --filters "Name=instance-type,Values=t2.micro" "Name=image-id,Values=$1" --query "Reservations[*].Instances[*].PublicIpAddress"  --output=text
