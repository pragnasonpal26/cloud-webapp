#!/bin/bash

envVar=`cat /usr/lib/envVar.txt`

java -jar webApplication-0.0.1-SNAPSHOT.jar $envVar > /dev/null 2> /dev/null < /dev/null &