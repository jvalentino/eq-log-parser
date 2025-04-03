package com.github.jvalentino.eq.component;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.axis.NumberAxis;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DpsPanel extends JPanel {

  private DefaultCategoryDataset dataset;
  private Timer timer;
  private Random random = new Random();
  private Map<Comparable<?>, Double> maxValues = new HashMap<>();
  private NumberAxis rangeAxis;

  public DpsPanel() {
    this.setBackground(Color.LIGHT_GRAY);
    this.setLayout(new BorderLayout());

    // Create dataset for the stacked bar chart
    dataset = new DefaultCategoryDataset();

    // Initial values for Melee and Non-Melee damage
    dataset.addValue(700.0, "Melee Damage", "Alpha");
    dataset.addValue(534.5, "Non-Melee Damage", "Alpha");

    dataset.addValue(200.0, "Melee Damage", "Bravo");
    dataset.addValue(145.6, "Non-Melee Damage", "Bravo");

    dataset.addValue(50.0, "Melee Damage", "Charlie");
    dataset.addValue(73.4, "Non-Melee Damage", "Charlie");

    // Store max values per category
    for (int i = 0; i < dataset.getColumnCount(); i++) {
      Comparable<?> key = dataset.getColumnKey(i);
      double total = dataset.getValue("Melee Damage", key).doubleValue() +
          dataset.getValue("Non-Melee Damage", key).doubleValue();
      maxValues.put(key, total);
    }

    // Create stacked horizontal bar chart **without title**
    JFreeChart chart = ChartFactory.createStackedBarChart(
        null,
        "",
        "",
        dataset,
        PlotOrientation.HORIZONTAL,
        true,    // Show legend
        true,    // Enable tooltips
        false
    );

    // Customize the plot
    CategoryPlot plot = (CategoryPlot) chart.getPlot();
    plot.setDomainGridlinesVisible(false);
    plot.setRangeGridlinesVisible(false);

    // Configure the range axis (X-axis for horizontal chart)
    rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setUpperMargin(0.30);
    updateChartAxisRange();

    // Use StackedBarRenderer instead of BarRenderer
    StackedBarRenderer renderer = new StackedBarRenderer();

    // Display values on the bars
    renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
    renderer.setDefaultItemLabelsVisible(true);

    // Set tooltips properly for each series
    renderer.setSeriesToolTipGenerator(0, new StandardCategoryToolTipGenerator());
    renderer.setSeriesToolTipGenerator(1, new StandardCategoryToolTipGenerator());

    // Apply renderer
    plot.setRenderer(renderer);

    // Add chart to panel
    ChartPanel chartPanel = new ChartPanel(chart);
    this.add(chartPanel, BorderLayout.CENTER);

    // Start animation
    startTimer();
  }

  /**
   * Start the timer that updates the dataset every second.
   */
  private void startTimer() {
    timer = new Timer(1000, e -> updateChartValues());
    timer.start();
  }

  /**
   * Update the chart's dataset values by a random factor of ±20%.
   */
  private void updateChartValues() {
    boolean maxUpdated = false;

    for (int j = 0; j < dataset.getColumnCount(); j++) {
      Comparable<?> key = dataset.getColumnKey(j);
      double melee = dataset.getValue("Melee Damage", key).doubleValue();
      double nonMelee = dataset.getValue("Non-Melee Damage", key).doubleValue();

      // Adjust melee and non-melee values randomly by ±20%
      melee *= 1 + (random.nextBoolean() ? 1 : -1) * (random.nextDouble() * 0.2);
      nonMelee *= 1 + (random.nextBoolean() ? 1 : -1) * (random.nextDouble() * 0.2);

      dataset.setValue(melee, "Melee Damage", key);
      dataset.setValue(nonMelee, "Non-Melee Damage", key);

      double newTotal = melee + nonMelee;

      // Check if we have a new maximum
      if (newTotal > maxValues.get(key)) {
        maxValues.put(key, newTotal);
        maxUpdated = true;
      }
    }

    // If a new max value was found, update the axis range
    if (maxUpdated) {
      updateChartAxisRange();
    }

    repaint(); // Trigger a repaint to reflect the updated dataset
  }

  /**
   * Update the chart's Y-axis range based on the stored max values.
   */
  private void updateChartAxisRange() {
    double max = maxValues.values().stream().mapToDouble(Double::doubleValue).max().orElse(1.0);
    rangeAxis.setRange(0, max * 1.2); // Ensure extra space beyond the max
  }
}
