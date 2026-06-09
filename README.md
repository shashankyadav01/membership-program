# FirstClub Membership Program

A Spring Boot based membership management application that allows users to subscribe to membership plans, automatically qualify for membership tiers based on order activity, and manage their subscriptions through upgrades, downgrades, and cancellations.

---

# 1. Overview

FirstClub Membership Program is a loyalty and subscription management platform designed to reward customers based on their purchasing activity.

Users can subscribe to membership plans, receive benefits such as discounts and free delivery, and automatically progress through membership tiers as their order activity increases.

The system supports:

* Membership Plan Management
* Membership Tier Management
* Membership Subscription
* Membership Upgrade
* Membership Downgrade
* Membership Cancellation
* Automatic Tier Evaluation
* Order-Based Tier Promotion
* Tier History Tracking
* Optimistic Locking
* Global Exception Handling
* Swagger API Documentation

## Tech Stack

| Layer                 | Technology                    |
| --------------------- | ----------------------------- |
| Backend               | Java 21                       |
| Framework             | Spring Boot 3.5               |
| Database              | PostgreSQL                    |
| ORM                   | Spring Data JPA / Hibernate   |
| API Documentation     | Swagger OpenAPI 3             |
| Build Tool            | Maven                         |
| Boilerplate Reduction | Lombok                        |
| Concurrency Handling  | Optimistic Locking (@Version) |

---

# 2. Architecture Decisions

## Why this structure?

The project follows a clean layered architecture:

Controller Layer
↓
Service Layer
↓
Repository Layer
↓
PostgreSQL Database

Each domain object has its own:

* Entity
* Repository
* Service
* Controller

This separation keeps business logic isolated from HTTP concerns and database operations.

## Key Design Decisions

| Decision                     | Reasoning                                            |
| ---------------------------- | ---------------------------------------------------- |
| Service Layer Business Logic | Keeps controllers thin and easy to maintain          |
| Spring Data JPA              | Reduces boilerplate CRUD code                        |
| PostgreSQL                   | Reliable relational database with strong consistency |
| Swagger Integration          | Simplifies API testing and documentation             |
| DTO Responses                | Prevents exposing JPA entities directly              |
| Global Exception Handler     | Provides clean API error responses                   |
| Tier History Tracking        | Maintains audit logs of membership changes           |
| Optimistic Locking           | Prevents concurrent update conflicts                 |

## What I Intentionally Added

### Automatic Tier Evaluation

Instead of manually assigning membership tiers, users can be evaluated automatically based on:

* Total Orders
* Monthly Order Value
* Customer Cohort

Example:

```java
if(user.getTotalOrders() >= 50)
    return platinum;

if(user.getTotalOrders() >= 20)
    return gold;

return silver;
```

### Tier History Auditing

Whenever a user's tier changes:

SILVER → GOLD

or

GOLD → PLATINUM

the system stores:

* Previous Tier
* New Tier
* Timestamp
* User

This provides a complete audit trail.

### Optimistic Locking

Implemented using:

```java
@Version
private Long version;
```

This prevents multiple concurrent updates from overwriting each other.

---

# 3. Running Locally

## Prerequisites

* Java 21
* Maven 3.9+
* PostgreSQL 15+
* Git

## Clone Repository

```bash
git clone <repository-url>
cd membership-program
```

## Configure Database

Update:

```properties
src/main/resources/application.properties
```

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/firstclub

spring.datasource.username=postgres

spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
```

## Build Project

```bash
mvn clean install
```

## Run Application

```bash
mvn spring-boot:run
```

Application URL:

```text
http://localhost:8081
```

---

# 4. API Documentation

Swagger UI:

```text
http://localhost:8081/swagger-ui/index.html
```

OpenAPI Specification:

```text
http://localhost:8081/v3/api-docs
```

---

# 5. API Reference

All endpoints return JSON.

## Membership APIs

### Create Membership Plan

POST /api/memberships/plans

Request

```json
{
  "planName": "MONTHLY",
  "durationMonths": 1,
  "price": 99,
  "discountPercentage": 5,
  "freeDelivery": true,
  "prioritySupport": false
}
```

Response

```json
{
  "id": 1,
  "planName": "MONTHLY",
  "durationMonths": 1,
  "price": 99,
  "discountPercentage": 5,
  "freeDelivery": true,
  "prioritySupport": false
}
```

### Get All Plans

GET /api/memberships/plans

### Create Membership Tier

POST /api/memberships/tiers

Request

```json
{
  "tierName": "SILVER",
  "discountPercentage": 5,
  "freeDelivery": true,
  "prioritySupport": false,
  "minimumOrders": 5,
  "minimumOrderValue": 1000,
  "cohort": "REGULAR"
}
```

Response

```json
{
  "id": 1,
  "tierName": "SILVER",
  "discountPercentage": 5,
  "freeDelivery": true,
  "prioritySupport": false,
  "minimumOrders": 5,
  "minimumOrderValue": 1000,
  "cohort": "REGULAR"
}
```

### Get All Tiers

GET /api/memberships/tiers

---

## Subscription APIs

### Subscribe

POST /api/subscriptions/subscribe

Request

```json
{
  "userId": 1,
  "planId": 1,
  "tierId": 1
}
```

Response

```json
{
  "id": 1,
  "status": "ACTIVE"
}
```

### Upgrade Membership

PUT /api/subscriptions/upgrade

### Downgrade Membership

PUT /api/subscriptions/downgrade

### Cancel Membership

PUT /api/subscriptions/cancel/{subscriptionId}

### Get Subscription

GET /api/subscriptions/{subscriptionId}

Returns DTO response:

```json
{
  "subscriptionId": 1,
  "userName": "Shashank",
  "planName": "MONTHLY",
  "tierName": "GOLD",
  "status": "ACTIVE",
  "expiryDate": "2026-07-09"
}
```

---

# 6. Error Handling

Global exception handling is implemented using:

```java
@RestControllerAdvice
```

Example response:

```json
{
  "message": "User not found"
}
```

Instead of exposing stack traces.

Common Error Responses:

| Code | When                  |
| ---- | --------------------- |
| 400  | Invalid Request       |
| 404  | Resource Not Found    |
| 500  | Internal Server Error |

---

# 7. Project Structure

```text
C:.
│   pom.xml
│   
├───.github
│   └───modernize
│       └───java-upgrade
│           │   .gitignore
│           │   
│           └───hooks
│               └───scripts
│                       recordToolUse.ps1
│                       recordToolUse.sh
│                       
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───firstclub
│   │   │           │   MembershipApplication.java
│   │   │           │   
│   │   │           ├───config
│   │   │           ├───controller
│   │   │           │       MembershipController.java
│   │   │           │       SubscriptionController.java
│   │   │           │       
│   │   │           ├───dto
│   │   │           │       MembershipResponse.java
│   │   │           │       SubscribeRequest.java
│   │   │           │       UpgradeTierRequest.java
│   │   │           │       
│   │   │           ├───entity
│   │   │           │       MembershipPlan.java
│   │   │           │       MembershipTier.java
│   │   │           │       Order.java
│   │   │           │       Subscription.java
│   │   │           │       TierHistory.java
│   │   │           │       User.java
│   │   │           │       
│   │   │           ├───exception
│   │   │           │       GlobalExceptionHandler.java
│   │   │           │       
│   │   │           ├───repository
│   │   │           │       MembershipPlanRepository.java
│   │   │           │       MembershipTierRepository.java
│   │   │           │       OrderRepository.java
│   │   │           │       SubscriptionRepository.java
│   │   │           │       TierHistoryRepository.java
│   │   │           │       UserRepository.java
│   │   │           │       
│   │   │           └───service
│   │   │                   MembershipService.java
│   │   │                   OrderService.java
│   │   │                   SubscriptionService.java
│   │   │                   TierService.java
│   │   │                   
│   │   └───resources
│   │           application.properties
│   │           
│   └───test
│       └───java
└───target
    │   membership-program-0.0.1-SNAPSHOT.jar
    │   membership-program-0.0.1-SNAPSHOT.jar.original
    │   
    ├───classes
    │   │   application.properties
    │   │   
    │   └───com
    │       └───firstclub
    │           │   MembershipApplication.class
    │           │   
    │           ├───controller
    │           │       MembershipController.class
    │           │       SubscriptionController.class
    │           │       
    │           ├───dto
    │           │       MembershipResponse$MembershipResponseBuilder.class
    │           │       MembershipResponse.class
    │           │       SubscribeRequest.class
    │           │       UpgradeTierRequest.class
    │           │       
    │           ├───entity
    │           │       MembershipPlan$MembershipPlanBuilder.class
    │           │       MembershipPlan.class
    │           │       MembershipTier$MembershipTierBuilder.class
    │           │       MembershipTier.class
    │           │       Order$OrderBuilder.class
    │           │       Order.class
    │           │       Subscription$SubscriptionBuilder.class
    │           │       Subscription.class
    │           │       TierHistory$TierHistoryBuilder.class
    │           │       TierHistory.class
    │           │       User$UserBuilder.class
    │           │       User.class
    │           │       
    │           ├───exception
    │           │       GlobalExceptionHandler.class
    │           │       
    │           ├───repository
    │           │       MembershipPlanRepository.class
    │           │       MembershipTierRepository.class
    │           │       OrderRepository.class
    │           │       SubscriptionRepository.class
    │           │       TierHistoryRepository.class
    │           │       UserRepository.class
    │           │       
    │           └───service
    │                   MembershipService.class
    │                   OrderService.class
    │                   SubscriptionService.class
    │                   TierService.class
    │                   
    ├───generated-sources
    │   └───annotations
    ├───generated-test-sources
    │   └───test-annotations
    ├───maven-archiver
    │       pom.properties
    │       
    ├───maven-status
    │   └───maven-compiler-plugin
    │       ├───compile
    │       │   └───default-compile
    │       │           createdFiles.lst
    │       │           inputFiles.lst
    │       │           
    │       └───testCompile
    │           └───default-testCompile
    │                   createdFiles.lst
    │                   inputFiles.lst
    │                   
    └───test-classes
```

---

# 8. What I'd Do With More Time

### Improvements

* JWT Authentication
* Role-Based Authorization
* Subscription Renewal Scheduler
* Email Notifications
* Payment Gateway Integration
* Docker Deployment
* CI/CD Pipeline
* Unit Testing
* Integration Testing
* Dashboard Analytics

### Advanced Features

* Membership Expiry Alerts
* Reward Points System
* Coupon Management
* Referral Program
* Admin Dashboard
* Event-Driven Architecture with Kafka

---

# Author

**Shashank J N**

Email: [karthishashi1100@gmail.com](mailto:karthishashi1100@gmail.com)

LinkedIn:
https://www.linkedin.com/in/shashank-j-n-72982a225

GitHub:
https://github.com/shashiyadav90
