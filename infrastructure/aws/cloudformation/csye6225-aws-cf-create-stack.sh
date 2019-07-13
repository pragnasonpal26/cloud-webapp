echo "Input the stack name which you want to create"
read name
stackId=$(aws cloudformation create-stack --stack-name $name --template-body \
file://csye6225-cf-networking.json --parameters "ParameterKey=stackName,ParameterValue=$name" \
--query [StackId] --output text)

echo "Stack Id - "
echo $stackId

if [ -z $stackId ]; then
    echo 'Error occurred.TERMINATED'
else
    aws cloudformation wait stack-create-complete --stack-name $stackId
    echo "Stack Creation Complete"
fi

