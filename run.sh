#!/bin/sh

# Build the code
./mvnw clean package

# Start the hub
java -jar target/jars/selenium-server-4.17.0.jar hub --log target/hub.log &

# Start the node
java -jar target/jars/selenium-server-4.17.0.jar --ext target/demo-node-1.0-SNAPSHOT.jar node --log target/node.log \
--node-implementation com.rationaleemotions.DecoratedLoggingNode --selenium-manager true &

# Run the test case
./mvnw exec:java


