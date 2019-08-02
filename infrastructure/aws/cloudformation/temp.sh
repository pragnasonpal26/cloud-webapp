echo "Input the new application stack name"
read name

stackId=$(aws cloudformation create-stack --stack-name $name --capabilities CAPABILITY_NAMED_IAM --template-body file://temp.json --parameters "ParameterKey=stackName,ParameterValue=$name" \
"ParameterKey=LambdaBucketName,ParameterValue=$LAMBDA_BUCKET_NAME" "ParameterKey=SystemEmail,ParameterValue=$SYSTEM_EMAIL" \
--query [StackId] --output text)