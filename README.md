# Outbox Pattern Demonstration Project

This project demonstrates the implementation of the Outbox Pattern for reliable message delivery in a distributed system. It consists of two Spring Boot applications that work together to ensure messages are reliably sent to Kafka even in the face of failures.

## What is the Outbox Pattern?

The Outbox Pattern is a design pattern used to solve the problem of reliably publishing messages/events to a message broker while updating a database, ensuring consistency between the database and the messages sent.

The pattern works as follows:
1. Instead of directly publishing messages to a message broker, the application first stores them in an "outbox" table in the database
2. A separate process periodically checks the outbox table for unsent messages and publishes them to the message broker
3. After successful publication, the messages are marked as sent in the outbox table

This approach ensures that even if the message broker is temporarily unavailable, no messages are lost, as they are safely stored in the database.

## Project Components

This project consists of two main applications:

### 1. Outbox Save Application (Port 8080)

This application:
- Provides a web interface for users to submit truck event messages
- Stores these messages in a PostgreSQL database using the outbox pattern
- Does not directly publish messages to Kafka

### 2. Outbox Send Application (Port 8081)

This application:
- Periodically checks the database for unsent messages (every 5 seconds)
- Publishes these messages to a Kafka topic
- Updates the message status in the database after successful publication

## Setup and Running Instructions

### Prerequisites

- Docker and Docker Compose
- Java 17 or higher
- Maven

### Step 1: Start the Infrastructure

Run the following command to start PostgreSQL, Kafka, and Zookeeper:

```bash
docker-compose up -d
```

This will start:
- PostgreSQL database on port 5432
- Zookeeper on port 2181
- Kafka on port 9092

### Step 2: Start the Applications

You can start the Spring Boot applications

1. Start the Outbox Save application
2. Start the Outbox Send application

## Usage

1. Access the web interface at http://localhost:8080
2. Fill out the truck message form with the following information:
   - Message Header
   - Business Unit Code
   - License Plate
   - Color
   - Event Type
3. Click "Send Message"

## Message Flow

1. When you submit the form, the Outbox Save application stores the message in the PostgreSQL database
2. The Outbox Send application, which runs on a 5-second schedule, detects the new message in the database
3. The Outbox Send application publishes the message to a Kafka topic
4. After successful publication, the message is marked as "SENT" in the database

This ensures that even if Kafka is temporarily unavailable, the message will be stored in the database and sent once Kafka becomes available again.

## Project Structure

- `outbox-core`: Contains shared entities and repositories
- `outbox-save`: The application that saves messages to the database
- `outbox-send`: The application that sends messages to Kafka
- `docker-compose.yml`: Configuration for PostgreSQL, Kafka, and Zookeeper
