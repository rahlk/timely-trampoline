#! /bin/bash

ssh -i ~/.ssh/MyKeyPair.pem -o StrictHostKeyChecking=no ubuntu@$1 $2
