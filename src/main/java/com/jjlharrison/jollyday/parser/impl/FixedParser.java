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
import com.jjlharrison.config.Fixed;
import com.jjlharrison.config.Holidays;

/**
 * The Class FixedParser. Parses a fixed date
 *
 * @author tboven
 * @version $Id: $
 */
public class FixedParser extends AbstractHolidayParser
{

    /*
     * (non-Javadoc)
     *
     * @see HolidayParser#parse(int, java.util.Set,
     * com.jjlharrison.config.Holidays)
     */
    /** {@inheritDoc} */
    public void parse(int year, Set<Holiday> holidays, final Holidays config) {
        for (Fixed f : config.getFixed()) {
            if (!isValid(f, year)) {
                continue;
            }
            LocalDate date = calendarUtil.create(year, f);
            LocalDate movedDate = moveDate(f, date);
            HolidayType type = xmlUtil.getType(f.getLocalizedType());
            Holiday h = new Holiday(movedDate, f.getDescriptionPropertiesKey(), type);
            holidays.add(h);
        }
    }

}
