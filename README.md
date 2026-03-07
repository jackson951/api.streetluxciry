# StreetLuxCity API

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.8-green.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15+-blue.svg)](https://www.postgresql.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-red.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## 🚀 Overview

StreetLuxCity API is a production-ready, enterprise-grade e-commerce backend system built with Spring Boot. This comprehensive RESTful API provides a complete solution for modern e-commerce platforms, featuring advanced user management, product catalog management, shopping cart functionality, order processing, and secure payment integration.

## ✨ Key Features

### 🔐 **Authentication & Security**
- JWT-based authentication with access and refresh tokens
- Role-based access control (Admin, Customer roles)
- Password encryption using BCrypt
- Email verification and OTP-based security
- Forgot password and password reset functionality
- Comprehensive security configuration with Spring Security

### 🛍️ **E-commerce Core Features**
- **Product Management**: Full CRUD operations for products with categories
- **Shopping Cart**: Persistent cart management with real-time updates
- **Order Processing**: Complete order lifecycle management
- **Customer Management**: Customer profiles and order history
- **Category Management**: Hierarchical product categorization

### 💳 **Payment & Checkout**
- Multi-provider payment support (Stripe integration ready)
- Secure checkout sessions with session-based cart management
- Payment method management
- Order tracking with multiple status stages
- Delivery fee calculation and shipping address management

### 📧 **Communication**
- SMTP-based email service for notifications
- Automated email templates for order confirmations
- Support for Gmail and other SMTP providers

### 🛠️ **Developer Experience**
- OpenAPI 3.0 documentation with Swagger UI
- Comprehensive error handling with custom exceptions
- Input validation using Bean Validation (JSR-380)
- HikariCP connection pooling for optimal database performance
- Flyway database migrations for schema management
- Docker containerization support

## 🏗️ Architecture

### Technology Stack
- **Backend Framework**: Spring Boot 3.3.8
- **Language**: Java 21
- **Database**: PostgreSQL (primary), MySQL, H2 (testing)
- **Security**: Spring Security, JWT, BCrypt
- **API Documentation**: SpringDoc OpenAPI 3.0
- **Build Tool**: Maven
- **Caching**: Spring Cache
- **Database Migration**: Flyway
- **Containerization**: Docker

### Project Structure
```
src/main/java/com/jackson/demo/
├── config/              # Configuration classes
│   ├── OpenApiConfig.java      # API documentation setup
│   ├── SecurityConfig.java     # Security configuration
│   └── DataSeeder.java         # Database seeding
├── controller/          # REST controllers
│   ├── ProductController.java
│   ├── OrderController.java
│   ├── CartController.java
│   ├── AuthController.java
│   ├── CheckoutController.java
│   ├── PaymentController.java
│   └── [Other controllers...]
├── service/             # Business logic layer
├── repository/          # Data access layer
├── entity/              # JPA entities
├── dto/                 # Data transfer objects
├── model/               # Enumerations and models
├── security/            # Security components
└── exception/           # Custom exceptions
```

### API Design Principles
- RESTful API design following HTTP standards
- Consistent response format with proper status codes
- Comprehensive input validation and error handling
- Role-based access control for all endpoints
- JWT token-based authentication
- Pagination support for list endpoints

## 🚀 Quick Start

### Prerequisites
- Java 21 or higher
- Maven 3.9 or higher
- PostgreSQL database
- SMTP server for email functionality

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/jackson951/api.streetluxcity.git
   cd api.streetluxcity
   ```

2. **Configure environment variables:**
   Create a `.env` file or set the following environment variables:
   ```bash
   # Database Configuration
   DB_PASSWORD=your_database_password
   
   # JWT Configuration
   JWT_SECRET=your_jwt_secret_key
   JWT_ACCESS_MINUTES=30
   JWT_REFRESH_DAYS=14
   
   # Email Configuration
   MAIL_HOST=smtp.gmail.com
   MAIL_PORT=587
   MAIL_USERNAME=your_email@gmail.com
   MAIL_PASSWORD=your_app_password
   MAIL_FROM=noreply@streeluxcity.shop
   MAIL_FROM_NAME=StreetLuxCity Support
   
   # Application Configuration
   DDL_AUTO=validate  # or update/create-drop for development
   SHOW_SQL=false     # set to true for debugging
   ```

3. **Build the application:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application:**
   - API Base URL: `http://localhost:8080/api`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`
   - OpenAPI Docs: `http://localhost:8080/api-docs`

### Docker Deployment

1. **Build the Docker image:**
   ```bash
   docker build -t streeluxcity-api .
   ```

2. **Run with Docker Compose (recommended):**
   ```bash
   docker-compose up -d
   ```

3. **Or run manually with environment variables:**
   ```bash
   docker run -p 8080:8080 \
     -e DB_PASSWORD=your_password \
     -e JWT_SECRET=your_secret \
     -e MAIL_USERNAME=your_email \
     -e MAIL_PASSWORD=your_password \
     streeluxcity-api
   ```

## 📚 API Documentation

### Authentication Endpoints

#### Register User
```http
POST /api/v1/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe"
}
```

#### Login
```http
POST /api/v1/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securePassword123"
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "expiresIn": 1800,
  "user": {
    "id": "uuid",
    "username": "john_doe",
    "email": "john@example.com",
    "role": "CUSTOMER"
  }
}
```

### Product Management

#### Get All Products
```http
GET /api/v1/products?q=laptop
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

#### Create Product (Admin only)
```http
POST /api/v1/products
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
  "name": "Gaming Laptop",
  "description": "High-performance gaming laptop",
  "price": 1299.99,
  "stockQuantity": 50,
  "categoryId": "uuid"
}
```

### Shopping Cart

#### Add to Cart
```http
POST /api/v1/cart/add
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
  "productId": "uuid",
  "quantity": 2
}
```

#### Get Cart
```http
GET /api/v1/cart
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

### Order Processing

#### Create Order
```http
POST /api/v1/orders
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
Content-Type: application/json

{
  "shippingAddress": "123 Main St, City, Country",
  "isDelivery": true
}
```

#### Track Order
```http
GET /api/v1/orders/{orderId}/tracking
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

## 🔧 Configuration

### Application Properties

The application uses environment-based configuration. Key properties include:

```properties
# Server Configuration
server.port=8080

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/streeluxcity
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}

# HikariCP Connection Pool
spring.datasource.hikari.maximum-pool-size=2
spring.datasource.hikari.minimum-idle=1

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false

# JWT Configuration
app.jwt.secret=${JWT_SECRET}
app.jwt.access-token-expiration-minutes=30
app.jwt.refresh-token-expiration-days=14

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `DB_PASSWORD` | Database password | - |
| `JWT_SECRET` | JWT signing secret | - |
| `MAIL_USERNAME` | SMTP username | - |
| `MAIL_PASSWORD` | SMTP password | - |
| `DDL_AUTO` | Hibernate DDL mode | validate |
| `SHOW_SQL` | Enable SQL logging | false |

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=ProductServiceTest

# Run tests with coverage
mvn clean test jacoco:report
```

### Test Structure

- **Unit Tests**: Located in `src/test/java/com/jackson/demo/service/`
- **Integration Tests**: Full application context tests
- **Security Tests**: Authentication and authorization tests
- **API Tests**: Controller endpoint testing

## 🚢 Deployment

### Production Deployment

1. **Database Setup:**
   ```sql
   CREATE DATABASE streeluxcity_prod;
   CREATE USER streeluxcity_user WITH PASSWORD 'secure_password';
   GRANT ALL PRIVILEGES ON DATABASE streeluxcity_prod TO streeluxcity_user;
   ```

2. **Environment Configuration:**
   ```bash
   # Production environment variables
   export DDL_AUTO=validate
   export SHOW_SQL=false
   export JWT_SECRET=production_jwt_secret_key_here
   export DB_PASSWORD=production_db_password
   ```

3. **Build for Production:**
   ```bash
   mvn clean package -DskipTests
   ```

4. **Run Application:**
   ```bash
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

### Docker Production Deployment

```bash
# Build production image
docker build -t streeluxcity-api:prod .

# Run with production configuration
docker run -d \
  --name streeluxcity-api \
  -p 8080:8080 \
  -e DDL_AUTO=validate \
  -e SHOW_SQL=false \
  -e JWT_SECRET=your_production_secret \
  -e DB_PASSWORD=your_production_db_password \
  streeluxcity-api:prod
```

## 🔍 Monitoring & Observability

### Health Checks
- **Application Health**: `GET /api/v1/health`
- **Database Health**: Included in health endpoint
- **Custom Health Indicators**: Database connectivity, email service

### Logging
- Structured logging with SLF4J
- Log levels configurable via environment
- Request/response logging for debugging

### Metrics
- Spring Boot Actuator endpoints available
- Custom metrics for business operations
- Performance monitoring ready

## 🛡️ Security

### Authentication Flow
1. User registration with email verification
2. JWT token generation on login
3. Token validation on each request
4. Refresh token mechanism for long sessions
5. Role-based access control enforcement

### Security Features
- Password encryption with BCrypt
- JWT token expiration and refresh
- CSRF protection enabled
- CORS configuration for frontend integration
- Input validation and sanitization
- Rate limiting ready for implementation

### Best Practices
- Never expose sensitive data in logs
- Use HTTPS in production
- Regular JWT secret rotation
- Database connection security
- Email service security (app passwords)

## 📈 Performance

### Database Optimization
- HikariCP connection pooling
- JPA query optimization
- Index creation for frequently queried fields
- Lazy loading for relationships

### Caching Strategy
- Spring Cache integration
- Product catalog caching
- User session caching
- Configurable cache providers

### Scalability Features
- Stateless JWT authentication
- Database connection pooling
- Efficient query patterns
- Pagination for large datasets

## 🤝 Contributing

### Development Setup
1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Make your changes and add tests
4. Run the test suite: `mvn test`
5. Commit your changes: `git commit -am 'Add some feature'`
6. Push to the branch: `git push origin feature/your-feature`
7. Submit a pull request

### Code Standards
- Follow Spring Boot best practices
- Use proper Java naming conventions
- Add comprehensive JavaDoc comments
- Include unit tests for new features
- Maintain backward compatibility

### Testing Guidelines
- Write unit tests for all business logic
- Include integration tests for API endpoints
- Test security configurations
- Validate input/output contracts

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## 📞 Support

For support and questions:
- **API Documentation**: [Swagger UI](http://localhost:8080/swagger-ui.html)
- **Email**: support@streeluxcity.shop
- **Issues**: [GitHub Issues](https://github.com/jackson951/api.streetluxcity/issues)

## 🔄 Changelog

### Version 1.0.0
- Initial release with complete e-commerce functionality
- JWT authentication and authorization
- Product, cart, and order management
- Payment integration ready
- Comprehensive API documentation

### Recent Updates
- Added delivery fee calculation
- Enhanced order tracking system
- Improved email service configuration
- Added comprehensive error handling
- Enhanced security configurations

## 🏆 Features Roadmap

- [ ] Advanced search and filtering
- [ ] Product reviews and ratings
- [ ] Wishlist functionality
- [ ] Advanced analytics dashboard
- [ ] Mobile app integration
- [ ] Multi-currency support
- [ ] Advanced caching strategies
- [ ] Microservices architecture support

---

**Built with ❤️ using Spring Boot**

For more information, visit our [API Documentation](http://localhost:8080/swagger-ui.html) or check out our [GitHub Repository](https://github.com/jackson951/api.streetluxcity).