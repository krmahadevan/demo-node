package com.rationaleemotions;

import java.net.URI;
import java.util.UUID;
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
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.tracing.Tracer;

@Slf4j
public class DecoratedLoggingNode extends Node {

  private Node node;

  protected DecoratedLoggingNode(Tracer tracer, URI uri, Secret registrationSecret) {
    super(tracer, new NodeId(UUID.randomUUID()), uri, registrationSecret);
  }

  public static Node create(Config config) {
    LoggingOptions loggingOptions = new LoggingOptions(config);
    BaseServerOptions serverOptions = new BaseServerOptions(config);
    URI uri = serverOptions.getExternalUri();
    SecretOptions secretOptions = new SecretOptions(config);

    // Refer to the foot notes for additional context on this line.
    Node node = LocalNodeFactory.create(config);

    DecoratedLoggingNode wrapper = new DecoratedLoggingNode(loggingOptions.getTracer(),
        uri, secretOptions.getRegistrationSecret());
    wrapper.node = node;
    return wrapper;
  }

  @Override
  public Either<WebDriverException, CreateSessionResponse> newSession(
      CreateSessionRequest sessionRequest) {
    log.info("[COMMENTATOR] Before newSession()");
    try {
      return this.node.newSession(sessionRequest);
    } finally {
      log.info("[COMMENTATOR] After newSession()");
    }
  }

  @Override
  public HttpResponse executeWebDriverCommand(HttpRequest req) {
    try {
      log.info("[COMMENTATOR] Before executeWebDriverCommand(): " + req.getUri());
      return node.executeWebDriverCommand(req);
    } finally {
      log.info("[COMMENTATOR] After executeWebDriverCommand()");
    }
  }

  @Override
  public Session getSession(SessionId id) throws NoSuchSessionException {
    try {
      log.info("[COMMENTATOR] Before getSession()");
      return node.getSession(id);
    } finally {
      log.info("[COMMENTATOR] After getSession()");
    }
  }

  @Override
  public HttpResponse uploadFile(HttpRequest req, SessionId id) {
    try {
      log.info("[COMMENTATOR] Before uploadFile()");
      return node.uploadFile(req, id);
    } finally {
      log.info("[COMMENTATOR] After uploadFile()");
    }
  }

  @Override
  public void stop(SessionId id) throws NoSuchSessionException {
    try {
      log.info("[COMMENTATOR] Before stop()");
      node.stop(id);
    } finally {
      log.info("[COMMENTATOR] After stop()");
    }
  }

  @Override
  public boolean isSessionOwner(SessionId id) {
    try {
      log.info("[COMMENTATOR] Before isSessionOwner()");
      return node.isSessionOwner(id);
    } finally {
      log.info("[COMMENTATOR] After isSessionOwner()");
    }
  }

  @Override
  public boolean isSupporting(Capabilities capabilities) {
    try {
      log.info("[COMMENTATOR] Before isSupporting");
      return node.isSupporting(capabilities);
    } finally {
      log.info("[COMMENTATOR] After isSupporting()");
    }
  }

  @Override
  public NodeStatus getStatus() {
    try {
      log.info("[COMMENTATOR] Before getStatus()");
      return node.getStatus();
    } finally {
      log.info("[COMMENTATOR] After getStatus()");
    }
  }

  @Override
  public HealthCheck getHealthCheck() {
    try {
      log.info("[COMMENTATOR] Before getHealthCheck()");
      return node.getHealthCheck();
    } finally {
      log.info("[COMMENTATOR] After getHealthCheck()");
    }
  }

  @Override
  public void drain() {
    try {
      log.info("[COMMENTATOR] Before drain()");
      node.drain();
    } finally {
      log.info("[COMMENTATOR] After drain()");
    }

  }

  @Override
  public boolean isReady() {
    try {
      log.info("[COMMENTATOR] Before isReady()");
      return node.isReady();
    } finally {
      log.info("[COMMENTATOR] After isReady()");
    }
  }
}
