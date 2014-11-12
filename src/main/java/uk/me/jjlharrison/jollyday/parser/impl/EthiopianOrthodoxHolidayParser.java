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
package uk.me.jjlharrison.jollyday.parser.impl;

import java.util.Set;

import org.joda.time.LocalDate;

import uk.me.jjlharrison.jollyday.Holiday;
import uk.me.jjlharrison.jollyday.HolidayType;
import uk.me.jjlharrison.config.EthiopianOrthodoxHoliday;
import uk.me.jjlharrison.config.Holidays;
import uk.me.jjlharrison.jollyday.parser.AbstractHolidayParser;

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
     * uk.me.jjlharrison.config.Holidays)
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
