# Spring-Security Using JWT Authentication

## DESCRIPTION
Simple project `Spring-Boot` and `spring-security` using `JWT Authentication`.

## Instalation
Step for installation:
```sh
# Clone this project from github
https://github.com/saptarga/spring-boot-basic-authentication.git

# Clears the target directory and builds the project
mvn clean install
```

## Configuration
Step for configuration:
- Create new database in postgresql with database name `demo_jwt_auth`
- Set database name, user, and password in `application-properties`
- Create table `sec_roles`, `sec_users`, and `sec_user_roles`
```sh 
    create table sec_roles(
        id bigserial primary key,
        name varchar
    );

    create table sec_users(
        id bigserial primary key,
        username varchar UNIQUE,
        email varchar UNIQUE,
        password varchar
    );

    create table sec_user_roles(
        id bigserial primary key,
        user_id int8,
        role_id int8,
        CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES sec_users(id),
        CONSTRAINT fk_role FOREIGN KEY(role_id) REFERENCES sec_roles(id)
    );
```
- Seeding data for table `sec_roles`
```sh 
    INSERT INTO sec_roles(name) VALUES('ROLE_USER');
    INSERT INTO sec_roles(name) VALUES('ROLE_ADMIN');
```
- set `app.jwtSecret` and `app.jwtExpirationMs` in `application-properties`

## Run Project
You can start this project using
```sh
mvn clean spring-boot:run
```

## Example Requests API
Register User
```sh
POST /api/auth/register-user HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 127

{
    "username": "saptarga",
    "password": "123456789",
    "email": "saptarga@gmail.com",
    "role": ["ROLE_ADMIN"]
}
```

Login User or Authentication User
```sh
POST /api/auth/login HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Content-Length: 62

{
    "username": "saptarga",
    "password": "123456789"
}
```

Get List Posts with Authentication
```sh
GET /posts HTTP/1.1
Host: localhost:8080
Content-Type: application/json
Authorization: Bearer {{your_token_from_login_user}}
```

## Author
Created and maintained by saptarga ([@saptarga](https://www.linkedin.com/in/saptarga)).

Feel free if you have question for this project.