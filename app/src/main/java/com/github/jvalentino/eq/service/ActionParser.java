package com.github.jvalentino.eq.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The underlying parsing for turning a log message into a log event
 */
public class ActionParser {

  private static final Pattern MELEE_PATTERN = Pattern.compile(
      "\\[(.*?)\\] (\\w+) (\\w+) (.+?) for (\\d+) points of damage\\."
  );

  private static final Pattern NON_MELEE_PATTERN = Pattern.compile(
      "\\[(.*?)\\] (\\w+) (\\w+) (.+?) for (\\d+) points of (\\w+-?\\w*) damage\\. \\((.+?)\\)"
  );

  private static final DateFormat
      dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.ENGLISH);

  public static LogEvent parse(String line) {
    String trimmed = line.trim();

    if (trimmed.contains("points of damage")) {
      return parseMeleeDamage(trimmed);
    } else if (trimmed.contains("points of non-melee damage")) {
      return parseNonMeleeDamage(trimmed);
    }

    return new LogEvent();

  }

  public static LogEvent parseMeleeDamage(String line) {
    LogEvent event = new LogEvent();
    Matcher matcher = MELEE_PATTERN.matcher(line);

    if (!matcher.matches()) {
      // this is to be expected with things like shield statements
      // System.err.println("Does not match parser:" + line);
      return event;
    }

    String timestampStr = matcher.group(1);         // "Sun Mar 30 18:54:35 2025"
    String actor = matcher.group(2);                // "You"
    String action = matcher.group(3);               // "slash"
    String target = matcher.group(4);               // "Qua Senshali Xakra"
    int damage = Integer.parseInt(matcher.group(5));// 365

    Date timestamp = null;

    try {
      timestamp = dateFormat.parse(timestampStr);
    } catch (ParseException e) {
      e.printStackTrace();
      System.err.println(line);
      return event;
    }

    // Populate your LogEvent object here
    event.setTimestamp(timestamp);
    event.setTimestampString(timestampStr);
    event.setActor(actor);
    event.setActionType(ActionType.MELEE);
    event.setRecipient(target);
    event.setDamage(damage);
    event.setActionSubType(action);

    return event;
  }

  public static LogEvent parseNonMeleeDamage(String line){
    LogEvent event = new LogEvent();
    Matcher matcher = NON_MELEE_PATTERN.matcher(line);
    if (!matcher.matches()) {
      return event;
    }

    String timestampStr = matcher.group(1);         // "Sun Mar 30 18:54:38 2025"
    String actor = matcher.group(2);                // "Trombonius"
    String action = matcher.group(3);               // "hit"
    String target = matcher.group(4);               // "Qua Senshali Xakra"
    int damage = Integer.parseInt(matcher.group(5));// 4087
    String damageType = matcher.group(6);           // "non-melee"
    String spell = matcher.group(7);                // "Time Snap"

    Date timestamp = null;

    try {
      timestamp = dateFormat.parse(timestampStr);
    } catch (ParseException e) {
      e.printStackTrace();
      System.err.println(line);
      return event;
    }

    event.setTimestamp(timestamp);
    event.setTimestampString(timestampStr);
    event.setActor(actor);
    event.setActionType(ActionType.SPELL);
    event.setRecipient(target);
    event.setDamage(damage);
    event.setActionSubType(spell);

    return event;
  }

}
