# Virtual Points - REST API in Java Spring

A REST API to manage virtual accounts and their users. It uses Spring Boot which allows to start a application much faster without difficult configuration.
Application in development.

## API

### Public API end points
These API end points are available without authentication:

/api/authentication/

You can login or register a new user account (called owner) which is used to authenticate and it is connected to a virtual points account. 

Registration of an owner
POST /api/authentication/registration
Add your email, password, forename and surname in a JSON format to your POST body. 

Login with an owner
POST /api/authentication/login
Add your email and password in a JSON format to your POST body. 


### Secured API end points
You need to authenticate with a user account (owner) as shown above. You have to add your JWT token to your header the following to the header key parameter "Authorization":
Bearer YOUR_JWT_TOKEN_HASH_STRING_XXXXXXXXXX

Id stands as a place holder for a real id. 

### Get an Account (virtual points) - GET:
/api/account/get/id 
Example: 
GET /api/account/get/2 

### Update an Account (virtual points) - PUT:
/api/account/update/id 
Example: 
PUT /api/account/get/2 
Add account fields values in a JSON format in your body. 


All API directives are in the URI: /api


## Run
If you want to start the application in an IDE, then you need to have Lombok installed in your IDE.


## Used technologies
Java 14, Spring, Spring Boot, Lombok, JWT & MySQL

## Authors

* **A. Dridi** - [a-dridi](https://github.com/a-dridi/)
