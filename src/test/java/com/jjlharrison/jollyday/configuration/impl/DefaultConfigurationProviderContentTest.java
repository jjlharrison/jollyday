package com.jjlharrison.jollyday.configuration.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

public class DefaultConfigurationProviderContentTest {

    private static final Set<String> KEYS_DEFAULT_CONFIG = new HashSet<String>(Arrays.asList("manager.impl",
            "manager.impl.jp","configuration.datasource.impl","parser.impl.com.jjlharrison.config.Fixed",
            "parser.impl.com.jjlharrison.config.FixedWeekdayInMonth", "parser.impl.com.jjlharrison.config.IslamicHoliday",
            "parser.impl.com.jjlharrison.config.ChristianHoliday", "parser.impl.com.jjlharrison.config.RelativeToFixed",
            "parser.impl.com.jjlharrison.config.RelativeToWeekdayInMonth",
            "parser.impl.com.jjlharrison.config.FixedWeekdayBetweenFixed",
            "parser.impl.com.jjlharrison.config.FixedWeekdayRelativeToFixed",
            "parser.impl.com.jjlharrison.config.EthiopianOrthodoxHoliday",
            "parser.impl.com.jjlharrison.config.RelativeToEasterSunday"));

    DefaultConfigurationProvider configurationProvider = new DefaultConfigurationProvider();

    @Test
    public void testPutConfiguration() {
        Properties p = configurationProvider.getProperties();
        assertFalse("Properties shouldn't be empty.", p.isEmpty());
        assertEquals("Default properties are not as expected.", KEYS_DEFAULT_CONFIG, p.keySet());
    }

}
