# MFA Service with Spring & Docker

## Overview

This project demonstrates a Java Spring Boot application integrated with a MySQL database, running in separate Docker containers managed by Docker Compose. The application includes a multi-factor authentication (MFA) service with email verification.

## Table of Contents

- [Requirements](#requirements)
- [Setup](#setup) 
    - [Build and Run](#build and Run)
    - [Environment Variables](#environment variables)
    - [Docker](#docker compose)
- [Endpoints](#endpoints)
- [Database](#database)
- [Troubleshooting](#troubleshooting)
- [Acknowledgements](#acknowledgements)

## Requirements

- Docker and Docker Compose installed on your machine.
- Java Development Kit 21 (JDK)  installed (if you want to build the project locally).
- Gradle 8.5 +

## Setup

### Clone the Repository

Clone this repository to your local machine using(You should know how right? If not Google it).

### Build and Run

#### If you wish to run the app locally without getting involved with docker-compose magic check here:


- Of course, you will need Docker, so install it Windows, Linux, MAC preferly use the official page:
https://docs.docker.com/engine/install/
- I don't need to tell you need java right ? It's Java 21 `
- Gradle if you dont have it, for Java 21 must be version 8.5 or above: 
https://docs.gradle.org/8.8-rc-1/userguide/installation.html
- Once you have Docker, Java, Gradle and probably some IDE (get IntelliJ IDEA) run this in your cmd/terminal/bash w/e
and Docker container with mysql database will be started.
```
docker run --name mfa_db -e MYSQL_ROOT_PASSWORD=12345 -e MYSQL_DATABASE=mfa_db -p 3306:3306 -d mysql:latest 
```
`*** You can always use another database, keep in mind this project its set up for MySQL,
so you will have to make some changes ***`

### Environment Variables

#### Locally running through IDE
- You will need to input some ENV variables in your run configuration here is how:

  https://www.jetbrains.com/help/objc/add-environment-variables-and-program-arguments.html#add-environment-variables
- They should use this "pattern", you can change the order and values as per your needs:
```
DATABASE_HOST=localhost;DB_USER=root;DB_PASS=12345;MAIL_HOST=smtp.yourdomain.com;
MAIL_PASSWORD="yourmailpassword";MAIL_USERNAME=youremailaddress/username
```
#### Keep in mind you will need your own SMTP/EMAIL/PASSWORD Values. 
#### I'm a good person but won't give you mine here is how to make it with Gmail (it's easyyy):
https://www.gmass.co/blog/gmail-smtp/

#### Another think ! Make sure to set Java 21 in your IDE and run `./gradlew clean build` to build the app.

## Docker compose
For docker-compose you will need to add `.env` file in the root directory or just change mine I will add it to the repo.
```
MAIL_HOST=smtp.yourdomain-or-gmail.com
MAIL_USERNAME=your-email-address
MAIL_PASSWORD=password-for-email-or-app
DB_USER=root
DB_PASS=12345
DATABASE_HOST=mysql
MYSQL_ROOT_PASSWORD=12345
```
- Make sure you have run `./gradlew clean build`
- Run `docker-compose up` in the terminal of the ide or from the project directory.
- You should have a container started with MySQL database and the application running and fully functional.

## Endpoints
- Just open: http://localhost:8080/ once you start it, the app has Swagger and will redirect you directly to the endpoints.

The application exposes the following endpoints:

- Issue MFA Code: /api/mfa/send

Method: POST

Description: Sends an MFA code to the specified email address.
- Verify MFA Code: /api/mfa/verify:

Method: POST

Description: Verifies the provided MFA code.

## Database
The application uses MySQL as the database. The database schema is automatically created and managed by Spring Boot JPA and Hibernate.

### MySQL Configuration
- Database Name: mfa_db
- Username: user
- Password: 12345


## Troubleshooting
If you encounter any issues, here are some common solutions:

- Docker Containers Not Starting: Ensure Docker and Docker Compose are installed and running.
Check if the ports 8080 and 3306 are free.
- Database Connection Issues: Verify the MySQL container is running and accessible. 
Check the environment variables in the .env file. (if using docker-compose)
- Email Sending Issues: Ensure the email credentials and SMTP server settings are correct.

## Acknowledgements
- https://spring.io/projects/spring-boot- Spring Boot framework
- https://www.docker.com/ - Containerization platform
- https://www.mysql.com/ - Database management system
- https://docs.docker.com/compose/ - Tool for defining and running multi-container Docker applications
