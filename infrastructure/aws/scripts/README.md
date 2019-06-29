# Infrastructure as Code with AWS Command Line Interface

## Creating Custom VPCs

### Prerequisites:

Install Python, pip and the AWS Command Line Interface on Linux

After the AWS CLI is installed, configured aws credentials of specific account

$ aws configure

| aws configuration                                                                                                     | 
| ---                                                                                                                   | 
| AWS Access Key ID [None]:                                                                                             | 
| AWS Secret Access Key [None]:                                                                                         | 
| Default region name [None]: us-east-1                                                                                 | 
| Default output format [None]:                                                                                         | 


### Shell Scripts:
csye6225-aws-networking-setup.sh to create and configure required networking resources using AWS CLI
csye6225-aws-networking-teardown.sh to delete networking resources using AWS CLI

Running Shell script through terminal

chmod +x csye6225-aws-networking-setup.sh --> to give execute rights

./csye6225-aws-networking-setup.sh --> to run the file

Same procedure for csye6225-aws-networking-teardown.sh
