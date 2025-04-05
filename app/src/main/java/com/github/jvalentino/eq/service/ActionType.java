package com.github.jvalentino.eq.service;

/**
 * Represents the types of actions we care about for parsing
 */
public enum ActionType {

  NONE,
  // A B C slash A B C for 892 points of damage.
  // Pli Va Dyn smashes YOU for 31 points of damage.
  MELEE,
  // X scores a critical hit! (900)
  CRITICAL_MELEE,
  // You deliver a critical blast! (4087) (Time Snap)
  CRITICAL_SPELL,
  // X hit A B C for 4087 points of non-melee damage. (Time Snap)
  SPELL

}
