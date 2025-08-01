# Simple Spring Boot App

This is a simple Spring Boot application that demonstrates basic REST API functionality.

## Description

A minimal Spring Boot application that exposes a single endpoint returning "hello world". This project is designed to showcase the basic structure of a Spring Boot application with essential dependencies.

## Features

- REST API endpoint at `/hello`
- Spring Boot Actuator for monitoring
- Spring Security for basic authentication
- Spring Boot DevTools for development
- H2 in-memory database
- Lombok for reducing boilerplate code

## Prerequisites

- Java 24
- Maven 3.5+

## Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Validation
- Spring Boot Starter Actuator
- Spring Boot DevTools
- H2 Database
- Lombok
- Spring Boot Starter Test
- Spring Security Test

## Getting Started

### Clone the repository

```bash
git clone <repository-url>
cd simple-spring-boot-app
```

### Build the application

```bash
./mvnw clean package
```

### Run the application

```bash
./mvnw spring-boot:run
```

Or run the JAR file directly:

```bash
java -jar target/simple-spring-boot-app-0.0.1-SNAPSHOT.jar
```

## API Endpoints

- `GET /hello` - Returns "hello world"

Note: Spring Security is enabled by default, so you'll need to authenticate to access the endpoint.

## Actuator Endpoints

- `GET /actuator/health` - Application health information
- `GET /actuator/info` - Application information

## Configuration

The application uses the following configuration in `src/main/resources/application.properties`:

- `spring.application.name=Simple App`

## Testing

Run the tests with:

```bash
./mvnw test
```

## Building with Native Image

This project is configured to build with GraalVM native image support:

```bash
./mvnw native:compile -Pnative
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Built with Spring Boot 3.5.4