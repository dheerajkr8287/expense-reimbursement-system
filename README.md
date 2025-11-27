# Employee Expense Reimbursement System

A simple REST API application for managing employee expense claims built with Java Spring Boot.

## Features

- Create expense claims
- View employee claims
- Approve/Reject claims
- Generate PDF reports

## Technologies

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database
- iText PDF
- Maven

## Getting Started

### Prerequisites
- Java 17+
- Maven 3.6+

### Installation

1. Clone the repository
```bash
git clone https://github.com/dheerajkr8287/expense-reimbursement-system.git
cd expense-reimbursement-system
```

2. Run the application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Access H2 Console
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:expensedb
Username: sa
Password: (empty)
```

## API Endpoints

### Create Claim
```
POST /employees/{id}/claims
Body: {"description": "Office supplies", "amount": 150.00}
```

### Get Employee Claims
```
GET /employees/{id}/claims
```

### Update Claim Status
```
PUT /claims/{claimId}/status
Body: {"status": "Approved"}
```

### Generate Report
```
GET /reports/claims?start_date=2025-01-01&end_date=2025-01-31
GET /reports/claims?start_date=2025-01-01&end_date=2025-01-31&format=pdf
```

## Testing with cURL

```bash
# Create a claim
curl -X POST http://localhost:8080/employees/1/claims \
  -H "Content-Type: application/json" \
  -d '{"description": "Team lunch", "amount": 125.00}'

# Get claims
curl http://localhost:8080/employees/1/claims

# Update status
curl -X PUT http://localhost:8080/claims/1/status \
  -H "Content-Type: application/json" \
  -d '{"status": "Approved"}'

# Download PDF
curl "http://localhost:8080/reports/claims?start_date=2025-01-01&end_date=2025-01-31&format=pdf" -o report.pdf
```

## Project Structure

```
src/main/java/com/company/expensereimbursement/
├── entity/          # Database models
├── repository/      # Data access layer
├── service/         # Business logic
├── controller/      # REST endpoints
├── dto/             # Request/Response objects
└── exception/       # Error handling
```

## Sample Data

The application comes with pre-loaded sample data:
- 3 Employees (Engineering, Marketing, Sales)
- 5 Sample expense claims

## Author

Your Name
- GitHub: [@dheerajkr8287](https://github.com/dheerajkr8287)

