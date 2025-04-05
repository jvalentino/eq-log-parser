package com.github.jvalentino.eq.service;

import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an event of interest from the log file
 */
@Data
@Getter
@Setter
public class LogEvent {

  private Date timestamp;
  private String timestampString;
  private ActionType actionType = ActionType.NONE;
  private String actor;
  private String recipient;
  private Integer damage;
  private String actionSubType;


}
