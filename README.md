# 💼 User Management API

A RESTful web service for managing users — built with Spring Boot. This project demonstrates best practices for creating, retrieving, filtering, and paginating user data, along with Swagger/OpenAPI documentation.

---

## 📌 Features

- Create a new user using DTOs and validation
- List all users with pagination
- Filter users by first name and/or age
- Swagger UI integration for API documentation and testing
- Clean layered architecture (Controller → Service → Repository)

---

##  Tech Stack

| Component           | Version        |
|---------------------|----------------|
| Java                | 17+            |
| Spring Boot         | 3.x            |
| Spring Web          | ✅             |
| Spring Data JPA     | ✅             |
| H2 Database         | ✅             |
| Springdoc OpenAPI   | 2.2.0          |
| Maven               | ✅             |

---

## 📁 Project Architecture

```
src/
└── main/
    ├── java/
    │   └── es.ibm.usermanagement/
    │       ├── controller/    UserController
    │       ├── service/       UserService
    │       ├── repository/    UserRepository → Data access (JPA)
    │       ├── entity/        User → JPA entities 
    │       └── dto/           UserRequest
    └── resources/
        └── application.yml
```

---

## 🚀 Getting Started

### 1. Clone the project

```bash
git clone https://github.com/nesrineabkk/user-management.git
cd user-management

```

### 2. Run the Spring Boot Application

You can run it using:

```bash
./mvnw spring-boot:run
```

Or from your IDE (IntelliJ, Eclipse).

---

## 📨 Example JSON for Creating a User

```json
POST /api/users
Content-Type: application/json

{
  "first_name": "Nesrine",
  "last_name": "Abdelkarim",
  "age": 26,
  "subscribed": true,
  "postal_code": "29014"
}
```

---

## 📃 View Users + Filter + Paginate

### ➕ List all users
```http
GET /api/users/search
```

### 🔍 Filter by first name
```http
GET /api/users/search?firstName=Nesrine
```

### 🔍 Filter by age
```http
GET /api/users/search?age=26
```

### 🔍 Filter by both + paginate
```http
GET /api/users/search?firstName=Nesrine&age=26&page=0&size=5&sort=firstName,asc
```

---

## 📉 Swagger / OpenAPI Docs

Thanks to **Springdoc OpenAPI**, you can explore and test the API interactively.

### 📦 Added Swagger Library

```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.2.0</version>
</dependency>
```

### 🌐 Access Swagger UI

After starting the app, open:

```
http://localhost:8080/swagger-ui.html
```
or
```
http://localhost:8080/swagger-ui/index.html
```

![Swagger UI Screenshot](./asstes/swagger.PNG)

---

## 🗃️ H2 Database Console

Access the in-memory database via:

```
http://localhost:8080/h2-console
```

**JDBC URL:**
```
jdbc:h2:mem:testdb
```
**Username:** `sa`  
**Password:** *(leave empty)*

---

