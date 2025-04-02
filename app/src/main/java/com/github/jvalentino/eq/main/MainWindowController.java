package com.github.jvalentino.eq.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * The controller for the main window
 */
public class MainWindowController {

  private MainWindow ui = null;

  public MainWindowController(MainWindow ui) {
    this.ui = ui;
  }

  public void displayed() {
    ui.loadButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ui.loadButtonPressed();
      }
    });

    // This causes the window every second to be repainted on top of everything
    Timer timer = new Timer(1000, e -> {
      ui.toFront();
      ui.repaint();
    });

    timer.setRepeats(true);
    timer.start();
  }



}
