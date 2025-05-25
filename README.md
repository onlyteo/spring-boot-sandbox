# Spring Boot Sandbox

[![Kotlin](https://img.shields.io/badge/kotlin-2.0.20-8d53f9.svg?logo=kotlin&logoColor=8d53f9)](http://kotlinlang.org)
[![Spring Boot](https://img.shields.io/badge/spring%20boot-3.3.3-6cb52d.svg?logo=spring-boot&logoColor=6cb52d)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/gradle-stable-209bc4.svg?logo=gradle&logoColor=209bc4)](https://gradle.org)
[![TypeScript](https://img.shields.io/badge/typescript-5.2.2-3178c6.svg?logo=typescript&logoColor=3178c6)](https://www.typescriptlang.org)
[![React](https://img.shields.io/badge/react-18.3.1-58c4dc.svg?logo=react&logoColor=58c4dc)](https://react.dev)
[![Node.js](https://img.shields.io/badge/node.js-stable-417e38.svg?logo=nodedotjs&logoColor=417e38)](https://nodejs.org)
[![GitHub license](https://img.shields.io/badge/license-Apache_2.0-e97726.svg)](https://www.apache.org/licenses/LICENSE-2.0)

This repository contain sandbox projects to showcase features of the
[Spring Boot framework](https://spring.io/projects/spring-boot).

All examples are written in [Kotlin](https://kotlinlang.org) and built using [Gradle](https://gradle.org).

## Examples
Read details about the examples in their respective project roots.

* [REST API](./apps/spring-boot-rest-api)
* [OAuth2 Token Relay](./apps/spring-boot-oauth2-token-relay)
* [OAuth2 Token Exchange](./apps/spring-boot-oauth2-token-exchange)
* [OAuth2 Extended Authorization Server](./apps/spring-boot-oauth2-extended-authorization-server)
* [CQRS Kafka](./apps/spring-boot-cqrs-kafka)
* [CDC Kafka](./apps/spring-boot-cdc-kafka)
* [OTEL Observability](./apps/spring-boot-otel-observability)

## Architecture
Spring Boot is a framework for building applications for the
[JVM runtime](https://en.wikipedia.org/wiki/Java_virtual_machine). Spring Boot was created to make it easier
to build and configure application based on the [Spring framework](https://spring.io/projects/spring-framework).
It is an opinionated framework that has a focus on convention over configuration. At it's core is the
[Spring IoC-container](https://docs.spring.io/spring-framework/reference/core/beans.html) which enables the
use of [Dependency Injection](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html)
and [Aspect Oriented Programming](https://docs.spring.io/spring-framework/reference/core/aop.html).

The examples typically consists of a `Frontend` and a `Backend` application.

```mermaid
graph TD
    A[Spring Frontend]:::spring
    B[Spring Backend]:::spring

    A --> B
    
    classDef react fill: #58c4dc, stroke: #000000, color: #000000
    classDef spring fill: #6cb52d, stroke: #000000, color: #000000
```

If the frontend is a JavaScript application then there is often a `Frontend API` application.

```mermaid
graph TD
    A[React Frontend]:::react
    B[Spring Frontend API]:::spring
    C[Spring Backend]:::spring
    
    A --> B
    B --> C
    
    classDef react fill: #58c4dc, stroke: #000000, color: #000000
    classDef spring fill: #6cb52d, stroke: #000000, color: #000000
```

## Use case
Most examples implement a *"hello world"* style logic that returns a greeting message when the user inputs a name.

* A user inputs the name "John" and clicks "Submit"
* The system generates a greeting "Hello John!" back to the user
