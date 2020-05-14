This is small POC to generate JWT token to authorize a authenticated user.
So far service1 and service2 module are not in use here.

We will use springeureka module to start Discovery server and then run the proxy-service module
to sign-up a user and later login with the credentials and then authorize with JWT token.

It uses spring security to implement JWT token

Steps to execute:(There is now UI for now,we will access our service through CURL command)

1. Run EurekaServerApplication.java and then AuthorizeWithJWT.java
2. Try to run below command to check
    
    # HTTP 403 Forbidden status is expected (No JWT Token)
    curl http://localhost:8081/users/message
    
    # registers a new user
    curl -H "Content-Type: application/json" -X POST -d '{
        "username": "admin4",
        "password": "password4"
    }' http://localhost:8081/users/sign-up
    
    # logs into the application (JWT is generated)
    curl -i -H "Content-Type: application/json" -X POST -d '{
        "username": "admin4",
        "password": "password4"
    }' http://localhost:8081/login

    # replace ${JWT} with the JWT retrieved above
    curl -H "Authorization: Bearer ${JWT}" http://localhost:8081/users/message
    
    
    
