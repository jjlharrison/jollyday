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

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import uk.me.jjlharrison.jollyday.Holiday;
import uk.me.jjlharrison.config.Fixed;
import uk.me.jjlharrison.config.Holidays;
import uk.me.jjlharrison.config.Month;
import uk.me.jjlharrison.config.RelativeToFixed;
import uk.me.jjlharrison.config.Weekday;
import uk.me.jjlharrison.config.When;
import uk.me.jjlharrison.jollyday.parser.impl.RelativeToFixedParser;
import uk.me.jjlharrison.jollyday.util.CalendarUtil;

/**
 * @author Sven
 *
 */
public class RelativeToFixedParserTest {

    private RelativeToFixedParser rtfp = new RelativeToFixedParser();
    private CalendarUtil calendarUtil = new CalendarUtil();

    @Test
    public void testEmpty() {
        Set<Holiday> holidays = new HashSet<Holiday>();
        Holidays config = new Holidays();
        rtfp.parse(2010, holidays, config);
        Assert.assertTrue("Expected to be empty.", holidays.isEmpty());
    }

    @Test
    public void testInvalid() {
        Set<Holiday> holidays = new HashSet<Holiday>();
        Holidays config = new Holidays();
        RelativeToFixed rule = new RelativeToFixed();
        rule.setValidFrom(2011);
        config.getRelativeToFixed().add(rule);
        rtfp.parse(2010, holidays, config);
        Assert.assertTrue("Expected to be empty.", holidays.isEmpty());
    }

    @Test
    public void testWeekday() {
        Set<Holiday> holidays = new HashSet<Holiday>();
        Holidays config = new Holidays();
        RelativeToFixed rule = new RelativeToFixed();
        rule.setWeekday(Weekday.THURSDAY);
        rule.setWhen(When.AFTER);
        Fixed date = new Fixed();
        date.setDay(5);
        date.setMonth(Month.AUGUST);
        rule.setDate(date);
        config.getRelativeToFixed().add(rule);
        rtfp.parse(2011, holidays, config);
        Assert.assertEquals("Number of holidays wrong.", 1, holidays.size());
        Assert.assertEquals("Wrong date.", calendarUtil.create(2011, 8, 11), holidays.iterator().next().getDate());
    }

    @Test
    public void testNumberOfDays() {
        Set<Holiday> holidays = new HashSet<Holiday>();
        Holidays config = new Holidays();
        RelativeToFixed rule = new RelativeToFixed();
        rule.setDays(3);
        rule.setWhen(When.BEFORE);
        Fixed date = new Fixed();
        date.setDay(5);
        date.setMonth(Month.AUGUST);
        rule.setDate(date);
        config.getRelativeToFixed().add(rule);
        rtfp.parse(2011, holidays, config);
        Assert.assertEquals("Number of holidays wrong.", 1, holidays.size());
        Assert.assertEquals("Wrong date.", calendarUtil.create(2011, 8, 2), holidays.iterator().next().getDate());
    }

}
