# ReciperBackend

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.2-brightgreen)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-latest-blue)](https://www.postgresql.org/)

## Overview

ReciperBackend is a robust server-side application designed to make cooking and shopping easier for everyone from novice cooks to passionate personal chefs. The backend provides a comprehensive API for a mobile frontend application, allowing users to create, discover, like, and save recipes. Additionally, it integrates with an AI service that generates recipes from descriptions and prompts.

## Features

- **Recipe Management**: Create, read, update, and delete recipes
- **AI Recipe Generation**: Integration with AI service for generating recipes from text descriptions
- **Favorites System**: Like and save favorite recipes for quick access
- **Ingredient Management**: Add and track ingredients
- **Fridge Inventory**: Save items to virtual fridge for inventory tracking
- **Shopping List Generation**: Automatically generate shopping lists based on selected recipes
- **Advanced Sorting**: Multiple sorting options for recipes and ingredients
- **Secure Authentication**: JWT and Basic Authentication support

## Tech Stack

- **Framework**: Spring Boot 3.3.2
- **Language**: Java 21
- **Database**: PostgreSQL
- **Key Dependencies**:
    - Spring Data JPA
    - Spring Security
    - Spring Web
- **API Type**: RESTful API

## Getting Started

### Prerequisites

- Docker and Docker Compose
- Network access to Docker Hub
- A compatible mobile frontend application

### Installation and Setup

1. Clone the repository
   ```bash
   git clone https://github.com/your-username/ReciperBackend.git
   cd ReciperBackend
   ```

2. Run with Docker Compose
   ```bash
   docker compose up --build
   ```

3. The application will be available at:
    - API: http://localhost:8080
    - Swagger UI: http://localhost:8080/swagger-ui/index.html

### Configuration

Configuration is managed through environment variables in the Docker Compose file. Key configurations include:

- Database connection parameters
- JWT secret and expiration
- AI service connection details

## API Documentation

Complete API documentation is available via Swagger UI at:
```
http://localhost:8080/swagger-ui/index.html
```

### Authentication

The API supports two authentication methods:
- **JWT (JSON Web Token)**: For regular API usage
- **Basic Authentication**: Alternative authentication method

To authenticate:
1. Send credentials to the authentication endpoint
2. Include the received JWT token in the Authorization header for subsequent requests

## Deployment

The application is containerized and can be deployed to any platform that supports Docker:

- AWS ECS/EKS
- Google Cloud Run/GKE
- Azure Container Instances/AKS
- Self-hosted Docker environments

### Deployment Instructions

1. Build the Docker image:
   ```bash
   docker build -t reciper-backend:latest .
   ```

2. Push to your container registry:
   ```bash
   docker tag reciper-backend:latest your-registry/reciper-backend:latest
   docker push your-registry/reciper-backend:latest
   ```

3. Deploy using your platform's container deployment method

## Upcoming Features

- Administrator dashboard for content management and user administration
- Enhanced analytics for recipe popularity
- Additional integrations with external services


## Contact:
### kamil.sakowicz.dev@gmail.com
