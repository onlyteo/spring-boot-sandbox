# Kafka Connect

## Install Debezium MySQL Connector
The connector is installed automatically from the Confluent Hub when the container starts.

### Enable Debezium MySQL Connector
It must be enabled by using the Kafka Connect REST API:

```bash
curl -X POST "http://localhost:8083/connectors" -H "Content-Type: application/json" -d @./resources/mysql-source-connector-config.json
```

### Verify Debezium MySQL Connector
Check if the connector is installed:

```bash
curl -X GET http://localhost:8083/connectors
```
Should return:
```json
["debezium-mysql-source-connector"]
```

### Disable Debezium MySQL Connector

```bash
curl -X DELETE "http://localhost:8083/connectors/debezium-mysql-source-connector"
```
