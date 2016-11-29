#### Task Tracking : [WORKSHEET2.md](https://github.ncsu.edu/rkrish11/timely-trampoline/blob/master/WORKSHEET2.md)



#### Screencasts:

Use Case 1:

```
  Success Case: 
  @vmbot create precise32 playbook <playbook url>
  
  To create a new custom AMI the user has to provide from a pre-determined base-box and a valid link 
  to the playbook and the playbook should be in the valid format. Here the order of the base-box and
  the playbook will not affect the output. 
```
Screencast: [Success Case](https://youtu.be/L_WdUOXxzV0)

```  
  Failure Case:
  @vmbot create <incorrect base image> playbook <invalid/incorrect link or playbook>
  
  Incorrect base image or playbook link or incorrect playbook format will generate error message 
  and prompt the user for the correct command to the bot.
``` 
Screencast: [Failed Case](https://youtu.be/sfvzRYmIV7s)



Use Case 2:

```
  Success Case: 
  @vmbot deploy <valid AMI-id> 
  
  To deploy an ec2-instance with default t2.micro, the user has to provide the ami-id in 
  valid format and AMI should exist in the teams AWS account.
```
Screencast: [Success case](https://youtu.be/1t5PAm7EFEo)
```  
  Failure Case:
  @vmbot deploy <anything else>
  
  Anything not matchhing the above syntax will fail and the user will be prompted with the
  correct command format. 
``` 

Screencast: [Failed](https://youtu.be/TXF5L2att_g)

Use Case 3:

```
  Success Case: 
  
  The following are the valid input formats to monitor or run specific commands on instance 
  with given IP address 
  
  @vmbot monitor <IP address> 
  -- default usage metrics like disk usage, memory usage and CPU usage 
  
  @vmbot monitor <IP address> <command1; command2; ...>
  -- runs the given commands and shows the corresponding output 
```
Screencast: [`Monitor <ip-addr>`](https://youtu.be/K_POhCsg2oM)

Screencast: [`Monitor <IP-ADDR> process`](https://youtu.be/O6esy4wQFQ8)

```  
  Failure Case:
  
  Anything that doesnot meet the success case command format will generate an error and will 
  be prompted with correct format. 
``` 
Screencast: [Failed Case](https://youtu.be/BTz3q1HPYjg)  
