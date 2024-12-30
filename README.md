# Project Overview

This project consists of multiple Spring Boot applications and supporting services. Its main goal is to provide a platform for tracking runs and ranking users based on their performance.

- **`run-service`**: Manages operations regarding run tracking.
- **`user-service`**: Manages operations related to the users of the application.
- **`statistics-service`**: Consumes Kafka topics for new runs and updates user rankings.
- **Discovery Server (Eureka)**: Registers and discovers services.
- **Gateway Server**: Routes requests to the appropriate services.
- **Configuration Server**: Centralizes configuration for all services.


## Tasks To Do

1. ~~Move each project configuration to the configuration server~~
2. Externalize `Run` class to a common repository
3. ~~Apply dependency inversion and controller advice to other services~~
4. Use MongoDB for the ranking model
5. Create a utility class to convert entities to DTOs
6. Develop a notification application
7. ~~Implement exception handling~~
8. Implement JWT and security
9. Add Swagger documentation to all services

## Bugs

- ~~Check user existence when a new run is created~~

## How to Run the Project

1. Clone the project repository.
2. Start the Docker Compose file:

    ```bash
    docker-compose up
    ```

3. Start all the applications in the following order:
  - Config-server
  - Eureka
  - Gateway
  - `run-service`
  - `user-service`
  - `statistics-service`