#!/bin/sh

# Build the code
./mvnw clean package
export "$(grep -v '^#' target/classes/selenium.properties | xargs)"
# Start the hub
java -jar target/jars/selenium-server-${version}.jar hub --log target/hub.log &

# Start the node
java -jar target/jars/selenium-server-${version}.jar --ext target/demo-node-1.0-SNAPSHOT.jar node --log target/node.log \
--node-implementation com.rationaleemotions.DecoratedLoggingNode --selenium-manager true &

# Run the test case
./mvnw exec:java


