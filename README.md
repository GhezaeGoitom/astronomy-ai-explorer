# Astronomy AI Explorer

Astronomy AI Explorer is a full-stack application for exploring
astronomy data through a REST API and an Angular web interface. The
backend is built with Spring Boot and PostgreSQL, with JWT-based
authentication, role-based authorization, and semantic search
infrastructure using `pgvector`.

## Repositories

-   **Backend:** This repository
-   **Frontend (Angular):**
    [astronomy-ai-explorer-ui](https://github.com/GhezaeGoitom/astronomy-ai-explorer-ui)

## Current Features

### Astronomy data API

The backend is organized around REST endpoints and persistence for
astronomy entities, including:

-   Universes
-   Galactic superclusters and galactic clusters
-   Galaxies
-   Solar systems
-   Stars
-   Planets
-   Supernovas
-   Black holes

The API uses DTOs to separate request/response data from JPA entities,
and service-layer logic to handle application operations.

### Authentication and authorization

-   User login with JWT token generation.
-   Password hashing with BCrypt.
-   Spring Security integration with a JWT authentication filter.
-   Role-based access control for `GUEST`, `SCIENTIST`, and `ADMIN`.
-   Login responses include the JWT and the user's authorities.
-   Public authentication endpoints under `/api/auth/**`; protected
    endpoints require authentication and the appropriate role.

The intended access model is:  
- **Guest:** search and read access as
permitted by the API. 

- **Scientist:** access to permitted astronomy and
discovery operations.
- **Admin:** administrative access, including user
and role management.

Resource ownership---for example, ensuring a scientist can modify only
their own records---must be enforced in the service layer in addition to
URL-level role rules.

### User and role management

-   User and role entities persisted in PostgreSQL.
-   User-to-role relationship managed through a join table.
-   Endpoint to retrieve available roles: `GET /api/roles`.
-   Admin role assignment endpoint: `PUT /api/users/{id}/roles`,
    designed to replace the user's current role set with the submitted
    role IDs.

### People, discoveries, and publications

The data model includes people profiles linked to application users,
discoveries, and publication records. The design supports associating a
discovery with the person who discovered it and linking discovery
information to publication records.

### Semantic search infrastructure

-   PostgreSQL with the `pgvector` extension.
-   An embedding table for astronomy/discovery content.
-   Vector similarity index using HNSW and cosine distance.
-   Search endpoint: `GET /api/search?query={query}&limit={limit}`.

The embedding dimension currently configured in the database is 384. The
embedding-generation and semantic-search workflow depends on the
corresponding application configuration and model being available.

## Technology Stack

### Backend

-   Java
-   Spring Boot
-   Spring Web / REST APIs
-   Spring Data JPA and Hibernate
-   Spring Security
-   JWT authentication (JJWT)
-   Bean Validation
-   Lombok
-   Flyway database migrations

### Database and search

-   PostgreSQL
-   `pgvector`
-   HNSW vector index

### Frontend

-   Angular
-   TypeScript
-   Angular Signals and RxJS

See the [frontend
repository](https://github.com/GhezaeGoitom/astronomy-ai-explorer-ui)
for UI source code and frontend setup instructions.

## Architecture

The backend follows a layered structure:

``` text
Controller
   ↓
Service interface
   ↓
Service implementation
   ↓
Repository
   ↓
PostgreSQL
```

DTOs are used at API boundaries. Spring Security handles authentication
and authorization, while Flyway manages database schema migrations.

## Running Locally

### Prerequisites

-   A compatible JDK for the version configured in the project
-   PostgreSQL with `pgvector` support (or a compatible Docker setup)
-   Maven or the project's Maven wrapper
-   Node.js and npm for the Angular frontend

### Backend configuration

Configure the datasource and JWT settings in your local application
configuration. For example, the current development setup uses a
PostgreSQL database named `astronomy_ai_explorer` on port `5433`.

Required application properties include:

``` properties
spring.datasource.url=jdbc:postgresql://localhost:5433/astronomy_ai_explorer
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

app.jwt.secret=YOUR_BASE64_OR_LONG_RANDOM_SECRET
app.jwt.expiration=YOUR_TOKEN_EXPIRATION_IN_MILLISECONDS
```

Use a sufficiently long, randomly generated secret compatible with the
configured JWT signing algorithm. Do not commit real database passwords
or JWT secrets to source control.

Flyway runs database migrations at application startup, and Hibernate is
configured to validate the schema rather than create it automatically.

### Frontend

Follow the installation and run instructions in the [Angular frontend
repository](https://github.com/GhezaeGoitom/astronomy-ai-explorer-ui).

## API Notes

-   Login and registration routes are under `/api/auth/**`.
-   Semantic search: `GET /api/search?query={query}&limit={limit}`.
-   Available roles: `GET /api/roles`.
-   Admin role assignment: `PUT /api/users/{id}/roles`.

Protected endpoints require an HTTP header in this format:

``` http
Authorization: Bearer <JWT>
```

The exact endpoint permissions are determined by the backend security
configuration.

## Roadmap

Potential future enhancements include an AI astronomy assistant,
natural-language questions grounded in astronomy records, richer
semantic retrieval, and additional exploration features. These should be
treated as planned work unless implemented in the current codebase.

## Status

This project is under active development. Features, API contracts, and
setup instructions may change as development continues.
