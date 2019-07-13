#!/bin/bash
echo "Input the stack name which you want to terminate"
read name

EC2_ID=$(aws ec2 describe-instances --query "Reservations[*].Instances[*].InstanceId[]" --filters "Name=tag-key,Values=aws:cloudformation:stack-name" "Name=tag-value,Values=$name" --output=text)
aws ec2 modify-instance-attribute --instance-id $EC2_ID --no-disable-api-termination 

aws cloudformation delete-stack --stack-name $name
aws cloudformation wait stack-delete-complete --stack-name $name

echo "Stack succesfully terminated"