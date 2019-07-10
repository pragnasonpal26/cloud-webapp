echo "Input the stack name which you want to create"
read name
aws cloudformation create-stack --stack-name $name --template-body file://csye6225-cf-networking.json --parameters "ParameterKey=stackName,ParameterValue=$name"



