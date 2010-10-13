package org.javakontor.sherlog.ui.timefilter.test;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.ui.timefilter.TimeFilter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimeFilterTest {

  @Mock
  private LogEvent _pastLogEvent;

  @Mock
  private LogEvent _nowLogEvent;

  @Mock
  private LogEvent _futureLogEvent;

  private long     _now = System.currentTimeMillis();

  @Before
  public void configureMocks() {
    given(_pastLogEvent.getTimeStamp()).willReturn(_now - 1000);
    given(_nowLogEvent.getTimeStamp()).willReturn(_now);
    given(_futureLogEvent.getTimeStamp()).willReturn(_now + 1000);

  }

  @Test
  public void allEventsShouldMatchWhenNoTimestampsAreSet() {
    TimeFilter timeFilter = new TimeFilter();

    assertTrue(timeFilter.matches(_pastLogEvent));
    assertTrue(timeFilter.matches(_nowLogEvent));
    assertTrue(timeFilter.matches(_futureLogEvent));
  }

  @Test
  public void eventsBeforeBeforeTimestampShouldNotMatch() {
    TimeFilter timeFilter = new TimeFilter();
    timeFilter.setBeforeTimestamp(_now);

    assertFalse(timeFilter.matches(_pastLogEvent));
    assertTrue(timeFilter.matches(_nowLogEvent));
    assertTrue(timeFilter.matches(_futureLogEvent));
  }

  @Test
  public void eventsAfterAfterTimestampShouldNotMatch() {
    TimeFilter timeFilter = new TimeFilter();
    timeFilter.setAfterTimestamp(_now);

    assertTrue(timeFilter.matches(_pastLogEvent));
    assertTrue(timeFilter.matches(_nowLogEvent));
    assertFalse(timeFilter.matches(_futureLogEvent));
  }

  @Test
  public void onlyEventsBetweenBeforeAndAfterShouldMatch() {
    TimeFilter timeFilter = new TimeFilter();
    timeFilter.setBeforeTimestamp(_now - 500);
    timeFilter.setAfterTimestamp(_now + 100);

    assertFalse(timeFilter.matches(_pastLogEvent));
    assertTrue(timeFilter.matches(_nowLogEvent));
    assertFalse(timeFilter.matches(_futureLogEvent));
  }

}
