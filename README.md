# EasyMoney

EasyMoney is a simple banking application backend built with **Spring Boot / JEE**.  
This project provides core banking functionalities, including user management, account management, transactions, and currency exchanges. It exposes RESTful APIs that can be consumed by a frontend application.

---

## Features

- **User Management**
  - Create, read, and manage users with personal information (username, email, phone, age)
  
- **Account Management**
  - Create accounts for users
  - Track balances in different currencies
  
- **Transactions**
  - Deposit and withdraw funds
  - Transfer money between accounts
  - Dynamic balance calculation
  
- **Currency Exchange**
  - Convert between currencies at specified rates
  - Track all exchanges per account
  
- **REST API**
  - Fully functional endpoints for all operations
  - Easy integration with frontend applications
  
- **Testing**
  - Unit and integration tests ensure reliability of core banking features

---

## API Endpoints

### Users

- `POST /api/users` – Create a new user  
- `GET /api/users` – Get all users  
- `GET /api/users/{uuid}` – Get a specific user by UUID  

### Accounts

- `POST /api/accounts?userUuid={uuid}&currency={currency}` – Create an account for a user  
- `GET /api/accounts/{accountId}` – Get account by ID  
- `GET /api/accounts/user/{uuid}` – Get all accounts for a user  

### Transactions

- `POST /api/transactions/deposit?accountId={id}&amount={amount}&description={desc}` – Deposit funds  
- `POST /api/transactions/withdraw?accountId={id}&amount={amount}&description={desc}` – Withdraw funds  
- `POST /api/transactions/transfer?fromAccountId={id1}&toAccountId={id2}&amount={amount}&description={desc}` – Transfer funds  
- `GET /api/transactions/account/{accountId}/balance` – Get account balance  

### Currency Exchange

- `POST /api/exchanges?accountId={id}&from={currency}&to={currency}&rate={rate}&amount={amount}` – Create an exchange  
- `GET /api/exchanges/account/{accountId}` – Get exchanges for an account  

---

## Technologies

- Java 17 / 21  
- Spring Boot / JEE  
- Hibernate / JPA  
- H2 / PostgreSQL (configurable)  
- Maven for build and dependency management  
- JUnit & Spring Test for unit and integration testing  

---

## Getting Started

1. Clone the repository:

```bash
git clone https://github.com/yourusername/easymoney.git
cd easymoney
```

2. Build the project:

```bash
mvn clean install
```

3. Run the application:

```bash
mvn spring-boot:run
```

Access the REST API at http://localhost:8080/api/
