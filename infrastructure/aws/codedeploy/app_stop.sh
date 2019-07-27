#!/bin/bash


if apid=$(pgrep -f webApplication-0.0.1-SNAPSHOT.jar)
then
    cd /tmp
    sudo rm webApplication-0.0.1-SNAPSHOT.jar
    ps -ef | grep webApplication-0.0.1-SNAPSHOT | grep -v grep | awk '{print $2}' | xargs kill
fi

#sudo rm -rf /opt/tomcat/webapps/docs  /opt/tomcat/webapps/examples /opt/tomcat/webapps/host-manager  /opt/tomcat/webapps/manager /opt/tomcat/webapps/webApplication-0.0.1-SNAPSHOT.jar