#!/bin/bash

aws ec2 describe-instances --filters "Name=instance-type,Values=t2.micro" "Name=image-id,Values=$1" --query "Reservations[*].Instances[*].PublicIpAddress"  --output=text


