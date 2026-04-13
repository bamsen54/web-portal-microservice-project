# Microservice Project - Grupp A

A microservice ecosystem with API Gateway, Spring Boot Admin Dashboard, and three independent microservices (Padel, Sushi, Travels).

## 👥 Authors

| Name | Service | GitHub                                     |
|------|---------|--------------------------------------------|
| Simon Toivola | Padel Service | [GitHub](https://github.com/bamsen54/WigellPadel)                                 |
| Lucas Celik | Sushi Service | [GitHub](https://github.com/Lucascelik/Sushi)    |
| Frida Lundberg | Travels Service | [GitHub](https://github.com/Fridaheldina/WigellTravels) |

## 🏗️ Services

| Service | Port | Description |
|---------|------|-------------|
| Dashboard | 9090 | Spring Boot Admin - Monitoring |
| Gateway | 8580 | API Gateway - Routing |
| Padel Service | 8584 | Padel booking API |
| Sushi Service | 8582 | Sushi booking API |
| Travels Service | 8581 | Travel booking API |

## 🚀 Start order

1. **Dashboard** (port 9090)
2. **Microservices** (Padel, Sushi, Travels) - any order
3. **Gateway** (port 8580)

> The gateway routes requests to the microservices. The services must be running before the gateway can route correctly.

## 🔐 Authentication

Basic Auth is used for all endpoints.

| Role | Username | Password |
|------|----------|----------|
| Admin | `a` | `1234` |
| User | `u` | `1234` |

## 📡 API Access

The API can be accessed:

- **Directly** via each microservice's own port
- **Via Gateway** at `http://localhost:8580/{service}/`

### Examples (Gateway)

## 🛠️ Tech Stack (Overall)

- Java 24
- Spring Boot 3.5.13
- Spring Cloud Gateway
- Spring Boot Admin 3.5.8
- Spring Data JPA / Hibernate
- MySQL 8.x
- Maven

## 📦 Database Configuration

Each microservice has its own database:

| Service | Database | MySQL User | Password |
|---------|----------|------------|----------|
| Padel | `wigell_padel_db` | root | root |
| Sushi | `wigell_sushi_db` | root | root |
| Travels | `wigell_travels_db` | root | root |

> The URL includes `?createDatabaseIfNotExist=true` so databases are created automatically.

## 📝 Notes

- All services run locally on `localhost`
- Gateway requires all microservices to be running before it can route requests
- Dashboard shows real-time health and metrics for all registered services

# 📂 Project Structure

```text
web-portal-microservice-project/
├── dashboard/
├── GatewayApi/
├── wigellPadel/
├── wigellSushi/
├── wigellTravels/
└── README.md