
# Product Management System

This is a Spring Boot-based product management system that provides APIs for user authentication and product management.

## Features
- User registration and login with JWT-based authentication.
- CRUD operations for managing products.
- Error handling with custom exceptions and handlers.
- Secure endpoints using Spring Security.

## Project Structure
```
src/main/java/com/springboot/spring_security
├── config         # Configuration files for security and application setup
├── controller     # Controllers for managing API endpoints
├── dto            # Data Transfer Objects for API communication
├── exception      # Custom exceptions
├── exceptionhandler # Handlers for managing exceptions
├── filter         # JWT authentication filter
├── model          # Entity classes for database models
├── repository     # Interfaces for database access
├── service        # Service classes implementing business logic
└── util           # Utility classes like JWT token management
```

## Prerequisites
- Java 11 or later
- Maven 3.6+
- A database (e.g., MySQL)

## Installation

1. Clone the repository:
   ```bash
   git clone <repository_url>
   ```

2. Navigate to the project directory:
   ```bash
   cd product_management
   ```

3. Configure the database in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. Build the project:
   ```bash
   mvn clean install
   ```

5. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Endpoints

### Authentication
- `POST /api/auth/login` - Login with username and password
- `POST /api/auth/register` - Register a new user

### Products
- `GET /api/products` - List all products
- `POST /api/products` - Create a new product
- `PUT /api/products/{id}` - Update a product
- `DELETE /api/products/{id}` - Delete a product

## Technologies Used
- Spring Boot
- Spring Security
- JWT for authentication
- Maven for dependency management

## License
This project is licensed under the MIT License. See the LICENSE file for details.
