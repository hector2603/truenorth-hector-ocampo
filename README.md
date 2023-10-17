# Calculator App - Backend

This is the backend portion of the Calculator App, built with Java and Spring Boot. The primary purpose of this backend application is to handle user authentication, perform the actual calculations for the calculator operations.

## Getting Started

To run the backend server locally, follow these steps:

1. Clone the repository
2. Open the project in your IDE of choice
3. Configure your environment variables, including database connection details and JWT secret, in the application properties file application.properties.
4. Run the application
5. The application will be running on port 8080
6. open http://localhost:8080/swagger-ui/index.html in your browser to view the Swagger UI

## API Endpoints

- POST /api/v1/auth/sign-in - Sign in with username and password
- POST /api/v1/auth/sign-up - Sign up with username and password
- GET /api/v1/operations - fetch all operations
- POST /api/v1/operations - perform the calculation for the given operation
- GET /api/v1/user - fetch the information of the currently logged in user
- PATCH /api/v1/user - update the information of the currently logged in user
- GET /api/v1/record - fetch all records of the currently logged in user
- DELETE /api/v1/record/{id} - delete the record with the given id

## Database
Mysql is used as the database for this application. The database schema is as follows:

user->record->operation

## Live Version

the live version of this app is deployed in railway app platform. You can access it here: 
https://truenorth-hector-ocampo-production.up.railway.app/api/v1/swagger-ui/index.html
