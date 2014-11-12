/**
 * Copyright 2011 Sven Diedrichsen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package uk.me.jjlharrison.jollyday.tests.parsers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDate;
import org.junit.Assert;
import org.junit.Test;

import uk.me.jjlharrison.jollyday.Holiday;
import uk.me.jjlharrison.config.Fixed;
import uk.me.jjlharrison.config.Holidays;
import uk.me.jjlharrison.config.Month;
import uk.me.jjlharrison.config.MovingCondition;
import uk.me.jjlharrison.config.Weekday;
import uk.me.jjlharrison.config.With;
import uk.me.jjlharrison.jollyday.parser.impl.FixedParser;
import uk.me.jjlharrison.jollyday.util.CalendarUtil;

/**
 * @author Sven
 *
 */
public class FixedParserTest {

    private FixedParser fixedParser = new FixedParser();
    private CalendarUtil calendarUtil = new CalendarUtil();

    @Test
    public void testFixedWithValidity() {
        Holidays h = createHolidays(createFixed(1, Month.JANUARY), createFixed(3, Month.MARCH),
                createFixed(5, Month.MAY, 2011, null));
        Set<Holiday> set = new HashSet<Holiday>();
        fixedParser.parse(2010, set, h);
        containsAll(new ArrayList<Holiday>(set), calendarUtil.create(2010, 1, 1), calendarUtil.create(2010, 3, 3));
    }

    @Test
    public void testFixedWithMoving() {
        Holidays h = createHolidays(
                createFixed(8, Month.JANUARY, createMoving(Weekday.SATURDAY, With.PREVIOUS, Weekday.FRIDAY)),
                createFixed(23, Month.JANUARY, createMoving(Weekday.SUNDAY, With.NEXT, Weekday.MONDAY)));
        Set<Holiday> set = new HashSet<Holiday>();
        fixedParser.parse(2011, set, h);
        containsAll(new ArrayList<Holiday>(set), calendarUtil.create(2011, 1, 7), calendarUtil.create(2011, 1, 24));
    }

    @Test
    public void testCyle2YearsInvalid() {
        Fixed fixed = createFixed(4, Month.JANUARY);
        fixed.setValidFrom(2010);
        fixed.setEvery("2_YEARS");
        Holidays holidays = createHolidays(fixed);
        Set<Holiday> set = new HashSet<Holiday>();
        fixedParser.parse(2011, set, holidays);
        Assert.assertTrue("Expected to be empty.", set.isEmpty());
    }

    @Test
    public void testCyle3Years() {
        Fixed fixed = createFixed(4, Month.JANUARY);
        fixed.setValidFrom(2010);
        fixed.setEvery("3_YEARS");
        Holidays holidays = createHolidays(fixed);
        Set<Holiday> set = new HashSet<Holiday>();
        fixedParser.parse(2013, set, holidays);
        Assert.assertEquals("Wrong number of holidays.", 1, set.size());
    }

    private void containsAll(List<Holiday> list, LocalDate... dates) {
        Assert.assertEquals("Number of holidays.", dates.length, list.size());
        List<LocalDate> expected = new ArrayList<LocalDate>(Arrays.asList(dates));
        Collections.sort(expected);
        Collections.sort(list, new HolidayComparator());
        for (int i = 0; i < expected.size(); i++) {
            Assert.assertEquals("Missing date.", expected.get(i), list.get(i).getDate());
        }
    }

    public Holidays createHolidays(Fixed... fs) {
        Holidays h = new Holidays();
        h.getFixed().addAll(Arrays.asList(fs));
        return h;
    }

    /**
     * @return
     */
    public Fixed createFixed(int day, Month m, MovingCondition... mc) {
        Fixed f = new Fixed();
        f.setDay(day);
        f.setMonth(m);
        f.getMovingCondition().addAll(Arrays.asList(mc));
        return f;
    }

    public Fixed createFixed(int day, Month m, Integer validFrom, Integer validUntil, MovingCondition... mc) {
        Fixed f = createFixed(day, m, mc);
        f.setValidFrom(validFrom);
        f.setValidTo(validUntil);
        return f;
    }

    public MovingCondition createMoving(Weekday substitute, With with, Weekday weekday) {
        MovingCondition mc = new MovingCondition();
        mc.setSubstitute(substitute);
        mc.setWith(with);
        mc.setWeekday(weekday);
        return mc;
    }

}
