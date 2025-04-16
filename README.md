# Cagnotte API

A modern RESTful API built with **Spring Boot**, supporting user authentication, cagnotte creation, participation handling (with anonymous options), access control, and public/private sharing with token-based access.

---

## Features

- ✅ User registration & login (JWT-based)
- ✅ Role-Based Access Control (RBAC)
- ✅ Public/private cagnottes with tokenized access
- ✅ Anonymous and named participations
- ✅ Custom messages, amount visibility settings
- ✅ Swagger documentation
- ✅ Rate-limiting using Bucket4j
- ✅ PostgreSQL database support
- ✅ Docker & Docker Compose ready for deployment
- ✅ CI/CD-ready project structure

## Project Structure

```
├── src/main/java/com/grey/cagnotte
│   ├── controller
│   ├── entity
│   ├── enums
│   ├── events
│   ├── exception
│   ├── payload (request/response)
│   ├── repository
│   ├── security
│   ├── seeder
│   ├── service / impl
│   ├── utils
│   └── CagnotteApplication.java
├── src/main/resources
│   ├── images
│   ├── META-INF
│   └── application.yml
├── Dockerfile
├── docker-compose.yml
├── .env.xample
├── pom.xml
└── README.md
```

## Technologies

- Java 17
- Spring Boot 3.x
- Spring Security (JWT)
- PostgreSQL
- Maven
- Lombok
- Log4j2
- Bucket4J
- Swagger (SpringDoc)
- Docker / Docker Compose

## Getting Started

### 1. Clone the project

```bash
git clone https://github.com/Alwil17/cagnotte.git
cd cagnotte
```

### 2. Create `.env` file (if using Docker Compose)

```env
SERVER_PORT=8080

SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/cagnotte_db
SPRING_DATASOURCE_USERNAME=your_user
SPRING_DATASOURCE_PASSWORD=your_password
SPRING_DATASOURCE_DRIVER-CLASS-NAME=org.postgresql.Driver
```

### 3. Build and Run with Maven

```bash
mvn clean install
java -jar target/cagnotte-0.0.1-SNAPSHOT.jar
```

---

## Run with Docker

### 1. Build the image

```bash
docker build -t cagnotte-api .
```

### 2. Run the container

```bash
docker run -p 8080:8080 --env-file .env cagnotte-api
```

Or use **Docker Compose**:

### docker-compose.yml

```yaml
version: '3.8'

services:
  app:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "${SERVER_PORT}:${SERVER_PORT}"
    environment:
      - SPRING_DATASOURCE_URL=${SPRING_DATASOURCE_URL}
      - SPRING_DATASOURCE_USERNAME=${SPRING_DATASOURCE_USERNAME}
      - SPRING_DATASOURCE_PASSWORD=${SPRING_DATASOURCE_PASSWORD}
      - SPRING_DATASOURCE_DRIVER-CLASS-NAME=${SPRING_DATASOURCE_DRIVER-CLASS-NAME}
      - SERVER_PORT=${SERVER_PORT}
    env_file:
      - .env
```

```bash
docker-compose up --build
```

## 🔍 API Documentation

- Swagger UI available at: `http://localhost:8080/swagger-ui/index.html` or `https://cagnotte-api.onrender.com/swagger-ui/index.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs` or `https://cagnotte-api.onrender.com/v3/api-docs`

## CI/CD Suggestions

- Use **GitHub Actions** to build and test the project on push
- Deploy to **Render**, **Railway**, or **Fly.io** using Docker
- Use secrets for `.env` config in hosted environments

## Security Notes

- Rate-limiting recommended (e.g., Bucket4j or Spring filters)
- Secure database access & use secrets in production
- Always validate tokens & handle role-based permissions carefully

---

## Contribution

Pull requests are welcome! Please follow best practices and open an issue first if proposing a major change.

---

## Contact

Built by Alwil17 - contact@example.com