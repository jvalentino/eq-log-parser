package com.github.jvalentino.eq.component;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.ui.RectangleInsets;
import org.jfree.chart.axis.NumberAxis;

import javax.swing.*;
import java.awt.*;

public class DpsPanel extends JPanel {

  public DpsPanel() {
    this.setBackground(Color.LIGHT_GRAY);
    this.setLayout(new BorderLayout()); // Ensures proper layout for chart

    // Create dataset for the bar chart
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    dataset.addValue(1234.5, "DPS", "Alpha");
    dataset.addValue(345.6, "DPS", "Bravo");
    dataset.addValue(123.4, "DPS", "Charlie");

    // Create horizontal bar chart **without title**
    JFreeChart chart = ChartFactory.createBarChart(
        null,                // **No title**
        "",                  // No X-axis label
        "",                  // No Y-axis label
        dataset,
        PlotOrientation.HORIZONTAL, // **Make bars go left to right**
        false,               // No legend
        false,               // No tooltips
        false                // No URLs
    );

    // Customize the plot
    CategoryPlot plot = (CategoryPlot) chart.getPlot();
    plot.setDomainGridlinesVisible(false);
    plot.setRangeGridlinesVisible(false);

    // Increase space on the right by adjusting the range axis (Y-axis for horizontal bar chart)
    NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
    rangeAxis.setUpperMargin(0.30); // Increase space on the right side of the chart

    // Customize the renderer for better bar appearance
    BarRenderer renderer = (BarRenderer) plot.getRenderer();
    renderer.setMaximumBarWidth(0.1); // Adjust bar thickness

    // Display values on the bars
    renderer.setDefaultItemLabelGenerator(new StandardCategoryItemLabelGenerator());
    renderer.setDefaultItemLabelsVisible(true);

    // Apply renderer changes
    plot.setRenderer(renderer);

    // Add chart to panel
    ChartPanel chartPanel = new ChartPanel(chart);
    this.add(chartPanel, BorderLayout.CENTER);
  }
}
