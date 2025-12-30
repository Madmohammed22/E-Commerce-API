# E-Commerce API

A RESTful API for an e-commerce platform built with Spring Boot, JWT authentication, and PostgreSQL.

## 🏗️ Architecture

This application follows a layered architecture pattern with clear separation of concerns:

```
┌─────────────────────────────────────────────────────────────┐
│                      Presentation Layer                      │
│                       (Controllers)                          │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                      Business Layer                          │
│                        (Services)                            │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                      Data Access Layer                       │
│                      (Repositories)                          │
└─────────────────────────────────────────────────────────────┘
                              │
┌─────────────────────────────────────────────────────────────┐
│                    Database (PostgreSQL)                     │
└─────────────────────────────────────────────────────────────┘
```

## 📊 UML Class Diagram

### Entity Model

```mermaid
classDiagram
    class User {
        +Long id
        +String email
        +String password
        +String fullName
        +String address
        +String phone
        +Role role
        +LocalDateTime createdAt
        +onCreate()
    }

    class Role {
        <<enumeration>>
        CUSTOMER
        ADMIN
    }

    class Product {
        +Long id
        +String name
        +String description
        +Double price
        +Integer stockQuantity
        +String category
        +String imageUrl
        +LocalDateTime createdAt
        +onCreate()
    }

    class Order {
        +Long id
        +User user
        +Set~OrderItem~ orderItems
        +Double totalAmount
        +OrderStatus status
        +String shippingAddress
        +LocalDateTime createdAt
        +onCreate()
    }

    class OrderStatus {
        <<enumeration>>
        PENDING
        PROCESSING
        SHIPPED
        DELIVERED
        CANCELLED
    }

    class OrderItem {
        +Long id
        +Order order
        +Product product
        +Integer quantity
        +Double price
    }

    User "1" -- "*" Order : places
    Order "1" -- "*" OrderItem : contains
    Product "1" -- "*" OrderItem : references
    User ..> Role : uses
    Order ..> OrderStatus : uses
```

### Application Architecture

```mermaid
classDiagram
    class AuthController {
        -AuthService authService
        +register(AuthRequestDto) AuthResponseDto
        +login(AuthRequestDto) AuthResponseDto
    }

    class ProductController {
        -ProductService productService
        +getAllProducts() List~ProductDto~
        +getProductById(Long) ProductDto
        +createProduct(ProductDto) ProductDto
        +updateProduct(Long, ProductDto) ProductDto
        +deleteProduct(Long) void
    }

    class OrderController {
        -OrderService orderService
        +createOrder(OrderDto) OrderDto
        +getOrderById(Long) OrderDto
        +getUserOrders() List~OrderDto~
        +updateOrderStatus(Long, String) OrderDto
    }

    class AuthService {
        -UserRepository userRepository
        -JwtTokenProvider jwtTokenProvider
        +register(AuthRequestDto) AuthResponseDto
        +login(AuthRequestDto) AuthResponseDto
    }

    class ProductService {
        -ProductRepository productRepository
        -ProductMapper productMapper
        +getAllProducts() List~ProductDto~
        +getProductById(Long) ProductDto
        +createProduct(ProductDto) ProductDto
        +updateProduct(Long, ProductDto) ProductDto
        +deleteProduct(Long) void
    }

    class OrderService {
        -OrderRepository orderRepository
        -ProductRepository productRepository
        -OrderMapper orderMapper
        +createOrder(OrderDto, User) OrderDto
        +getOrderById(Long) OrderDto
        +getUserOrders(Long) List~OrderDto~
        +updateOrderStatus(Long, String) OrderDto
    }

    class UserRepository {
        <<interface>>
        +findByEmail(String) Optional~User~
    }

    class ProductRepository {
        <<interface>>
        +findById(Long) Optional~Product~
        +findAll() List~Product~
    }

    class OrderRepository {
        <<interface>>
        +findByUserId(Long) List~Order~
        +findById(Long) Optional~Order~
    }

    AuthController --> AuthService
    ProductController --> ProductService
    OrderController --> OrderService

    AuthService --> UserRepository
    ProductService --> ProductRepository
    OrderService --> OrderRepository
    OrderService --> ProductRepository
```

### Security Components

```mermaid
classDiagram
    class SecurityConfig {
        -JwtAuthenticationFilter jwtAuthenticationFilter
        +securityFilterChain(HttpSecurity) SecurityFilterChain
        +passwordEncoder() PasswordEncoder
        +authenticationManager(AuthenticationConfiguration) AuthenticationManager
    }

    class JwtAuthenticationFilter {
        -JwtTokenProvider jwtTokenProvider
        -UserDetailsService userDetailsService
        +doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain) void
    }

    class JwtTokenProvider {
        -String jwtSecret
        -Long jwtExpirationMs
        +generateToken(Authentication) String
        +getUsernameFromToken(String) String
        +validateToken(String) boolean
    }

    SecurityConfig --> JwtAuthenticationFilter
    JwtAuthenticationFilter --> JwtTokenProvider
```

### Data Transfer Objects (DTOs) and Mappers

```mermaid
classDiagram
    class UserDto {
        +Long id
        +String email
        +String fullName
        +String address
        +String phone
        +String role
    }

    class ProductDto {
        +Long id
        +String name
        +String description
        +Double price
        +Integer stockQuantity
        +String category
        +String imageUrl
    }

    class OrderDto {
        +Long id
        +Long userId
        +List~OrderItemDto~ orderItems
        +Double totalAmount
        +String status
        +String shippingAddress
        +LocalDateTime createdAt
    }

    class OrderItemDto {
        +Long productId
        +Integer quantity
        +Double price
    }

    class AuthRequestDto {
        +String email
        +String password
        +String fullName
    }

    class AuthResponseDto {
        +String token
        +UserDto user
    }

    class UserMapper {
        <<interface>>
        +toDto(User) UserDto
        +toEntity(UserDto) User
    }

    class ProductMapper {
        <<interface>>
        +toDto(Product) ProductDto
        +toEntity(ProductDto) Product
    }

    class OrderMapper {
        <<interface>>
        +toDto(Order) OrderDto
        +toEntity(OrderDto) Order
    }

    UserMapper ..> UserDto
    ProductMapper ..> ProductDto
    OrderMapper ..> OrderDto
    OrderDto --> OrderItemDto
    AuthResponseDto --> UserDto
```

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.2.1
- **Language**: Java 21
- **Database**: PostgreSQL
- **Security**: Spring Security + JWT
- **ORM**: Spring Data JPA (Hibernate)
- **Mapping**: MapStruct 1.6.3
- **Build Tool**: Maven
- **Documentation**: SpringDoc OpenAPI 2.5.0
- **Container**: Docker Compose

## 📦 Key Dependencies

| Dependency | Version | Purpose |
|------------|---------|---------|
| Spring Boot | 3.2.1 | Core framework |
| Spring Security | 3.2.1 | Authentication & Authorization |
| JWT | 0.12.5 | Token-based authentication |
| PostgreSQL | Latest | Database |
| MapStruct | 1.6.3 | DTO mapping |
| Lombok | 1.18.42 | Boilerplate code reduction |
| OpenFeign | 2023.0.3 | HTTP client |
| SpringDoc | 2.5.0 | API documentation |

## 🚀 Getting Started

### Prerequisites

- Java 21
- Maven 3.x
- Docker & Docker Compose
- PostgreSQL (or use Docker Compose)

### Installation

1. Clone the repository
```bash
git clone <repository-url>
cd E-Commerce-API
```

2. Start PostgreSQL with Docker Compose
```bash
docker-compose up -d
```

3. Build the project
```bash
mvn clean install
```

4. Run the application
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## 📚 API Documentation

Once the application is running, access the interactive API documentation at:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## 🔐 Security

The API uses JWT (JSON Web Token) for authentication. The security flow:

1. User registers or logs in via `/api/auth/register` or `/api/auth/login`
2. Server returns a JWT token
3. Client includes the token in the `Authorization` header for subsequent requests: `Authorization: Bearer <token>`
4. JwtAuthenticationFilter validates the token on each request

### Roles

- **CUSTOMER**: Default role for registered users
- **ADMIN**: Administrative privileges

## 📁 Project Structure

```
src/main/java/com/ecommerce/
├── config/              # Security and application configuration
│   └── SecurityConfig.java
├── controller/          # REST endpoints
│   ├── AuthController.java
│   ├── OrderController.java
│   └── ProductController.java
├── dto/                 # Data Transfer Objects
│   ├── AuthRequestDto.java
│   ├── AuthResponseDto.java
│   ├── OrderDto.java
│   ├── OrderItemDto.java
│   ├── ProductDto.java
│   └── UserDto.java
├── entity/              # JPA entities
│   ├── Order.java
│   ├── OrderItem.java
│   ├── Product.java
│   └── User.java
├── mapper/              # MapStruct mappers
│   ├── OrderMapper.java
│   ├── ProductMapper.java
│   └── UserMapper.java
├── repository/          # Data access layer
│   ├── OrderRepository.java
│   ├── ProductRepository.java
│   └── UserRepository.java
├── security/            # JWT security components
│   ├── JwtAuthenticationFilter.java
│   └── JwtTokenProvider.java
├── service/             # Business logic
│   ├── AuthService.java
│   ├── OrderService.java
│   └── ProductService.java
└── EcommerceApplication.java  # Main application class
```

## 🔄 Database Schema

The application uses the following main tables:
- `users` - User accounts and authentication
- `products` - Product catalog
- `orders` - Customer orders
- `order_items` - Individual items within orders

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is a demo application for learning purposes.

