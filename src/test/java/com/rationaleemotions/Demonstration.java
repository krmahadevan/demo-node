package com.rationaleemotions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Demonstration {

  public static void main(String[] args) throws IOException, InterruptedException {
    System.err.println("Sleeping for 30 seconds before starting test case.");
    TimeUnit.SECONDS.sleep(30);
    RemoteWebDriver driver = null;
    try {
      driver = new RemoteWebDriver(new URL("http://localhost:4444"), new ChromeOptions());
      driver.get("https://www.selenium.dev/");
      System.err.println("Page title :" + driver.getTitle());
      printLogOutput();
    } finally {
      if (driver != null) {
        driver.quit();
      }
      killProcesses();
    }
  }

  private static void printLogOutput() throws IOException {
    File file = new File("target/node.log");
    if (!file.exists()) {
      return;
    }
    System.err.println("==============================================");
    System.err.println("Printing the node logs from the Decorated Node");
    System.err.println("==============================================");
    Files.readAllLines(file.toPath())
        .stream()
        .filter(each -> each.contains("[COMMENTATOR]"))
        .forEach(System.err::println);
    System.err.println("==============================================");
  }

  private static void killProcesses() {
    ProcessHandle.allProcesses()
        .filter(Demonstration::isSeleniumJarCommand)
        .peek(Demonstration::prettyPrint)
        .forEach(ProcessHandle::destroy);
  }

  private static boolean isSeleniumJarCommand(ProcessHandle handle) {
    return handle.info().commandLine()
        .map(cmd -> cmd.contains("target/jars/selenium-server-4.17.0.jar"))
        .orElse(false);
  }

  private static void prettyPrint(ProcessHandle handle) {
    handle.info()
        .commandLine()
        .ifPresent(cmd -> {
          System.err.println("Killing [" + cmd + "]");
        });
  }

}
