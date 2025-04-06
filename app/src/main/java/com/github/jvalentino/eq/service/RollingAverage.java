package com.github.jvalentino.eq.service;

import java.util.*;

/**
 * Used to maintain a time-based rolling average for a series of values.
 */
public class RollingAverage {
  private final long windowMillis;
  private final Deque<Entry> window = new LinkedList<>();
  private double sum = 0;

  public RollingAverage(long windowMillis) {
    this.windowMillis = windowMillis;
  }

  public void add(double value, long now) {
    evictOld(now); // ✅ evict first based on current timestamp
    window.addLast(new Entry(now, value));
    sum += value;
  }

  private void evictOld(long now) {
    while (!window.isEmpty() && window.peekFirst().timestamp <= now - windowMillis) {
      sum -= window.removeFirst().value;
    }
  }

  public double getAverage(long now) {
    evictOld(now); // ✅ evict again to ensure consistency
    return window.isEmpty() ? 0 : sum / window.size();
  }

  public double getSum(long now) {
    evictOld(now); // ✅ same as above
    return sum;
  }

  private static class Entry {
    long timestamp;
    double value;
    Entry(long timestamp, double value) {
      this.timestamp = timestamp;
      this.value = value;
    }
  }
}
