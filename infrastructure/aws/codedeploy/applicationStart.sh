#!/bin/bash

#sudo systemctl start tomcat.service
cd /opt/tomcat/webapps
userdata=$(curl http://169.254.169.254/latest/user-data)

java -jar webApplication-0.0.1-SNAPSHOT.jar $userdata > /dev/null 2> /dev/null < /dev/null &