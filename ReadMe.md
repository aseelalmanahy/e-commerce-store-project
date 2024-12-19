# Product Catalog Service

## Overview
The **Product Catalog Service** is a Spring Boot application designed to manage and provide product details, allowing users to filter and sort products based on various criteria. It serves as the foundation for building an e-commerce platform or integrating with other microservices such as Order or Inventory Management.

### Key Features
- Filter products by:
    - Price range
    - Brand
    - Availability (in stock or out of stock)
- Sort products by:
    - Price (ascending or descending)
    - Name (alphabetical order)

---

## Technologies Used
- **Java 17**
- **Spring Boot 3.3.4**
- **Spring Data JPA**
- **MySQL**
- **Maven**

---

## Setup Instructions
### Prerequisites
1. **Java 17** or higher installed.
2. **Maven** installed.
3. **MySQL** database setup.

### Steps to Run the Project
1. Clone the repository:
   ```bash
   git clone <repository_url>
   ```
2. Navigate to the project directory:
   ```bash
   cd product-catalog-service
   ```
3. Configure the database:
    - Update the `application.properties` file with your MySQL credentials.
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/product_catalog
   spring.datasource.username=<your_username>
   spring.datasource.password=<your_password>
   ```
4. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```
5. Access the application:
    - Base URL: `http://localhost:8080`

---

## API Endpoints

### 1. Filter Products
**Endpoint**: `GET /products/filter`

#### Query Parameters:
- `minPrice` (optional): Minimum price to filter by.
- `maxPrice` (optional): Maximum price to filter by.
- `brand` (optional): Filter by product brand.
- `availability` (optional): Filter by stock status (`true` or `false`).
- `sortBy` (optional): Field to sort by (`price` or `name`).
- `order` (optional): Sorting order (`asc` or `desc`).

#### Example Request:
```
GET /products/filter?minPrice=100&maxPrice=500&brand=Samsung&sortBy=price&order=asc
```

#### Example Response:
```json
[
  {
    "id": 1,
    "name": "Smartphone",
    "price": 400,
    "brand": "Samsung",
    "availability": true
  },
  {
    "id": 2,
    "name": "Tablet",
    "price": 450,
    "brand": "Samsung",
    "availability": true
  }
]
```

---

## Future Enhancements
- Add pagination to handle large datasets.
- Integrate with an Order Service for end-to-end e-commerce functionality.
- Implement caching to improve performance.

---

## License
This project is licensed under the MIT License. See the LICENSE file for details.

