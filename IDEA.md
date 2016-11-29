# Ideas...

A Slack BOT that lets a user (a newbie) create, manage and seemlessly interact with a virtual development environment. This bot allows the user to do anything from create a simple vm and talk to it to shipping docker containers in a main Vagrant image and drive some of the customization and upgrade with Ansible. All through slack. 

### Challenge

As a new developer who's not used to any form of cloud computations. Learning all the tools/technologies that are available to him and putting it to good use requires scaling a steep learning curve. 

 - Should I use vagrant or docker?
 - Manage multiple instances/configurations — Ansible!
 - VirtualBox, VMware, AWS, or Digital Ocean?
 
A SlackBOT that can do everything enables rapid deployment.

### A (rough) draft of the idea:
 - A server bot for slack. A bot that let's newbs use vagrant/docker/ansible from a slack channel.
 - Users can:
   + Create, configure, communicate, and interact with an instance of a VM all through a slack. Example:
      ![image](https://media.github.ncsu.edu/user/3656/files/e82a8bda-922a-11e6-9bd2-a95c7a9f9777)
      ![image](https://media.github.ncsu.edu/user/3656/files/0a1cad86-922b-11e6-9aeb-95a138f9ee64)
 - Interactive debugging running instances via slack. For instance, we can talk to the BOT to find:
   - What is running?
   - Whether the issue is related to IO/hardware/networking or configuration.
   - Whether there's a pattern you recognize: for example a bad use of the DB indexes, or too many apache workers?
 - Use portforwarding to pipe outputs from running "boxes"
 - When deployed on the cloud, like digital ocean, manage your cloud app with your teams via slack. 
 - Invite members and set access permissions.

## Link Dumps
- [On Vagrant, Docker, and Ansible](http://devo.ps/blog/vagrant-docker-and-ansible-wtf/)
- [On Debugging](http://devo.ps/blog/troubleshooting-5minutes-on-a-yet-unknown-box/)
- [A Sample SlackBOT for Ansible]()
- [SSH via slack](https://github.com/guillaumewuip/ssh-slack-bot)
- [Slash commands](https://api.slack.com/slash-commands)
- [How to write a SlackBOT in python](https://www.fullstackpython.com/blog/build-first-slack-bot-python.html)
