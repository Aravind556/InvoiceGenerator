# Invoice Generator

A full-featured Spring Boot application for creating, managing, and distributing invoices. This project provides a web interface and a REST API to generate professional-looking PDF invoices and deliver them via email.

## Features

-   **Web Interface**: An intuitive web UI built with Thymeleaf and Bootstrap to create, view, and list invoices.
-   **REST API**: A comprehensive REST API for programmatic invoice management.
-   **Dynamic PDF Generation**: Generates clean, well-formatted PDF invoices using the OpenPDF library.
-   **Email Integration**: Automatically sends generated PDF invoices as email attachments to customers using Spring Mail.
-   **Database Persistence**: Saves all invoice data using Spring Data JPA, configured for an in-memory H2 database by default.
-   **PDF Caching**: Implements a simple in-memory cache for recently generated PDFs to improve performance for repeated downloads.
-   **Docker Support**: Includes a multi-stage `dockerfile` for easy containerization and deployment.

## Technologies Used

-   **Backend**: Java 21, Spring Boot 3
-   **Data**: Spring Data JPA, H2 Database
-   **Web**: Spring Web, Thymeleaf
-   **PDF**: OpenPDF
-   **Email**: Spring Boot Starter Mail
-   **Build**: Maven
-   **Containerization**: Docker

## Getting Started

### Prerequisites

-   Java Development Kit (JDK) 21 or later
-   Apache Maven
-   An SMTP server (e.g., Gmail) for sending emails

### Installation & Configuration

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/aravind556/invoicegenerator.git
    cd invoicegenerator
    ```

2.  **Configure Email Settings:**
    The application uses environment variables to configure email credentials. Set the following variables in your environment or IDE's run configuration:

    -   `SPRING_MAIL_USERNAME`: Your SMTP username (e.g., your.email@gmail.com).
    -   `SPRING_MAIL_PASSWORD`: Your SMTP password or an app-specific password.

    These are referenced in `src/main/resources/application.properties`.

3.  **Build and Run the Application:**
    ```sh
    ./mvnw spring-boot:run
    ```
    The application will start on `http://localhost:8080`.

### Using Docker

You can also run the application using Docker.

1.  **Build the Docker image:**
    ```sh
    docker build -t invoice-generator .
    ```

2.  **Run the Docker container:**
    Pass your email credentials as environment variables to the container.
    ```sh
    docker run -p 8080:8080 \
      -e SPRING_MAIL_USERNAME="your-email@example.com" \
      -e SPRING_MAIL_PASSWORD="your-email-password" \
      invoice-generator
    ```

## Usage

### Web Interface

-   **Home Page (`/`)**: Navigate to `http://localhost:8080` to see a list of all created invoices.
-   **Create Invoice (`/invoices/create`)**: Click the "Create New Invoice" button to access a form where you can enter customer details and add line items. Submitting the form creates the invoice, generates a PDF, and emails it to the customer.
-   **Invoice Detail (`/invoices/{id}`)**: View details of a specific invoice, including an embedded preview of the PDF.

### API Endpoints

The API is available under the `/Invoice` path.

| Method | Endpoint             | Description                                          |
| :----- | :------------------- | :--------------------------------------------------- |
| `POST` | `/Invoice`           | Creates a new invoice from the JSON payload.         |
| `GET`  | `/Invoice`           | Returns a list of all invoices.                       |
| `GET`  | `/Invoice/{id}/view` | Returns the PDF file for inline viewing (in browser).|
| `GET`  | `/Invoice/{id}/pdf`  | Downloads the invoice as a PDF file.                 |

**Example `POST /Invoice` Body:**
```json
{
    "name": "Jane Doe",
    "email": "jane.doe@example.com",
    "items": [
        {
            "itemName": "Web Design Service",
            "quantity": 10,
            "unitPrice": 50.0,
            "discount": 5
        },
        {
            "itemName": "Logo Creation",
            "quantity": 1,
            "unitPrice": 300.0,
            "discount": 0
        }
    ]
}