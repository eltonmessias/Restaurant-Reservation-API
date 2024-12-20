# **Restaurant Reservation API**


A backend API for managing restaurant reservations. Allows customers to make reservations


## Table of contents

1. [Description](#description)
2. [Features](#features)
3. [Technologies used](#technologies)
4. [Installation and Setup](#installation-and-setup)
    - [With Docker](#with-docker)
    - [Without Docker](#without-docker)
5. [API Endpoint](#api-endpoint)
6. [Authentication and Authorization](#authentication-and-authorization)
7. [Licence](#licence)

---

## Description

The **Restaurant Reservation API** is a backend system for managing restaurant reservations. It provides secure endpoints for customers to create reservations and for administrators to manage tables and users.


---

## **Features**

- **JWT authentication** to secure endpoints.
- Different permissions for users and administrators.
- Full CRUD functionalities for tables and reservations.
- Data validation for input data.
- Global error logs for validation and exception handling.
- Support for different table and reservation statuses.


---

## **Technologies Used**

- **Java 21**
- **Spring Boot 3**
  - Spring Security
  - Spring Data JPA
  - Hibernate Validator
- **Database:** PostgreSQL
- **Authentication:** JWT
- **Maven** as dependency manager
- **Swagger** for API documentation
- **Docker** for containerization


---

## **Installation and Setup**

### With Docker

To run the application using Docker, follow these steps:

1. Clone the repository:
    ```bash
   git clone https://github.com/eltonmessias/Restaurant-Reservation-API.git
   cd Restaurant-Reservation-API
2. Build the Docker image:
    ```bash
   docker build -t reservation-system .
3. Run the Docker container:
    ```bash
   docker run -p reservation-system
   
4. Access the API at http://localhost:8080

---

## API Endpoints

### **Authentication**
* ```POST /auth/restister``` - Register a new user.
* ```POST /auth/login``` - Authenticate user and generate a JWT token

### **Reservations**
* ```GET /api/admin/reservations``` - List all reservations(Admin)
* ```POST /api/reservations``` - Create a new reservation(Customer)
* ```PUT /api/reservations/{id}``` - Update an existing reservation
* ```DELETE /api/reservations/{id}``` - Cancel a reservation

### **Tables**
* ```GET /api/tables``` - List all tables
* ```POST /api/admin/tables``` - Add a new table(Admin)
* ```PATCH /api/tables/{id}``` - Update status of a table
* ```DELETE /api/tables/{id}``` - Remove a table (Admin)


---

## **Authentication and Authorization**
* **Authentication**: JWT (Bearer Token)
* **User Roles:**
  * ```ADMIN```: Full access to management endpoints
  * ```USER```: Permissions to create and manage their own reservations
* **Role-based Protection**: Configured in ```SecurityConfig```

---

## Swagger Documentation
Access the API Documentation at: http://localhost:8080/swagger-ui.html

---


## **Licence**

This project is licensed under the [MIT Licence](https://mit-license.org/)