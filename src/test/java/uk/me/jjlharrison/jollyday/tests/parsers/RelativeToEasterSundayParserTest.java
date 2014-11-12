package uk.me.jjlharrison.jollyday.tests.parsers;

import uk.me.jjlharrison.config.*;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.Test;

import uk.me.jjlharrison.jollyday.Holiday;
import uk.me.jjlharrison.jollyday.parser.impl.RelativeToEasterSundayParser;
import uk.me.jjlharrison.jollyday.util.CalendarUtil;

public class RelativeToEasterSundayParserTest {

    RelativeToEasterSundayParser parser = new RelativeToEasterSundayParser();
    Set<Holiday> holidays = new HashSet<Holiday>();
    CalendarUtil calendarUtil = new CalendarUtil();

    @Test
    public void testForEasterMonday() {
        doTest(2013, 1);
    }

    @Test
    public void testForEasterSaturday() {
        doTest(2013, -1);
    }

    private void doTest(int year, int days) {
        Holidays holidaysConfig = new Holidays();
        addRelativeToEasterHoliday(holidaysConfig, days);
        parser.parse(year, holidays, holidaysConfig);
        assertEquals("Missing holiday.", 1, holidays.size());
        Holiday h = holidays.iterator().next();
        LocalDate targetDate = calendarUtil.getEasterSunday(year).plusDays(days);
        assertEquals("Wrong date found.", targetDate, h.getDate());
    }

    private void addRelativeToEasterHoliday(Holidays holidaysConfig, int days) {
        RelativeToEasterSunday r = new RelativeToEasterSunday();
        r.setDays(days);
        holidaysConfig.getRelativeToEasterSunday().add(r);
    }

}
