# Infrastructure as Code with AWS Cloud Formation

## Creating Custom VPCs

### Prerequisites:

Install Python, pip, and the AWS Command Line Interface on Linux

After the AWS CLI is installed, configured aws credentials of specific account

$ aws configure

| aws configuration                                                                                                     | 
| ---                                                                                                                   | 
| AWS Access Key ID [None]:                                                                                             | 
| AWS Secret Access Key [None]:                                                                                         | 
| Default region name [None]: us-east-1                                                                                 | 
| Default output format [None]:                                                                                         | 

### Cloud Formation template:
csye6225-cf-networking.json defines the template for custom VPC and othe networking components.

### Shell Scripts:
csye6225-aws-cf-create-stack.sh to create and configure required networking resources using AWS CLI
csye6225-aws-cf-terminate-stack.sh to delete networking resources using AWS CLI

### Creating a stack

Step 1 : chmod +x csye6225-aws-cf-create-stack.sh        --> to give execute rights

Step 2 : sh csye6225-aws-cf-create-stack.sh              --> to run the file
         Input the stack name which you want to create   --> enter a stack name of your choice.
         {   
             "StackId": "XXXX"                           --> Output                                      
         }
Step 3 : Verify succesful creation by navigating to https://console.aws.amazon.com/cloudformation/


### Creating a stack

Step 1 : chmod +x csye6225-aws-cf-terminate-stack.sh        --> to give execute rights

Step 2 : sh csye6225-aws-cf-terminate-stack.sh              --> to run the file
         Input the stack name which you want to create      --> enter a stack name of your choice.
         
Step 3 : Verify succesful deletion by navigating to https://console.aws.amazon.com/cloudformation/