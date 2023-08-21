# Bookstore Web Application

This is a Java-based web application that allows an admin user to add new books and view all existing books on the main index page. The application is built using Spring Boot, Spring Data JPA, and Spring MVC.

## Main Components and Architecture

### Main Components:

1. **Controller (`BookController.java`)**: Handles HTTP requests, interacts with the `BookService` class, and returns appropriate views.
2. **Service (`BookService.java`)**: Implements the business logic for handling books, interacts with the `BookRepository` class, and caches the list of books.
3. **Repository (`BookRepository.java`)**: Provides CRUD operations for the `Book` entity and interacts with the database.
4. **Model (`Book.java`)**: Represents the `Book` entity with fields `id`, `name`, and `dateAdded`.

### Architecture and Technologies:

- **Framework**: The application uses the Spring Boot framework, which simplifies the development of Java web applications.
- **Web Layer**: The application uses Spring MVC for handling HTTP requests and returning views.
- **Data Access Layer**: The application uses Spring Data JPA for data access and CRUD operations.
- **Database**: The application uses an H2 in-memory database with a single table for the `Book` entity.
- **Caching**: The application uses Spring's caching support to cache the list of books and improve the performance of the main page.

## Risks

1. **Database Performance**: The performance of the main page depends on the database query for retrieving the list of books. Ensure that the database is optimized for performance, especially when dealing with a large number of books.
2. **Caching**: While caching can improve performance, it can also introduce complexities, such as cache invalidation and stale data. Proper cache configuration and management are essential.
3. **Concurrency**: Proper handling of concurrent requests and database transactions is required to prevent data inconsistencies.
4. **Load Testing**: Perform load testing to validate that the application can handle the forecasted user sessions and that the main page load time meets the requirement.

## Setup Guide

### Target OS:

- Any OS that supports Java (e.g., Windows, macOS, Linux).

### Required Software:

- Java Development Kit (JDK)
- Apache Maven

### Deployment Steps:

1. Install the required software.
2. Clone or download the provided application source code.
3. Compile and package the application using Maven: `mvn clean package`
4. Run the application: `java -jar target/bookstore-0.0.1-SNAPSHOT.jar`
5. Access the application in a web browser at `http://localhost:8080`

## Accessing the Application

1. **Run the application**: Follow the steps in the Setup Guide to run the application.
2. **Find the generated password**: After starting the application, check the console output for a line that looks like the following:

   ```
   Using generated security password: 78fa095d-3f4c-48b1-ad50-e24c31d5cf35
   ```

   The password will be different each time you run the application.

3. **Access the application in a web browser**: Open a web browser and go to `http://localhost:8080`.
4. **Log in**: When prompted, use the username `user` and the generated password from step 2 to log in.

Note: The application uses an H2 in-memory database, which is suitable for development and testing purposes. The database is automatically configured and does not require any external setup. Data is stored in memory and not persisted.
