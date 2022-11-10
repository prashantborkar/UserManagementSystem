# UserManagementSystem

In User Management System, one is often interested in which user can add their information and manages in the System. To make it easier for those who work with this, Created a small Web-API in Springboot Application where User can Add, Update, Delete, GET their details.



-----------------> Technical Specification <----------------- 

- Language: Java 8+
- Framework: SpringBoot 
- Web-API: REST Architecture
- API Documentation: Swagger
- Maven Project
- Postman Tool



-----------------> Steps to RUN the application <----------------- 

1. Import Project in any IDE and Build Maven as below command.

2. Command:  “mvn clean install“

3. After building the application you can deploy jar or make change accordingly e.g., war file for server.

4. Or from IDE to run app select the SpringBootApplication Main file and Run.

5. From local system the URL by default is http://localhost:9999/swagger-ui.html#/
   i.e. You can modify the port in the application.properties file.

6. The SpringBootApplication is Running so from Command prompt also you can run the below command:

   CREATE USER: curl -X POST "http://localhost:9999/users" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"dateOfBirth\": \"1997-10-10\", \"emailAddress\": \"sheck@gmail.com\", \"fullName\": \"SHECK J\", \"personalNumber\": 9710100174}"

7. Apart from command prompt you can use Postman Tool and Swagger-UI I have implemented in the Swagger configuration.

   Swagger url: http://localhost:9999/swagger-ui.html#

For More Information please find the Swagger Technical Documentation and email me any concerns.

You can see the attached snapshot for the reference.

Thanks for your time to visit on my GitHub Repository.
