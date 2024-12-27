# Project Overview

- A project consisting of 2 Spring Boot apps: `runs` and `users`
- A discovery server (Eureka) to register the services
- A gateway server to route the requests to the services
- Task to do:<br>
1. Move each project configuration to the configuration server
2. externalize Run Class to common repo
3. use Mongo for Ranking model
4. Util class to Convert class to DTO
5. notification app
6. Exception Handling
7. JWT and security


### How to run the project
clone the project 
start the docker compose file
```bash
docker-compose up
```
Start all the apps: Config-server, Eureka, Gateway, Runs, Users
