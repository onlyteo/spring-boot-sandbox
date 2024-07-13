# Spring Boot Sandbox
This repository contain sandbox projects to showcase features of the Spring Boot framework.

## Use case
Most examples implement a "hello world" style logic that returns a greeting message when the user inputs a name.

* A user inputs the name "John" and clicks "Submit"
* The system generates a greeting "Hello John!" back to the user

The examples typically consists of a `Frontend` and a `Backend` application. If the frontend is a JavaScript
application then there is often a `Frontend API` application.

```mermaid
graph TD
    A1[Spring Boot Frontend]
    B1[Spring Boot Backend]

    A2[React Frontend]
    B2[Spring Boot Frontend API]
    C2[Spring Boot Backend]

    A1:::spring --> B1:::spring
    
    A2:::react --> B2:::spring
    B2:::spring --> C2:::spring
    
    classDef react fill: #58c4dc, stroke: #000000, color: #000000
    classDef spring fill: #80ea6e, stroke: #000000, color: #000000
    classDef oauth2 fill: #c98979, stroke: #000000, color: #000000
```

## Examples
Read details about the examples in their respective project roots.

* [OAuth2 Token Relay](./spring-boot-oauth2-token-relay)
* [OAuth2 Token Exchange](./spring-boot-oauth2-token-exchange)
* [OAuth2 Extended Authorization Server](./spring-boot-oauth2-extended-authorization-server)
* [CDC Event Streaming](./spring-boot-cdc-event-streaming)
