#!/bin/bash
echo "Input the stack name which you want to terminate"
read name
aws cloudformation delete-stack --stack-name $name