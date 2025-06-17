# Spring Boot REST API

This example shows a React frontend and Spring Boot REST API.

## Prerequisites

* Java Runtime - e.g. [Temurin JDK](https://adoptium.net) or [OpenJDK](https://openjdk.org)
* [NodeJS Runtime](https://nodejs.org)
* [NPM](https://www.npmjs.com) or [Yarn](https://yarnpkg.com)

## Run

Start Backend application:
```bash
../../gradlew :apps:spring-boot-rest-api:backend:bootRun
```

Start Frontend API application:

```bash
../../gradlew :apps:spring-boot-rest-api:frontend-api:bootRun
```

Start Frontend application (this should open a browser window):
```bash
yarn --cwd ./frontend install
yarn --cwd ./frontend start
```

## Architecture

```mermaid
graph TD
    A[React Frontend]:::react
    B[Spring Frontend API]:::spring
    C[Spring Backend]:::spring

    A -- REST --> B
    B -- REST --> C

    classDef react fill: #58c4dc, stroke: #000000, color: #000000
    classDef spring fill: #6cb52d, stroke: #000000, color: #000000
```

### Backend

The Backend is a REST API application based on Spring Boot.

### Frontend API

The Frontend API is a REST API application based on Spring Boot.

### Frontend

The Frontend is a JavaScript web application based on ReactJS and using the React Bootstrap framework.