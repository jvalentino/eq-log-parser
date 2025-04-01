package com.github.jvalentino.eq;

import com.github.jvalentino.eq.service.LogParsingProcessor;
import java.io.File;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListenerAdapter;

/**
 * Main application runtime.
 */
@SuppressWarnings({"PMD.AtLeastOneConstructor"})
public class MainApplication extends TailerListenerAdapter {

  /**
   * Entry point
   * @param args Not Used
   */
  public static void main(final String[] args) {
    new MainApplication().execute();
  }

  /**
   * Main method
   */
  public void execute() {
    File logFile = new File("demo-data/demo-file.txt");

    LogParsingProcessor processor = new LogParsingProcessor(logFile);
    processor.start(true);

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
