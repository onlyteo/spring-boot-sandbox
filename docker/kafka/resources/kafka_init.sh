#!/bin/bash

if [[ -z "${BOOTSTRAP_SERVER}" ]]; then
  echo "BOOTSTRAP_SERVER env var is not set"
  exit 1
fi

if [[ -z "${CREATE_TOPICS}" ]]; then
  echo "CREATE_TOPICS env var is not set"
  exit 1
fi

IFS=$IFS,
topics=()
for topic in ${CREATE_TOPICS}
do topics+=("${topic}")
done

partitions=${PARTITIONS:-1}
replication_factor=${REPLICATION_FACTOR:-1}

kafka-topics --list --bootstrap-server "${BOOTSTRAP_SERVER}"
for topic in "${topics[@]}"
do
  kafka-topics --create --topic "${topic}" --partitions "${partitions}" --replication-factor "${replication_factor}" --if-not-exists --bootstrap-server "${BOOTSTRAP_SERVER}"
done
