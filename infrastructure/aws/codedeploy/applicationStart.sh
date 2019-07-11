#!/bin/bash

sudo systemctl start tomcat.service
cd /opt/tomcat/webapps
java -jar webApplication-0.0.1-SNAPSHOT.jar \
--regionName=us-east-1 --bucketName=csye6225-su19-haridasb-me-csye6225-com \
--spring.profile.active=prod \
--spring.datasource.url=jdbc:mysql://csye6225-su19.cys6ezwflabj.us-east-1.rds.amazonaws.com:3306/csye6225?serverTimezone=UTC&createDatabaseIfNotExist=true