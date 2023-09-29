# Spring Boot OAuth2 Authorization Server Extended

This is a example of an OAuth2 protected JavaScript based frontend with a Spring Boot REST API.

The example consists of three applications:

### Authorization Server
This is an OAuth2 Authorization Server application based on the `spring-security-oauth2-authorization-server` project.

Look at the `WebSecurityConfig` and the `AuthorizationServerConfig` class, as well as the `application.yml` file for
more details on the security configuration.

### Client
This is a Thymeleaf webapp based on Spring Boot and with an OAuth2 Client security configuration. This application
is only used to invoke a OAuth2 login flow together with the OAuth2 Authorization Server.
