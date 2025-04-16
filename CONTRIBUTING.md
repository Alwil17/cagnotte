# Contributing to Cagnotte API

Thank you for considering contributing to the Cagnotte API! We welcome contributions from everyone. The following guidelines will help you get started.


## Project Setup

Make sure you have the following installed:
- Java 17+
- Maven 3.8+
- Docker & Docker Compose (if testing containers)
- PostgreSQL (or use the one from Docker)

Clone the project and set up your environment:
```bash
git clone https://github.com/Alwil17/cagnotte-api.git
cd cagnotte-api
cp .env.example .env
```

## How to Contribute

### 1. Fork & Branch
- Fork the repository
- Create a new branch: `feature/your-feature-name` or `fix/bug-description`

### 2. Code Guidelines
- Follow Java and Spring Boot best practices
- Use meaningful commit messages (e.g., `fix: handle null user permissions in UserResponse mapping`)
- Write unit tests for any logic you add or modify

### 3. Test Before Commit
- Run tests using:
```bash
mvn test
```
- Test locally or via Docker Compose if you added environment-related changes:
```bash
docker-compose up --build
```

### 4. Open a Pull Request
- Push to your fork
- Open a PR with a descriptive title and a summary of changes
- Make sure your PR targets the `main` branch unless told otherwise

---

## Communication
Use issues to:
- Report bugs
- Propose enhancements
- Discuss new ideas before opening a PR

---

## Security
If you discover a security vulnerability, do **not** open an issue. Instead, please contact the maintainers directly.

---

## ❤️ Thank You!
Your contributions make this project better. Whether it's code, feedback, or just pointing out a typo, you're helping build something great. 🙌