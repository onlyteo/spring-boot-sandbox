# Spring Boot OAuth2 Authorization Server Extended

This example shows how to extend the Spring Security Authorization Server with production ready features.

## Architecture

```mermaid
graph TD
    subgraph Frontend
        A[OAuth2 Client]
    end
    subgraph Authorization Server
        X[OAuth2 Login]
    end

    A:::spring <-. Login Redirect .-> X:::oauth2
    A:::spring -- Fetch Token --> X:::oauth2
    
    classDef spring fill:#80ea6e,stroke:#000000,color:#000000
    classDef oauth2 fill:#c98979,stroke:#000000,color:#000000
```

### Authorization Server
The Authorization Server is an OAuth2 Authorization Server application based on Spring Boot and the
[spring-security-oauth2-authorization-server](https://spring.io/projects/spring-authorization-server) project.

```bash
../gradlew :spring-boot-oauth2-authorization-server-extended:authorization-server:bootRun
```

Look at the `WebSecurityConfig` and the `AuthorizationServerConfig` class, as well as the `application.yml` files for
more details on the security configuration.

### Frontend
This is a Thymeleaf webapp based on Spring Boot and with an OAuth2 Client security configuration. This application
is only used to invoke a OAuth2 login flow together with the OAuth2 Authorization Server.

```bash
../gradlew :spring-boot-oauth2-authorization-server-extended:frontend:bootRun
```
