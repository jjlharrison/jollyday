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
package com.jjlharrison.jollyday.tests.parsers;

import java.util.HashSet;
import java.util.Set;

import com.jjlharrison.jollyday.Holiday;
import com.jjlharrison.jollyday.util.CalendarUtil;
import junit.framework.Assert;

import org.junit.Test;

import com.jjlharrison.config.FixedWeekdayInMonth;
import com.jjlharrison.config.Holidays;
import com.jjlharrison.config.Month;
import com.jjlharrison.config.RelativeToWeekdayInMonth;
import com.jjlharrison.config.Weekday;
import com.jjlharrison.config.When;
import com.jjlharrison.config.Which;
import com.jjlharrison.jollyday.parser.impl.RelativeToWeekdayInMonthParser;

/**
 * @author svdi1de
 *
 */
public class RelativeToWeekdayInMonthParserTest {

    private RelativeToWeekdayInMonthParser rtwim = new RelativeToWeekdayInMonthParser();
    private CalendarUtil calendarUtil = new CalendarUtil();

    @Test
    public void testEmpty() {
        Set<Holiday> result = new HashSet<Holiday>();
        Holidays config = new Holidays();
        rtwim.parse(2011, result, config);
        Assert.assertTrue("Result is not empty.", result.isEmpty());
    }

    @Test
    public void testInvalid() {
        Set<Holiday> result = new HashSet<Holiday>();
        Holidays config = new Holidays();
        RelativeToWeekdayInMonth rule = new RelativeToWeekdayInMonth();
        rule.setWeekday(Weekday.TUESDAY);
        rule.setWhen(When.AFTER);
        FixedWeekdayInMonth date = new FixedWeekdayInMonth();
        date.setWhich(Which.SECOND);
        date.setWeekday(Weekday.MONDAY);
        date.setMonth(Month.JULY);
        rule.setFixedWeekday(date);
        config.getRelativeToWeekdayInMonth().add(rule);
        rule.setValidFrom(2012);
        rtwim.parse(2011, result, config);
        Assert.assertTrue("Result is not empty.", result.isEmpty());
    }

    @Test
    public void testTueAfter2ndMondayJuly() {
        Set<Holiday> result = new HashSet<Holiday>();
        Holidays config = new Holidays();
        RelativeToWeekdayInMonth rule = new RelativeToWeekdayInMonth();
        rule.setWeekday(Weekday.TUESDAY);
        rule.setWhen(When.AFTER);
        FixedWeekdayInMonth date = new FixedWeekdayInMonth();
        date.setWhich(Which.SECOND);
        date.setWeekday(Weekday.MONDAY);
        date.setMonth(Month.JULY);
        rule.setFixedWeekday(date);
        config.getRelativeToWeekdayInMonth().add(rule);
        rtwim.parse(2011, result, config);
        Assert.assertEquals("Wrong number of dates.", 1, result.size());
        Assert.assertEquals("Wrong date.", calendarUtil.create(2011, 7, 12), result.iterator().next().getDate());
    }

}