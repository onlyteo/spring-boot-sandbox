# Spring Boot REST API

This example shows a React frontend and Spring Boot REST API.

## Prerequisites

* Java Runtime - e.g. [Temurin JDK](https://adoptium.net), [OpenJDK](https://openjdk.org) or [Oracle JDK](https://www.oracle.com/java)
* [NodeJS Runtime](https://nodejs.org)
* [NPM](https://www.npmjs.com) or [Yarn](https://yarnpkg.com)
* [Docker](https://www.docker.com)

## Run

Start Backend application:
```bash
../gradlew :spring-boot-rest-api:backend:bootRun
```

Start Frontend API application:

```bash
../gradlew :spring-boot-rest-api:frontend-api:bootRun
```

Start Frontend application (this should open a browser window):
```bash
yarn --cwd ./frontend install
yarn --cwd ./frontend start
```

## Architecture

```mermaid
graph TD
    subgraph Frontend
        A[REST Client]:::react
    end
    subgraph Frontend API
        B[REST API]:::spring
    end
    subgraph Backend
        C[REST API]:::spring
    end

    A -- REST --> B
    B-- REST --> C

    classDef react fill: #087ea4, stroke: #000000, color: #000000
    classDef spring fill: #80ea6e, stroke: #000000, color: #000000
```

### Backend

The Backend is a REST API application based on Spring Boot.

### Frontend API

The Frontend API is a REST API application based on Spring Boot.

### Frontend

The Frontend is a JavaScript web application based on ReactJS and using the React Bootstrap framework.