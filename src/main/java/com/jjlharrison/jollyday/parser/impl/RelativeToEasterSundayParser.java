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
package com.jjlharrison.jollyday.parser.impl;

import java.util.Set;

import com.jjlharrison.jollyday.Holiday;
import com.jjlharrison.jollyday.parser.AbstractHolidayParser;
import org.joda.time.LocalDate;

import com.jjlharrison.config.HolidayType;
import com.jjlharrison.config.Holidays;
import com.jjlharrison.config.RelativeToEasterSunday;

/**
 * This parser creates holidays relative to easter sunday.
 *
 * @author Sven Diedrichsen
 * @version $Id: $
 */
public class RelativeToEasterSundayParser extends AbstractHolidayParser
{

    /**
     * Properties prefix for christian holidays names.
     */
    private static final String PREFIX_PROPERTY_CHRISTIAN = "christian.";

    /**
     * {@inheritDoc}
     *
     * Parses relative to easter sunday holidays.
     */
    public void parse(int year, Set<Holiday> holidays, Holidays config) {
        for (RelativeToEasterSunday ch : config.getRelativeToEasterSunday()) {
            if (!isValid(ch, year)) {
                continue;
            }
            LocalDate easterSunday = getEasterSunday(year, ch.getChronology()).plusDays(ch.getDays());
            String propertiesKey = PREFIX_PROPERTY_CHRISTIAN + ch.getDescriptionPropertiesKey();
            addChrstianHoliday(easterSunday, propertiesKey, ch.getLocalizedType(), holidays);
        }
    }

    /**
     * Adds the given day to the list of holidays.
     *
     * @param day
     *            a {@link org.joda.time.LocalDate} object.
     * @param propertiesKey
     *            a {@link java.lang.String} object.
     * @param holidayType
     *            a {@link com.jjlharrison.config.HolidayType} object.
     * @param holidays
     *            a {@link java.util.Set} object.
     */
    protected void addChrstianHoliday(LocalDate day, String propertiesKey, HolidayType holidayType,
            Set<Holiday> holidays) {
        LocalDate convertedDate = calendarUtil.convertToISODate(day);
        com.jjlharrison.jollyday.HolidayType type = xmlUtil.getType(holidayType);
        Holiday h = new Holiday(convertedDate, propertiesKey, type);
        holidays.add(h);
    }

}
