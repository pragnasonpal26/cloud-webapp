echo "Input the new application stack name"
read name

echo "Input ssh key name"
read keyName

echo "Input network stack name"
read stackname

amitag='custom-centos'
vpcname=$stackname'-csye6225-vpc'
websubnetname=$stackname'-csye6225-subnet1'
dbsubnetname=$stackname'-csye6225-subnet3'
groupname='csye6225-webapps-sg'
rdsgroupname='csye6225-rds-sg'

amiID=`aws ec2 describe-tags --filters "Name=value,Values=custom-centos" --query 'Tags[0].ResourceId' --output text`
#amiID='aws ec2 describe-images --filters "Name=tag:custom,Values=custom-centos" --query 'Images[0].ImageId' --output text'
VPCID=`aws ec2 describe-tags --filters "Name=value,Values=$vpcname" --query 'Tags[0].ResourceId' --output text`
webAppSubnetID=`aws ec2 describe-tags --filters "Name=value,Values=$websubnetname" --query 'Tags[0].ResourceId' --output text`
dbSubnetID=`aws ec2 describe-tags --filters "Name=value,Values=$dbsubnetname" --query 'Tags[0].ResourceId' --output text`
GROUPID=`aws ec2 describe-tags --filters "Name=value,Values=$groupname" --query 'Tags[0].ResourceId' --output text`
#RDSGROUPID=`aws ec2 describe-tags --filters "Name=value,Values=$rdsgroupname" --query 'Tags[0].ResourceId' --output text`
InstanceType='t2.micro'
aws cloudformation create-stack --stack-name $name --capabilities CAPABILITY_NAMED_IAM --template-body file://csye6225-cf-application.json --parameters "ParameterKey=stackName,ParameterValue=$name" "ParameterKey=KeyName,ParameterValue=$keyName" "ParameterKey=ImageId,ParameterValue=$amiID" "ParameterKey=InstanceType,ParameterValue=$InstanceType" "ParameterKey=VPCID,ParameterValue=$VPCID" "ParameterKey=webAppSubnetID,ParameterValue=$webAppSubnetID" "ParameterKey=dbSubnetID,ParameterValue=$dbSubnetID" "ParameterKey=SecurityGroupID,ParameterValue=$GROUPID"