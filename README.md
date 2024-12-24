# Project Overview

- A project consisting of 2 Spring Boot apps: `runs` and `users`
- A discovery server (Eureka) to register the services
- A gateway server to route the requests to the services
- Task to do: Move each project configuration to the configuration server


### How to run the project
clone the project 
start the docker compose file
```bash
docker-compose up
```
Start all the apps: Config-server, Eureka, Gateway, Runs, Users
