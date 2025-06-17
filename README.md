
![Screenshot 2025-06-17 054835](https://github.com/user-attachments/assets/c5b25eb0-ea5e-4d87-bdee-5b8a270a89ee)
![Screenshot 2025-06-17 054827](https://github.com/user-attachments/assets/ea0f1460-cfaa-4d14-a725-a0c297b159bc)
Order Service :

Handles customer orders and order-related communication.

Features : 

Create and fetch user orders.

Emits order.created events via RabbitMQ.

Maps order DTOs with items and timestamps.

Calls the Payment Service and sends message to Notification.

Tech Stack :

Java 17, Spring Boot

Spring Data JPA (SQL Server)

RabbitMQ (event publishing)

ModelMapper, Caching with Spring
