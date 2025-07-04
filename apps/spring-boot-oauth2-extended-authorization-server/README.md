# Spring Boot OAuth2 Extended Authorization Server

This example shows how to extend the Spring Security Authorization Server with production ready features.

By default, Spring Security stores state in-memory. This examples adds persistent state stores. It will also
harden the security setup.

Extended features:
* Storing user credentials in the database.
* Storing OAuth2 registered clients in the database.
* Storing OAuth2 authorizations in the database.
* Storing OAuth2 authorization consents in the database.
* Using external RSA keys for OAuth2 token signing.
* Requiring PKCE for OAuth2 clients using the Authorization Code Grant flow.

## Prerequisites

* Java Runtime - e.g. [Temurin JDK](https://adoptium.net) or [OpenJDK](https://openjdk.org)

## Run

Start Authorization Server application:
```bash
../../gradlew :apps:spring-boot-oauth2-extended-authorization-server:authorization-server:bootRun
```

Start Backend application:
```bash
../../gradlew :apps:spring-boot-oauth2-extended-authorization-server:backend:bootRun
```

Start Frontend API application:

```bash
../../gradlew :apps:spring-boot-oauth2-extended-authorization-server:frontend-api:bootRun
```

## Architecture

```mermaid
graph TD
    subgraph Authorization Server
        X[OAuth2 Login]:::oauth2
    end
    subgraph Apps
        A[Spring Frontend]:::spring
        B[Spring Backend]:::spring
    end

    A -- REST --> B
    A -- Fetch Token --> X
    A <-. Login Redirect .-> X

    classDef react fill: #58c4dc, stroke: #000000, color: #000000
    classDef spring fill: #6cb52d, stroke: #000000, color: #000000
    classDef oauth2 fill: #c98979, stroke: #000000, color: #000000
```

### Authorization Server
The Authorization Server is an OAuth2 Authorization Server application based on Spring Boot and the
[spring-security-oauth2-authorization-server](https://spring.io/projects/spring-authorization-server) project.

Look at the `WebSecurityConfig` and the `AuthorizationServerConfig` class, as well as the `application.yml` files for
more details on the security configuration.

### Backend
This is a Spring Boot app with a REST API, which is protected as an OAuth2 Resource Server.

### Frontend
This is a Thymeleaf webapp based on Spring Boot and with an OAuth2 Client security configuration. This application
is only used to invoke a OAuth2 login flow together with the OAuth2 Authorization Server.
