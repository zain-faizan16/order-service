# 📦 Order Service

The **Order Service** is responsible for processing customer orders and coordinating related operations across the eCommerce system. It publishes events to trigger actions in other services such as Payment and Notification.

---

## ✨ Features

- 📝 **Create Orders**:
  - Accepts user email and product items.
- 🔍 **Fetch User Orders**:
  - Retrieve all orders for a specific user.
- 📤 **Emit `order.created` Events**:
  - Publishes order events via RabbitMQ for downstream services.
- 🧾 **Order DTO Mapping**:
  - Includes ordered items and timestamps.
- 🔗 **Integration**:
  - Initiates Payment Service.
  - Sends user notifications via Notification Service.

---

## 🛠 Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA** (SQL Server)
- **RabbitMQ** (event publishing)
- **ModelMapper**
- **Spring Cache**

---

## 📁 Project Structure (Simplified)

```plaintext
order-service/
├── controller/
├── dto/
├── entity/
├── repository/
├── service/
├── messaging/          # Event publishing logic
├── config/
└── OrderServiceApplication.java
