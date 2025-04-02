package com.github.jvalentino.eq.main;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This is the main window for the application in which all content is rendered.
 */
public class MainWindow extends JFrame {

  private MainWindowController controller = null;
  protected JPanel mainPanel;
  protected JButton loadButton;

  public MainWindow() {
    controller = new MainWindowController(this);
  }

  public void display() {
    this.setSize(300, 200);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setAlwaysOnTop(true);
    this.setTitle("EverQuest (THJ) DPS Monitor");

    // Create main panel with a centered button
    mainPanel = new JPanel(new GridBagLayout()); // Ensures button stays normal-sized and centered
    loadButton = new JButton("Load");

    // Center the button
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    mainPanel.add(loadButton, gbc);

    this.setContentPane(mainPanel); // Set initial content
    this.setVisible(true);

    controller.displayed();
  }

  /**
   * Replaces the window content with a new JPanel.
   */
  protected void loadButtonPressed() {
    JPanel newPanel = new JPanel(new GridBagLayout());
    newPanel.setBackground(Color.LIGHT_GRAY);

    JLabel label = new JLabel("New Panel Loaded!", SwingConstants.CENTER);

    // Center the label
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    newPanel.add(label, gbc);

    // Replace content
    this.setContentPane(newPanel);
    this.revalidate(); // Refresh layout
    this.repaint();    // Redraw window
  }
}
