package com.github.jvalentino.eq.service

import spock.lang.Specification
import spock.lang.Subject

class RollingAverageTest extends Specification {


    @Subject
    RollingAverage subject

    def setup() {
        subject = new RollingAverage(300L)
    }

    def "test rolling average"() {
      when:
      long t1 = 100
      subject.add(100.0, t1)

      then:
      subject.getSum(t1) == 100.0
      subject.getAverage(t1) == 100.0

      when:
      long t2 = 200
      subject.add(200.0, t2)

      then:
      subject.getSum(t2) == 300.0
      subject.getAverage(t2) == 150.0

      when:
      long t3 = 300
      subject.add(100.0, t3)

      then:
      subject.getSum(t3) == 400.0
      subject.getAverage(t3) == 133.33333333333334

      when:
      long t4 = 400
      subject.add(200, t4)

      then:
      subject.getSum(t4) == 500.0
      subject.getAverage(t4) == 166.66666666666666


    }

}
