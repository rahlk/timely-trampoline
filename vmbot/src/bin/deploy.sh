#!/bin/bash

aws ec2 run-instances --image-id $1 --count 1 --instance-type t2.micro --key-name ncj --security-groups ncjamaat-sec-group
aws ec2 describe-instances
aws ec2 describe-instances --filters "Name=instance-type,Values=t2.micro" "Name=image-id,Values=$1" --query "Reservations[*].Instances[*].PublicIpAddress"  --output=text


