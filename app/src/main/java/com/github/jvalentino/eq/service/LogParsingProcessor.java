package com.github.jvalentino.eq.service;

import java.io.File;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;

/**
 * The purpose of this class is to handle doing the actual parsing and processing of a log.
 *
 */
public class LogParsingProcessor extends TailerListenerAdapter {

  private File file;

  public LogParsingProcessor(File file) {
    this.file = file;
  }

  public void start(boolean tail) {
    // Create a Tailer to monitor the log file
    Tailer tailer = new Tailer(this.file, this, 1000, true);

    // Run the tailer in a separate thread
    Thread thread = new Thread(tailer);
    thread.start();

    // Optionally, stop tailing after some time
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Stopping log tailer...");
      tailer.stop();
    }));
  }

  @Override
  public void handle(String line) {
    // Process each new line added to the log
    System.out.println("Log: " + line);
  }

  @Override
  public void fileNotFound() {
    System.err.println("Log file not found.");
  }

  @Override
  public void fileRotated() {
    System.out.println("Log file rotated.");
  }

  @Override
  public void handle(Exception ex) {
    System.err.println("Error while tailing log: " + ex.getMessage());
  }

}
