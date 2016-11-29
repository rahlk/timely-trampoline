### Use Cases


##### UC1 : Create a customized vagrant box.
```
1. Preconditions:
	The bot should be running.	

2. MainFlow:
	User chooses to create a new vagrant box from baseboxes from HashiCorp Atlas Repo or existing boxes saved in AWS S3 [S1] and provide either commands or url for zipped Ansible Playbooks[S2].

3. SubFlow:
	[S1] The user gives the box name[E1] and command[E2] or url[E3]
	[S2] The user gives url endpoint of box saved in AWS S3[E4] and gives either command[E2] or url[E3]

4. Alternate Flows:
	[E1] The boxname does not exist and user will be prompted again.
	[E2] The command has wrong syntax , user will prompted again with right syntax format.
	[E3] The ansible playbook has syntax error or does not exist.
	[E4] The url endpoint has wrong format or does not exist. User will be prompted again.

```

##### UC2 : Deploy a customized vagrant box to AWS.
```
1. Preconditions:
	The bot should be running.
	User should be a part AWS organization account.
	User should have permissions to deploy.

2. MainFlow:
	User will deploy to default[S1] AWS EC2 instance[S1] using an existing base box url/id and password or specific AWS EC2 instance type[S2]

3. SubFlow:
	[S1] The user deploys to default instance by providing box url[E1] generated from UC1 and password[E2] as in the following command "deploy --box <box url> --pass abc123"
	[S2] The user deploys to specific type by providing type[E3] with default RAM and harddisk size along with bix url/is and password[E2] as in "deploy --box <box url> --type c1.large --pass abc123"

4. Alternate Flows:
	[E1] The url is malformed or box does not exist, user will prompted with error message.
	[E2] The password is wrong or user is not permitted, user will be prompted with error message.
	[E3] Given type does not exist , user will be prompted to try again with correct type or go with default type.
```

##### UC3: Check the server process running on given AWS EC2 instance
```
1. Preconditions:
	User should be a part AWS organization account.
	User should have permissions to access.
	User should know the Public IP address or domain url.

2. MainFlow:
	User will look at the status of CPU, RAM ,DISK usage[S1] or a particular process[S2].

3. SubFlow:
	[S1] User will provide the "status -h" command look for general server metrics.
	[S2] User will look for specific process status by providing keyword[E1] as "status -p <keyword>"

4. Alternate Flows:
	[E1] No such process relates to given keyword and user will be prompted "Try another keyword". User will then try with another keyword.

```

### Mocking

##### 1. UC1: Create a customized vagrant box.

- Do vagrant init locally to generate a Vagrantfile. 
- Publish it on slack on request.

``` python
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
```

##### UC2 : Deploy a customized vagrant box to AWS.
- This was mocked by mannually running vagrant.up() on the localhost and saving to `stdout` to `mockdata/deploy`.
- The `mock_deploy()` function merely reads the mock data and prints it on slack.

``` python
def mock_deploy(config):
    with open('./src/mockdata/deploy') as myfile:
        status_file = myfile.read()
    return status_file
```

#### UC3: Check the server process running on given AWS EC2 instance
- Again, we used mock data to query the localhost about the processes running/health (using `top`)
- The `mock_monitor()` or `mock_health()` functions merely read the mock data and print it on slack.

``` python
def mock_monitor():
    with open('./src/mockdata/monitor') as myfile:
        status_file = myfile.read()
    return status_file


def mock_health():
    with open('./src/mockdata/health') as myfile:
        status_file = myfile.read()
    return status_file
```

#### Note:

You'll find the mock data [here](https://github.ncsu.edu/rkrish11/timely-trampoline/tree/master/vmbot/src/mockdata)


### Bot Implemention

**Bot Platform:** 

In this milestone we implemented hooks into our platform (Slack). As a part of this milestone we do have a fully operational bot within your platform (Slack). It is capable of responding to basic commands. Please see [Screencasts]() for usage example.

To design our BOT we used the following sources:

1. [SlackClient](https://github.com/slackhq/python-slackclient)
2. [SlackBot](https://github.com/lins05/slackbot)
3. [EmojiBot](https://github.com/owocki/emojibot)
4. [Vagrant command with Argparse](https://docs.python.org/2/library/argparse.html#module-argparse)
5. A customized version of [Python-Vagrant](https://github.com/todddeluca/python-vagrant)

We integrated our BOT to a new Slack channel we created called [serverbot-project.slack.com](serverbot-project.slack.com).

**Bot Integration:**

As for implementataion, we enabled basic conversation/interaction with bot. We have provided a support for a user to have a conversation with an bot as defined by our use cases. 

The implentation made use of decorators offered by SlackBot python library. As a contrived example consider the interaction for use case 1. 

Here the BOT user uses our BOT 'vmbot' to create an instance of a precise32 operating system and install selenium on it. Also, he asks that a directory also be created. Note that this conversation happens as a natural conversation. A code snippet that can handle this follows:

``` python
@listen_to('create box precise32 with pip install selenium, mkdir myproject', re.IGNORECASE)
@respond_to('create box precise32 with pip install selenium, mkdir myproject', re.IGNORECASE)
def create_box(message):
    create_config = {
        'box_name': 'precise32',
        'box_url': 'http://files.vagrantup.com/precise32.box'
    }

    try:
        assert mock_create(create_config) == 'SUCCESS'
        message.reply('A vagrant box for ubuntu-precise32 has been created. '
                      'Installed python. Installed selenium. Directory myproject created. '
                      '\n\n Box now ready to be deployed.')
    except (OSError, AssertionError) as E:
        message.reply('Failed to create precise32. A box with this name has already been created.')

```
In keeping with the requirements of this milestone, we have mocked the behaviour of creating a virtual image for precise32. Therefore the `mock_create(config)` function. The complete code is availabe in [bot.py](https://github.ncsu.edu/rkrish11/timely-trampoline/blob/master/vmbot/src/bot.py)

###Selenium Testing

Selenium is primarily used to automate browsers. We harness this automation utility of Selenium to automate test cases. For each of the use case, we have written a test case, which shows one "happy path" and one "alternative path". The test cases are described as follows:

**1. Help:**
When the user addresses the bot, "@vmbot help", then the bot generates a list of actions it can perform.
Test case function: aInputVerify()

**2. Invalid operations:**
When the user gives any command which is unknown to the bot, it generates a message "Sorry but I didn't understand you."


**3. UC1:**
When the user gives all correct parameters for the command "Create box" then the box is created with the message "A vagrant box for ubuntu-precise32 has been created.". Otherwise a failure message "Failed to create precise32. A box with this name has already been created." is generated.
Test case function: bCreateBox()

**4. UC2:**
When the user gives all correct parameters for the command "Deploy" then the box is deployed with the status "Deployment Status". Otherwise a failure message "Failed to deploy precise32." is generated.
Test case function: cDeploy()

**5. UC3:**
When the user gives all correct parameters for the command "Status process python" then the box return with the "Process Status Python". Otherwise a failure message "Failed to identify process python" is generated.
Test case function: dStatusProcesses()

**6. UC4:**
When the user gives all correct parameters for the command "Status health" then the box return with the Precise32 Health information. Otherwise a failure message "Failed to obtain health." is generated.
Test case function: eHealth()

![example](https://github.ncsu.edu/rkrish11/timely-trampoline/blob/master/Screencasts/testScreenCast.gif)

### Use case screencasts

**1. UC1**

![screencast_use_case_1](https://media.github.ncsu.edu/user/3656/files/90c843f4-9708-11e6-9735-e2a7d8bcd524)

**2. UC2**

![screencast_2](https://media.github.ncsu.edu/user/3656/files/01e64518-9709-11e6-997a-5b0859ee539f)

**3. UC3**

![screencast_3](https://media.github.ncsu.edu/user/3656/files/2a0cc350-9709-11e6-9e96-ed93e44f538c)

**4. UC4**

![screencast_4](https://media.github.ncsu.edu/user/3656/files/5692413e-9709-11e6-9cf9-690a1dc80e61)



###Travis CI + SauceLabs

The tests used for TravisCI + SauceLabs are in Selenium Webdriver Python bindings. Code and TravisCi+SauceLabs integration is hosted on my personal github account (https://github.com/abzter10690/SlackBotTesting)

Screencast on build and testruns:

![TravisCI+Saucelabs](https://github.ncsu.edu/rkrish11/timely-trampoline/blob/master/Screencasts/travis.gif)
