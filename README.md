# awstool
connect to the aws ec2 service via client app

1)copy folder .aws to $user.home/, if Windows OS, it will be c:/user/home/.aws.

And configure the file credentials, change the value from AWS with ypur own KEY

aws_access_key_id=xxxxx
aws_secret_access_key=xxxxx

Configure the file config, change the value with your own 

region = ap-southeast-1


2) install SBT, and download it from http://www.scala-sbt.org/
Add sbt to $PATH.

3) in the project folder, run sbt

in sbt console, execute the command "run"

or "run-main DescribeInstance"

If any exception, please check the configure in $user.home/.aws/ (credential or configure)
Try to do in 1)

3)If I run the app, it will output

[info] Running DescribeInstance
You have access to 2 Availability Zones.
ap-southeast-1a
ap-southeast-1b
You have instances: 1
i-3bc662f7      terminated
[success] Total time: 23 s, completed 2015-1-21 18:01:05