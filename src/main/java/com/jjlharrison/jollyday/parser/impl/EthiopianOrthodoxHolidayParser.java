/**
 * Copyright 2010 Sven Diedrichsen
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

import com.jjlharrison.jollyday.HolidayType;
import com.jjlharrison.config.EthiopianOrthodoxHoliday;
import com.jjlharrison.config.Holidays;

/**
 * Calculates the ethiopian orthodox holidays.
 *
 * @author Sven Diedrichsen
 * @version $Id: $
 */
public class EthiopianOrthodoxHolidayParser extends AbstractHolidayParser
{

    /**
     * Ethiopian orthodox properties prefix.
     */
    private static final String PREFIXE_PROPERTY_ETHIOPIAN_ORTHODOX = "ethiopian.orthodox.";

    /*
     * (non-Javadoc)
     *
     * @see HolidayParser#parse(int, java.util.Set,
     * com.jjlharrison.config.Holidays)
     */
    /** {@inheritDoc} */
    public void parse(int year, Set<Holiday> holidays, Holidays config) {
        for (EthiopianOrthodoxHoliday h : config.getEthiopianOrthodoxHoliday()) {
            if (!isValid(h, year)) {
                continue;
            }
            Set<LocalDate> ethiopianHolidays = null;
            switch (h.getType()) {
            case TIMKAT:
                ethiopianHolidays = calendarUtil.getEthiopianOrthodoxHolidaysInGregorianYear(year, 5, 10);
                break;
            case ENKUTATASH:
                ethiopianHolidays = calendarUtil.getEthiopianOrthodoxHolidaysInGregorianYear(year, 1, 1);
                break;
            case MESKEL:
                ethiopianHolidays = calendarUtil.getEthiopianOrthodoxHolidaysInGregorianYear(year, 1, 17);
                break;
            default:
                throw new IllegalArgumentException("Unknown ethiopian orthodox holiday type " + h.getType());
            }
            String propertiesKey = PREFIXE_PROPERTY_ETHIOPIAN_ORTHODOX + h.getType().name();
            HolidayType type = xmlUtil.getType(h.getLocalizedType());
            for (LocalDate d : ethiopianHolidays) {
                holidays.add(new Holiday(d, propertiesKey, type));
            }
        }
    }

}
