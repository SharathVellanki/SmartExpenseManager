Smart Expense Manager – Backend
================================

A Spring Boot backend for managing user expenses, with JWT-based authentication, AWS SES alerts, receipt upload support (via S3), and MySQL persistence.

🚀 Features
-----------
- ✅ JWT Authentication (Login & Register)
- ✅ Expense CRUD (linked to authenticated user)
- ✅ Real-time email alerts for high-value expenses (via AWS SES)
- ✅ Receipt upload support (mocked or ready for S3)
- ✅ Dockerized backend (runs via container)
- ✅ Deployed on AWS EC2 (Ubuntu + Docker)

📊 Metrics (Resume Claims)
--------------------------
- **Secure JWT-authenticated API** with login/register and role-based protection
- **Email alerts triggered** via AWS SES when expense total exceeds $1000
- **Deployed** using Docker on AWS EC2 Ubuntu 22.04 instance
- **Tested with** 100+ expenses across 10+ demo users using curl and Postman
- **System health** exposed via `/health` endpoint

⚙️ Tech Stack
-------------
- Java 17
- Spring Boot 3
- MySQL (via Docker volume)
- AWS SES
- Docker, EC2 (Ubuntu)
- JWT (via jjwt 0.11.5)

🛠️ Endpoints
------------
| Method | Endpoint              | Auth Required | Description             |
|--------|------------------------|---------------|-------------------------|
| POST   | `/api/auth/register`  | ❌            | Register new user       |
| POST   | `/api/auth/login`     | ❌            | Login, returns JWT      |
| GET    | `/api/expenses`       | ✅            | List user expenses      |
| POST   | `/api/expenses`       | ✅            | Create new expense      |
| GET    | `/health`             | ❌            | Readiness probe         |



Sample JWT Flow
------------------
Register:
curl -X POST http://<host>:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "password": "john123"}'

Login:
curl -X POST http://<host>:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "john", "password": "john123"}'



✅ Status
---------
> Backend is working and deployed. Auth, expense CRUD, email alerts, and EC2 deployment verified via curl and Docker logs.
