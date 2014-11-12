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
import uk.me.jjlharrison.config.FixedWeekdayBetweenFixed;
import uk.me.jjlharrison.config.Holidays;
import uk.me.jjlharrison.config.Month;
import uk.me.jjlharrison.config.Weekday;
import uk.me.jjlharrison.jollyday.parser.impl.FixedWeekdayBetweenFixedParser;
import uk.me.jjlharrison.jollyday.util.CalendarUtil;

/**
 * @author svdi1de
 *
 */
public class FixedWeekdayBetweenFixedParserTest extends FixedParserTest {

    private FixedWeekdayBetweenFixedParser parser = new FixedWeekdayBetweenFixedParser();
    private CalendarUtil calendarUtil = new CalendarUtil();

    @Test
    public void testEmpty() {
        Set<Holiday> holidays = new HashSet<Holiday>();
        Holidays config = new Holidays();
        parser.parse(2010, holidays, config);
        Assert.assertTrue("Expected to be empty.", holidays.isEmpty());
    }

    @Test
    public void testInvalid() {
        Set<Holiday> holidays = new HashSet<Holiday>();
        Holidays config = new Holidays();
        FixedWeekdayBetweenFixed e = new FixedWeekdayBetweenFixed();
        e.setValidTo(2009);
        config.getFixedWeekdayBetweenFixed().add(e);
        parser.parse(2010, holidays, config);
        Assert.assertTrue("Expected to be empty.", holidays.isEmpty());
    }

    @Test
    public void testWednesday() {
        Set<Holiday> holidays = new HashSet<Holiday>();
        Holidays config = new Holidays();
        FixedWeekdayBetweenFixed e = new FixedWeekdayBetweenFixed();
        e.setFrom(createFixed(17, Month.JANUARY));
        e.setTo(createFixed(21, Month.JANUARY));
        e.setWeekday(Weekday.WEDNESDAY);
        config.getFixedWeekdayBetweenFixed().add(e);
        parser.parse(2011, holidays, config);
        Assert.assertEquals("Wrong number of results.", 1, holidays.size());
        Assert.assertEquals("Wrong date.", calendarUtil.create(2011, 1, 19), holidays.iterator().next().getDate());
    }

}
