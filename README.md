# Demo project for a customized node

## Documentation

Refer to the [Selenium documentation](https://www.selenium.dev/documentation/grid/advanced_features/customize_node/) to learn more about how to build a custom selenium node.

## Pre-requisites

* JDK11.
* Chrome Browser installed.

## Details

This sample project does the following:

* Downloads the Selenium standalone `4.8.0` jar from the Selenium downloads page.
* Wires-in a customized Selenium node and starts a simple Selenium Grid that uses the custom node.
* Runs a sample test-case against the spun off local grid.
* Prints the custom node's logs to show that the custom node works.
* Kills the Grid as well.

## Usage

* To see this project in action just run `./run.sh` and you should see an output that looks like below.

```bash
Jan 26, 2023 11:59:44 AM org.openqa.selenium.remote.tracing.opentelemetry.OpenTelemetryTracer createTracer
INFO: Using OpenTelemetry for tracing
Starting ChromeDriver 109.0.5414.74 (e7c5703604daa9cc128ccf5a5d3e993513758913-refs/branch-heads/5414@{#1172}) on port 27209
Only local connections are allowed.
Please see https://chromedriver.chromium.org/security-considerations for suggestions on keeping ChromeDriver safe.
ChromeDriver was started successfully.
Page title :Selenium
==============================================
Printing the node logs from the Decorated Node
==============================================
11:59:44.963 INFO [DecoratedLoggingNode.getHealthCheck] - [COMMENTATOR] Before getHealthCheck()
11:59:44.964 INFO [DecoratedLoggingNode.getHealthCheck] - [COMMENTATOR] After getHealthCheck()
11:59:44.968 INFO [DecoratedLoggingNode.getStatus] - [COMMENTATOR] Before getStatus()
11:59:44.969 INFO [DecoratedLoggingNode.getStatus] - [COMMENTATOR] After getStatus()
11:59:46.298 INFO [DecoratedLoggingNode.getStatus] - [COMMENTATOR] Before getStatus()
11:59:46.299 INFO [DecoratedLoggingNode.getStatus] - [COMMENTATOR] After getStatus()
11:59:46.629 INFO [DecoratedLoggingNode.newSession] - [COMMENTATOR] Before newSession()
11:59:49.652 INFO [DecoratedLoggingNode.newSession] - [COMMENTATOR] After newSession()
11:59:49.850 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] Before isSessionOwner()
11:59:49.850 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] After isSessionOwner()
11:59:49.851 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] Before isSessionOwner()
11:59:49.851 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] After isSessionOwner()
11:59:49.851 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] Before isSessionOwner()
11:59:49.851 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] After isSessionOwner()
11:59:49.852 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] Before isSessionOwner()
11:59:49.852 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] After isSessionOwner()
11:59:49.853 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] Before isSessionOwner()
11:59:49.853 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] After isSessionOwner()
11:59:49.854 INFO [DecoratedLoggingNode.executeWebDriverCommand] - [COMMENTATOR] Before executeWebDriverCommand(): /session/5951ff2a1c0a01e2d4c4038cce18e06e/url
11:59:51.098 INFO [DecoratedLoggingNode.executeWebDriverCommand] - [COMMENTATOR] After executeWebDriverCommand()
11:59:51.118 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] Before isSessionOwner()
11:59:51.118 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] After isSessionOwner()
11:59:51.118 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] Before isSessionOwner()
11:59:51.118 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] After isSessionOwner()
11:59:51.119 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] Before isSessionOwner()
11:59:51.119 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] After isSessionOwner()
11:59:51.119 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] Before isSessionOwner()
11:59:51.119 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] After isSessionOwner()
11:59:51.120 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] Before isSessionOwner()
11:59:51.120 INFO [DecoratedLoggingNode.isSessionOwner] - [COMMENTATOR] After isSessionOwner()
11:59:51.120 INFO [DecoratedLoggingNode.executeWebDriverCommand] - [COMMENTATOR] Before executeWebDriverCommand(): /session/5951ff2a1c0a01e2d4c4038cce18e06e/title
11:59:51.129 INFO [DecoratedLoggingNode.executeWebDriverCommand] - [COMMENTATOR] After executeWebDriverCommand()
==============================================
Killing [/Users/krmahadevan/.sdkman/candidates/java/current/bin/java -jar target/jars/selenium-server-4.8.0.jar --ext target/demo-node-1.0-SNAPSHOT.jar node --log target/node.log --node-implementation com.rationaleemotions.DecoratedLoggingNode]
Killing [/Users/krmahadevan/.sdkman/candidates/java/current/bin/java -jar target/jars/selenium-server-4.8.0.jar hub --log target/hub.log]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  9.498 s
[INFO] Finished at: 2023-01-26T11:59:52+05:30
[INFO] ------------------------------------------------------------------------
```
