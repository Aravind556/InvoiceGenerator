# Invoice Generator

A Spring Boot application for creating, storing, generating PDF, and emailing invoices. The project demonstrates a layered architecture with DTO mapping, PDF generation integration, and email delivery of invoices.

## Project Description

This project allows users to:
- Create invoices with multiple items
- Store invoices in a database
- Map between DTOs and entities for clean separation of concerns
- Generate PDF versions of invoices (integration point for PDF generator)
- Email invoices as PDF attachments to recipients

## Features

- RESTful API for invoice management
- Entity-DTO mapping using custom mappers
- PDF invoice generation (integration ready)
- Email delivery of invoices with PDF attachments

## Technologies

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Mail
- Maven

## Project Structure

- `Entity/` \- JPA entities (`Invoice`, `Item`)
- `Dto/` \- Data Transfer Objects
- `Repository/` \- Spring Data JPA repositories
- `Service/` \- Business logic and service interfaces/implementations
- `Mapper/` \- DTO/entity mappers

## Setup

1. Clone the repository.
2. Add your database and mail configuration to `src/main/resources/application.properties`:
    ```
    spring.datasource.url=jdbc:mysql://localhost:3306/invoice_db
    spring.datasource.username=your_db_user
    spring.datasource.password=your_db_password

    spring.mail.host=smtp.example.com
    spring.mail.port=587
    spring.mail.username=your_email
    spring.mail.password=your_password
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
    ```
3. Build and run the application:
    ```
    mvn clean install
    mvn spring-boot:run
    ```

## Usage

- Use the REST API endpoints to create and retrieve invoices.
- The service maps DTOs to entities, persists them, and can send invoice PDFs via email.

## License

MIT License