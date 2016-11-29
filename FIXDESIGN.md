###Problem Statement

Developers always encounter an issue where the members of the team face an issue due to the environment, configurations and dependencies. One solution to this problem is Vagrant. Vagrant will isolate dependencies and their configuration within a single disposable, consistent environment, without sacrificing any of the tools you are used to working with (editors, browsers, debuggers, etc.).

Again, for provisioning VM for a given environment ,there are several softwares/packages that need to be added. If may happen that the same configuration with minute changes needs to be used for the development of another project. When such a workflow is driven by using existing vagrant boxes, in a centrailized community driven way will dilute developer headache and increase productivity. What if this can be done using a Mobile device or Tablet PC? An enterprise social network like Slack is a perfect tool for this .Reusing an existing Vagrant box or Ansible playbook in a centralized manner can thus be acieved using a SlackBot. 
    

To this end, we plan on create a bot that communicates with a user to spin up a vagrant box image as per her/his specification.If the user requires specific packages to be installed, the bot creates an Ansible Playbook, and uses vagrant-anisble provisioner to run it on the image. This box can then be deployed either on a localhost, on a specific host given it’s IP address, or on a cloud platform like AWS.


###Scoped Bot Tasks/Use Cases

Prominent use-cases of this slackbot are:

1. The user may create a customized vagrant box by passing specific messages to BOT via Slack.
2. The user may ask the BOT to deploy an existing image (either on a localhost, or on the cloud).
3. Once deployed, the BOT then keeps track of the IP addresses. The user can then interact with the running instances of vm to: 
(a) Monitor the running OS
(b) Request specific tasks be run on the OSes.

Why create this slackbot ?

- The BOT can be called from either a @channel or as a private interaction with @user.
- When deployed from a @channel, all other members in the channel will have access to the created *.box image, or to the running instance of the OS.
- Anyone with access privileges can interact with the BOT to perform tasks like look at error logs and so on.
- Lastly, this interaction can be made on any device which runs slack.


###Streamlined Interaction

The interaction with the bot will be as minimal as possible.

#####To create a VM with OS: Ubuntu 12.04, 32 bits and run commands
```
**ahussai4** 3.42PM
@vagabot create box --basebox hashicorp/precise32 --command "pip install selenium; mkdir myproject;"

**vagabot** 3.42PM
Fetched the required box from hashicorp Atlas.
Running "pip install selenium"
Done.
Running "mkdir myproject"
Done.
```

#####Helping the user with tasks he can do.
```
**ahussai4** 1.55PM
@vagabot help


**vagabot** 1.56PM
Happy to help!

You can do the following:
- Create a customized vagrant box
		type **@vagabot create help** for more info
- Delpoy an vagrant box image to AWS
		type **@vagabot deploy help** for more info
- Check status
		type **@vagabot status help** for more info
Need something more
		type **@vagabot more**

**ahussai4** 1.55PM
@vagabot more


**vagabot** 1.56PM
- List archived boxes
		type **@vagabot listbox help** for more info
- List playbooks
		type **@vagabot listbooks help** for more info
```

#####Deploying to AWS using existing box , takes default t1.micro instance
```
**ahussai4** 1.58PM
@vagabot deploy box --boxid  845785 --pass abcd1234

**vagabot** 1.59PM
Started AWS instance with default launch config on Public IP: 45.78.123.221
Status : Pending
 
```

###Architecture 

![image](https://github.ncsu.edu/rkrish11/timely-trampoline/blob/master/Images/Architecture_Trampoline.jpg)


###Design Pattern :
	
The design pattern that we will be using will be hybrid i.e. a mixture of Singleton Design pattern and Conversationists.
	
* Singleton Design Pattern: is used as we cannot have more than one instances of the bot because if we do then on querying the bot,all the instances will respond to it.This is highly undesirable for this application as it may lead to some race conditions. Hence with the help of this design pattern we can assure that we won't be creating multiple instances of the bot. Again this will also help the bot know the conversational state.
	
* Conversationist: is used because the bot will be reacting to all the messages addressed to it. The bot will know who it is exactly talking to. The bot will be learning from the conversations done before (for example in the if the vagrant box of a particular operating system is already created and added then if it is asked again to do the same the bot will already know and will not create it). The bot will know when it is being addressed and will respond to it. 

Thus the bot will have access to the conversations had before and will respond to the commands and perform the necessary activities.
