echo "Input the Auto-Scaling stack name"
read name

echo "Input network stack name"
read stackname

echo "Enter your keypair"
read keyName

echo "Enter your CodeDeploy bucket name "
read BUCKET_NAME

echo "Enter your Image bucket name "
read IMG_BUCKET_NAME

echo "Enter your Lambda Revision bucket name "
read LAMBDA_BUCKET_NAME

echo "Enter your domain name "
read DomainName

amitag='custom-centos'
vpcname=$stackname'-csye6225-vpc'
websubnetname=$stackname'-csye6225-subnet1'
dbsubnetname=$stackname'-csye6225-subnet3'
rdsgroupname='csye6225-rds-sg'


amiID=$(aws ec2 describe-images --owners self --query 'sort_by(Images, &CreationDate)[-1].ImageId' --output text)
echo "AMI ID:${amiID}"

hostedzoneid=$(aws route53 list-hosted-zones --query HostedZones[0].Id --output=text | awk -F '/' '{ print $3 }')
echo "Hosted zone id: $hostedzoneid"

hostedzonename=$(aws route53 list-hosted-zones --query "HostedZones[0].Name" --output text)
echo "Hosted zone name: $hostedzonename"


AccId=$(aws sts get-caller-identity --output text --query 'Account')
echo "AWS AccountId: $AccId"

amiID=`aws ec2 describe-images --filters "Name=tag:custom,Values=custom-centos" --query 'Images[0].ImageId' --output text`
#amiID='aws ec2 describe-images --filters "Name=tag:custom,Values=custom-centos" --query 'Images[0].ImageId' --output text'
VPCID=`aws ec2 describe-tags --filters "Name=value,Values=$vpcname" --query 'Tags[0].ResourceId' --output text`
webAppSubnetID=`aws ec2 describe-tags --filters "Name=value,Values=$websubnetname" --query 'Tags[0].ResourceId' --output text`
dbSubnetID=`aws ec2 describe-tags --filters "Name=value,Values=$dbsubnetname" --query 'Tags[0].ResourceId' --output text`
#RDSGROUPID=`aws ec2 describe-tags --filters "Name=value,Values=$rdsgroupname" --query 'Tags[0].ResourceId' --output text`
InstanceType='t2.micro'

SSL_Cert=$(aws acm list-certificates --query CertificateSummaryList[0].CertificateArn --output text)

stackId=$(aws cloudformation create-stack --stack-name $name --capabilities CAPABILITY_NAMED_IAM --template-body file://csye6225-cf-auto-scaling-application.json --parameters "ParameterKey=stackName,ParameterValue=$name" \
"ParameterKey=KeyName,ParameterValue=$keyName" "ParameterKey=ImageId,ParameterValue=$amiID" \
"ParameterKey=InstanceType,ParameterValue=$InstanceType" "ParameterKey=VPCID,ParameterValue=$VPCID" \
"ParameterKey=webAppSubnetID,ParameterValue=$webAppSubnetID" "ParameterKey=dbSubnetID,ParameterValue=$dbSubnetID" \
"ParameterKey=BucketName,ParameterValue=$BUCKET_NAME" \
"ParameterKey=AccId,ParameterValue=$AccId" "ParameterKey=Img,ParameterValue=$IMG_BUCKET_NAME" \
"ParameterKey=LambdaBucketName,ParameterValue=$LAMBDA_BUCKET_NAME" "ParameterKey=DomainName,ParameterValue=$DomainName" \
"ParameterKey=HostedZoneID,ParameterValue=$hostedzoneid" "ParameterKey=hostedzonename,ParameterValue=$hostedzonename" \
"ParameterKey=SslCert,ParameterValue=$SSL_Cert" \
--query [StackId] --output text)

echo "Stack Id - "
echo $stackId

if [ -z $stackId ]; then
    echo 'Error occurred.TERMINATED'
else
    aws cloudformation wait stack-create-complete --stack-name $stackId
    echo "Stack Creation Complete"
fi
