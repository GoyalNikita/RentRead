# RentRead - Online Book Rental System

## Author - Nikita Goyal

## Overview
RentRead is an online book rental system developed using Spring Boot and MySQL. This system allows users to register, log in, browse books, and rent/return books. It features authentication and authorization using Basic Auth, with different roles for users and administrators.

## Features
- **User Registration and Login**
  - Users can register with their email, password (encrypted using BCrypt), first name, and last name.
  - Default role is "USER" unless specified as "ADMIN".
  - Users log in using their email and password.
- **Book Management**
  - Store and manage book details (title, author, genre, availability status).
  - Only administrators can create, update, and delete books.
  - Any user can browse available books.
- **Rental Management**
  - Users can rent books if they have fewer than two active rentals.
  - Users can return rented books.

## API Endpoints

### Public Endpoints
- `POST /register` - Register a new user.
- `POST /login` - Log in an existing user.

### Private Endpoints
- `GET /books` - Retrieve all available books (USER and ADMIN).
- `POST /books` - Create a new book (ADMIN only).
- `PUT /books/{bookId}` - Update an existing book (ADMIN only).
- `DELETE /books/{bookId}` - Delete a book (ADMIN only).
- `POST /books/{bookId}/rent` - Rent a book (USER and ADMIN).
- `POST /books/{bookId}/return` - Return a rented book (USER and ADMIN).

## Getting Started

### Prerequisites
- Java 11 or higher
- Gradle 6.7.1 or higher
- MySQL 8.0 or higher


### Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/GoyalNikita/RentRead.git
    cd LearningNavigator
    ```

2. Install dependencies and build the project:
    ```bash
    ./gradlew build
    ```

3. Run the application:
    ```bash
    ./gradlew bootRun
    ```

4. The API will be available at `http://localhost:8080`.

### Configuration

- Update `application.properties` with your MySQL configurations if needed:
    ```properties
    spring.datasource.url = jdbc:mysql://localhost:3300/{database}
    spring.datasource.username={username ex. root}
    spring.datasource.password={password ex. root}
    spring.jpa.hibernate.ddl-auto = update
    ```

## Running the Tests

Run the following command to execute the tests:
```bash
./gradlew test
```

## Postman Collection

You can use the following Postman Collection to test the API:
[Read Rent API Postman Collection](https://red-firefly-736811.postman.co/workspace/New-Team-Workspace~446ac2dc-32b8-4882-b3de-4aba165c8ad5/collection/36335262-27471865-4d51-4d0d-aa7b-0a91ad110e29?action=share&creator=36335262)
