# 🛒 Product Service

Product Service is a Spring Boot microservice that manages product information in an e-commerce system. It exposes product data via REST APIs and serves as the source of truth for product details such as name, price, stock quantity, availability, brand, and category.

In addition to serving product information, the service also acts as a Kafka consumer. It listens for order-related events published by the Order Service on the `order-topic`. Upon receiving these events, the Product Service can process or log them as needed, allowing it to eventually update stock, trigger alerts, or integrate with other services.

This service is part of a microservices architecture and is designed to demonstrate inter-service communication via REST as well as event-driven messaging using Apache Kafka. It uses Spring Data JPA to persist product data in a MySQL database, and supports modern API documentation via Swagger UI.

The key functionalities of this service include:
- Providing product information through a REST API (`GET /products/{id}`)
- Listening to Kafka events from the `order-topic`
- Logging and reacting to incoming order events
- Managing product availability and details in the database

This service runs independently but expects Kafka and the Order Service to be available when processing events. It is designed to remain lightweight, focused, and responsive to upstream changes in a distributed system.

---

## ⚙️ Technologies Used

- **Java 17**
- **Spring Boot 3.4.x**
- **Spring Web** (for REST API)
- **Spring Data JPA** (for database access)
- **Apache Kafka** (consumer for event handling)
- **MySQL** (as the relational database)
- **Swagger/OpenAPI** (for interactive API documentation)
- **Docker + Docker Compose** (for running Kafka and Zookeeper)
