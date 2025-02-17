package com.rationaleemotions;

import java.io.IOException;
import java.net.URI;
import java.time.Duration;
import java.util.UUID;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.grid.config.Config;
import org.openqa.selenium.grid.data.CreateSessionRequest;
import org.openqa.selenium.grid.data.CreateSessionResponse;
import org.openqa.selenium.grid.data.NodeId;
import org.openqa.selenium.grid.data.NodeStatus;
import org.openqa.selenium.grid.data.Session;
import org.openqa.selenium.grid.log.LoggingOptions;
import org.openqa.selenium.grid.node.HealthCheck;
import org.openqa.selenium.grid.node.Node;
import org.openqa.selenium.grid.node.local.LocalNodeFactory;
import org.openqa.selenium.grid.security.Secret;
import org.openqa.selenium.grid.security.SecretOptions;
import org.openqa.selenium.grid.server.BaseServerOptions;
import org.openqa.selenium.internal.Either;
import org.openqa.selenium.io.TemporaryFilesystem;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.tracing.Tracer;

@Slf4j
public class DecoratedLoggingNode extends Node {

  private Node node;

  protected DecoratedLoggingNode(Tracer tracer, NodeId nodeId, URI uri, Secret registrationSecret) {
    super(tracer, nodeId, uri, registrationSecret, Duration.ofMinutes(2));
  }

  @SuppressWarnings("unused")
  public static Node create(Config config) {
    LoggingOptions loggingOptions = new LoggingOptions(config);
    BaseServerOptions serverOptions = new BaseServerOptions(config);
    URI uri = serverOptions.getExternalUri();
    SecretOptions secretOptions = new SecretOptions(config);

    // Refer to the footnotes for additional context on this line.
    Node node = LocalNodeFactory.create(config);

    DecoratedLoggingNode wrapper = new DecoratedLoggingNode(loggingOptions.getTracer(),
        node.getId(),
        uri, secretOptions.getRegistrationSecret());
    wrapper.node = node;
    return wrapper;
  }

  @Override
  public void releaseConnection(SessionId id) {
    this.node.releaseConnection(id);
  }

  @Override
  public Either<WebDriverException, CreateSessionResponse> newSession(
      CreateSessionRequest sessionRequest) {
    return perform(() -> node.newSession(sessionRequest), "newSession");
  }

  @Override
  public HttpResponse executeWebDriverCommand(HttpRequest req) {
    return perform(() -> node.executeWebDriverCommand(req), "executeWebDriverCommand");
  }

  @Override
  public Session getSession(SessionId id) throws NoSuchSessionException {
    return perform(() -> node.getSession(id), "getSession");
  }

  @Override
  public HttpResponse uploadFile(HttpRequest req, SessionId id) {
    return perform(() -> node.uploadFile(req, id), "uploadFile");
  }

  @Override
  public HttpResponse downloadFile(HttpRequest req, SessionId id) {
    return perform(() -> node.downloadFile(req, id), "downloadFile");
  }

  @Override
  public TemporaryFilesystem getDownloadsFilesystem(UUID uuid) {
    return perform(() -> {
      try {
        return node.getDownloadsFilesystem(uuid);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }, "downloadsFilesystem");
  }

  @Override
  public TemporaryFilesystem getUploadsFilesystem(SessionId id) throws IOException {
    return perform(() -> {
      try {
        return node.getUploadsFilesystem(id);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }, "uploadsFilesystem");

  }

  @Override
  public void stop(SessionId id) throws NoSuchSessionException {
    perform(() -> node.stop(id), "stop");
  }

  @Override
  public boolean isSessionOwner(SessionId id) {
    return perform(() -> node.isSessionOwner(id), "isSessionOwner");
  }

  @Override
  public boolean tryAcquireConnection(SessionId id) {
    return node.tryAcquireConnection(id);
  }

  @Override
  public boolean isSupporting(Capabilities capabilities) {
    return perform(() -> node.isSupporting(capabilities), "isSupporting");
  }

  @Override
  public NodeStatus getStatus() {
    return perform(() -> node.getStatus(), "getStatus");
  }

  @Override
  public HealthCheck getHealthCheck() {
    return perform(() -> node.getHealthCheck(), "getHealthCheck");
  }

  @Override
  public void drain() {
    perform(() -> node.drain(), "drain");
  }

  @Override
  public boolean isReady() {
    return perform(() -> node.isReady(), "isReady");
  }

  private void perform(Runnable function, String operation) {
    try {
      log.info("[COMMENTATOR] Before {}()", operation);
      function.run();
    } finally {
      log.info("[COMMENTATOR] After {}}()", operation);
    }
  }

  private <T> T perform(Supplier<T> function, String operation) {
    try {
      log.info("[COMMENTATOR] Before {}()", operation);
      return function.get();
    } finally {
      log.info("[COMMENTATOR] After {}()", operation);
    }
  }
}
