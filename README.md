# Banking System

A Java 17 / Spring Boot REST API for creating bank accounts, retrieving account details, and recording withdrawals. Account and transaction data are stored in MySQL using Spring Data JPA.

## Features

- Create an account with account holder identity and address details.
- Retrieve account details and current balance.
- Withdraw funds when the account has a sufficient balance.
- Validate transaction requests and return application errors through a global exception handler.

> **Current scope:** New accounts start with a zero balance and `INACTIVE` status. The API currently supports withdrawals only; deposit, transfer, payment, account activation, and branch-management endpoints are not implemented.

## Tech stack

- Java 17
- Spring Boot 4.1.1
- Spring MVC, Spring Data JPA, and Jakarta Bean Validation
- MySQL 8
- Maven
- Docker Compose

## Run with Docker Compose

Install Docker with Docker Compose, then run these commands from the project root:

```shell
./mvnw clean package -DskipTests
docker compose up --build
```

On Windows, use the Maven wrapper script:

```powershell
.\mvnw.cmd clean package -DskipTests
docker compose up --build
```

The API listens on `http://localhost:8080`. MySQL is exposed on `localhost:3307` and the Compose file creates a persistent `banking-data` volume. The sample Compose configuration uses the development credentials `root` / `root`; do not use these credentials in a production environment.

Before creating an account, add a branch to the database. The project does not currently include an endpoint to create branches. After the application has started, one way to add a development branch is:

```shell
docker compose exec banking-db mysql -uroot -proot banking -e "INSERT INTO branch (branch_name, branch_status) VALUES ('Main Branch', 'ACTIVE');"
```

Account creation requires the ID of an existing branch (the first inserted branch will normally have ID `1`).

## Run locally

You need Java 17 and a reachable MySQL database. For example, start only the MySQL service from Compose:

```shell
docker compose up -d banking-db
```

Configure the connection and start the application. In PowerShell:

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:mysql://localhost:3307/banking"
$env:SPRING_DATASOURCE_USERNAME = "root"
$env:SPRING_DATASOURCE_PASSWORD = "root"
.\mvnw.cmd spring-boot:run
```

On macOS or Linux, use `./mvnw spring-boot:run` after exporting the same `SPRING_DATASOURCE_*` variables.

## API

All endpoints are prefixed with `/api/v1`.

### Create an account

`POST /api/v1/accounts` — returns `201 Created`.

```json
{
  "legalPersonName": "Jane Doe",
  "dateOfBirth": "1990-04-15",
  "identityType": "Passport",
  "identityNumber": "P1234567",
  "phoneNumber": "+1-555-0100",
  "address": {
    "street": "123 Main Street",
    "city": "Toronto",
    "state": "Ontario",
    "postalCode": "M5V 2T6",
    "country": "Canada"
  },
  "branchId": 1,
  "currency": "CAD",
  "accountType": "SAVINGS"
}
```

Supported `currency` values are `EUR`, `USD`, `CAD`, and `INR`. Supported `accountType` values are `SAVINGS` and `CHECKING`.

```shell
curl -X POST http://localhost:8080/api/v1/accounts \
  -H "Content-Type: application/json" \
  -d '{"legalPersonName":"Jane Doe","dateOfBirth":"1990-04-15","identityType":"Passport","identityNumber":"P1234567","phoneNumber":"+1-555-0100","address":{"street":"123 Main Street","city":"Toronto","state":"Ontario","postalCode":"M5V 2T6","country":"Canada"},"branchId":1,"currency":"CAD","accountType":"SAVINGS"}'
```

The response contains the generated `accountId` and `accountNumber`; use the `accountId` in the following endpoints.

### Get account details

`GET /api/v1/accounts/{accountId}` — returns `200 OK` with account holder, branch, status, currency, and balance details.

```shell
curl http://localhost:8080/api/v1/accounts/1
```

### Withdraw funds

`POST /api/v1/accounts/{accountId}/withdrawals` — returns `200 OK` when successful. The amount must be greater than zero and no greater than the available balance. As newly created accounts have a zero balance and there is no deposit endpoint yet, a withdrawal requires a balance to have been funded separately.

```json
{
  "amount": 25.00,
  "currency": "CAD",
  "transactionType": "WITHDRAW"
}
```

```shell
curl -X POST http://localhost:8080/api/v1/accounts/1/withdrawals \
  -H "Content-Type: application/json" \
  -d '{"amount":25.00,"currency":"CAD","transactionType":"WITHDRAW"}'
```

Other transaction types are not supported by this endpoint.

## Tests

Run the test suite with:

```shell
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

## Database note

The current JPA setting is `spring.jpa.hibernate.ddl-auto=create`, which recreates the schema when the application starts and can erase existing database data. Change this setting to an appropriate migration-based or non-destructive mode before using this project with data that must be retained.
