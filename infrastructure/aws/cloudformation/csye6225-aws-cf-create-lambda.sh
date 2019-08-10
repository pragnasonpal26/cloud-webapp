#!/usr/bin/env bash

echo "Write the stack-name"
read Stack_Name

echo "Enter your Lambda Revision bucket name "
read LambdaBucketName

function=resetPassword
domainname=$(aws route53 list-hosted-zones --query 'HostedZones[0].Name' --output text)
domain_name=${domainname%.}
WebAppBucket=$(aws s3api list-buckets | jq -r '.Buckets[] | select(.Name | startswith("csye6225")).Name')
lambdasnsexecution=$(aws iam get-role --role-name lambdasnsexecution --query Role.Arn --output text)


echo "Creating stack..."
STACK_ID=$(aws cloudformation create-stack \
  --stack-name ${Stack_Name} \
  --template-body file://csye6225-aws-cf-create-lambda.json \
  --parameters ParameterKey="LambdaBucketName",ParameterValue=${LambdaBucketName} ParameterKey="domainName",ParameterValue=${domain_name} ParameterKey="functionName",ParameterValue=${function} ParameterKey="lambdasnsexecution",ParameterValue=${lambdasnsexecution} \
  --capabilities CAPABILITY_NAMED_IAM --capabilities CAPABILITY_IAM | jq -r .StackId \
  )
echo $STACK_ID
echo "Waiting on ${STACK_ID} create completion..."
aws cloudformation wait stack-create-complete --stack-name ${Stack_Name}
aws cloudformation describe-stacks --stack-name ${Stack_Name} | jq .Stacks[0].Outputs
