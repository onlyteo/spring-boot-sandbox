# Spring Boot OTEL Observability

This example shows an observability enabled architecture with Open Telemetry and Micrometer.

## Prerequisites

* Java Runtime - e.g. [Temurin JDK](https://adoptium.net), [OpenJDK](https://openjdk.org) or [Oracle JDK](https://www.oracle.com/java)
* [NodeJS Runtime](https://nodejs.org)
* [NPM](https://www.npmjs.com) or [Yarn](https://yarnpkg.com)
* [Docker](https://www.docker.com)

## Run

Start Backend application:
```bash
../gradlew :spring-boot-otel-observability:backend:bootRun
```

Start Frontend API application:

```bash
../gradlew :spring-boot-otel-observability:frontend-api:bootRun
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
    subgraph OTEL Collector
        D[OTEL]:::otel
    end
    subgraph Prometheus
        E[Scrape]:::prometheus
    end
    subgraph Grafana
        F[Tempo]:::grafana
    end

    A -- REST --> B
    B -- REST --> C
    A -.-> D
    B -.-> D
    C -.-> D
    D -.-> E
    D -.-> F

    classDef react fill: #087ea4, stroke: #000000, color: #000000
    classDef spring fill: #80ea6e, stroke: #000000, color: #000000
    classDef otel fill: #4f62ad, stroke: #000000, color: #000000
    classDef prometheus fill: #e6522c, stroke: #000000, color: #000000
    classDef grafana fill: #ffa500, stroke: #000000, color: #000000
```

### Frontend

The Frontend is a JavaScript web application based on ReactJS and using the React Bootstrap framework.

### Frontend API

The Frontend API is a REST API application based on Spring Boot.

### Backend

The Backend is a REST API application based on Spring Boot.
