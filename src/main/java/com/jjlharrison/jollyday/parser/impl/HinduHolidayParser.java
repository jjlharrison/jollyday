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
import com.jjlharrison.config.HinduHoliday;
import com.jjlharrison.config.Holidays;

/**
 * <p>HinduHolidayParser class.</p>
 *
 * @author Sven
 * @version $Id: $
 */
public class HinduHolidayParser extends AbstractHolidayParser
{

    /** {@inheritDoc} */
    public void parse(int year, Set<Holiday> holidays, final Holidays config) {
        for (HinduHoliday hh : config.getHinduHoliday()) {
            if (!isValid(hh, year))
                continue;
            switch (hh.getType()) {
            case HOLI:
                // 20 February and ending on 21 March (20th march in leap years)
                // TODO: Calculate with hindu calendar.
                break;
            default:
                throw new IllegalArgumentException("Unknown hindu holiday "
                        + hh.getType());
            }
        }
    }

}
