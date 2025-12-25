# Midas Core - Real-time Distributed Ledger System

This project is a high-performance financial ledger system developed as part of the **J.P. Morgan Chase & Co. Software Engineering Virtual Experience**. It processes real-time transactions using an event-driven architecture.

## 🚀 Key Features
- **Event-Driven Processing:** Uses **Apache Kafka** to handle asynchronous transaction streams.
- **Microservices Integration:** Calls an external **RESTful Incentive API** (Port 8080) to calculate transaction bonuses.
- **Data Integrity:** Ensures ACID properties and data consistency using **Spring Data JPA** and **H2 Database**.
- **Automated Testing:** End-to-end validation using **Embedded Kafka** and JUnit 5.

## 🛠 Tech Stack
- **Backend:** Java, Spring Boot, Spring Kafka, Spring Data JPA
- **Messaging:** Apache Kafka
- **Database:** H2 (Relational)
- **Testing:** JUnit 5, Embedded Kafka


## 📋 System Architecture
1. **Producer:** Sends transaction data from files to Kafka topics.
2. **Kafka Broker:** Manages the message stream and ensures fault tolerance.
3. **Consumer (Transaction Listener):** Listens for new transactions, validates balances, and calls the Incentive API.
4. **REST API:** A separate microservice that provides incentive amounts for each transaction.
5. **Database:** Stores the updated user balances.

## ⚙️ Setup & Installation

1. Clone the repository:
   ```bash
   git clone [https://github.com/Jitendra-Kumar7078/forage-midas.git](https://github.com/Jitendra-Kumar/forage-midas.git)

2. Run the Incentive API (Required for Task 5):

Bash

java -jar transaction-incentive-api.jar

3. Build and Run the Spring Boot application using Maven:

Bash

mvn clean install
mvn spring-boot:run

4. Run Tests:

Bash

mvn test
