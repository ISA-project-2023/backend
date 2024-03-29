# Medical Equipment Procurement System - Team 47

## Project Overview

This project aims to develop a web application serving as a centralized information system for companies involved in the procurement of medical equipment. The system facilitates private hospitals in reserving and acquiring medical equipment. Additionally, administrators can input sales reports related to the equipment. The system manages a large number of registered companies, keeping records of employees, registered companies, equipment reservations, pickup schedules, users, and their profiles.

## Technologies Used

- **Backend:** Java, Spring Boot
- **Frontend:** Angular
- **Database:** PostgreSQL (pgAdmin)
- **Message Queue:** RabbitMQ

## System Functionality

### 1. System Purpose

The primary purpose of the application is to maintain records of employees, registered companies, equipment reservations, pickup schedules, users, and their profiles. Private hospitals can use the system to reserve and acquire medical equipment, and administrators can input sales reports.

### 2. User Types

The system caters to different user types:

- **Registered User:** Employees of private hospitals. They can reserve equipment pickup slots, cancel reservations up to 24 hours before, view scheduled pickup slots, write complaints, and have a personal profile tracking order history. They don't have access to other users' profiles.

- **Company Administrator:** Has access to profiles of clients who have reserved equipment and their pickup history. They can view a graphical representation of all pickup slots. Each administrator is associated with a single company.

- **System Administrator:** Registers new system administrators, companies, company administrators, responds to complaints from registered users, and defines loyalty programs.

- **Unauthenticated Users:** Can search for companies, register, and, if already registered, log in.

## Getting Started

To set up the project locally, follow these steps:

    1. Clone the repository.
    2. Set up the backend using Java and Spring Boot.
    3. Set up the frontend using Angular.
    4. Configure the database using pgAdmin.

## Running The Application

* Running the Angular application: Navigate to the `frontend-app/` folder and run the command:
    ```bash
    npm install --force
    ng serve
    ```

* [Installation and running RabbitMQ](https://www.youtube.com/watch?v=KhYiaEOrw7Q)

* Database configuration:
    ```
    postgresql://localhost:5432/jpa,
    username=jpa,
    password=super
    ```

* Running the Java Spring Boot Application (IntelliJ IDEA):

    1. Projects -> Open -> (rabbitmq-private-hopital) -> Run
    2. Projects -> Open -> (rabbitmq-producer-example) -> Run
    3. Projects -> Open -> (backend) -> Run
    4. Projects -> Open -> (external-application) -> Run

    Note: Install all the dependencies from the `pom.xml` file


## Contributors

- [Lara Petković](https://github.com/lara-petkovic) RA 185-2020
- [Dušan Suđić](https://github.com/dusan-sudjic) RA 81-2020
- [Jelisaveta Letić](https://github.com/jelly0107) RA 82-2020
- [Luka Milanko](https://github.com/Lukaa01) RA 78-2020

