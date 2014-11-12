package uk.me.jjlharrison.jollyday.configuration.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

public class DefaultConfigurationProviderContentTest {

    private static final Set<String> KEYS_DEFAULT_CONFIG = new HashSet<String>(Arrays.asList("manager.impl",
            "manager.impl.jp","configuration.datasource.impl","parser.impl.uk.me.jjlharrison.config.Fixed",
            "parser.impl.uk.me.jjlharrison.config.FixedWeekdayInMonth", "parser.impl.uk.me.jjlharrison.config.IslamicHoliday",
            "parser.impl.uk.me.jjlharrison.config.ChristianHoliday", "parser.impl.uk.me.jjlharrison.config.RelativeToFixed",
            "parser.impl.uk.me.jjlharrison.config.RelativeToWeekdayInMonth",
            "parser.impl.uk.me.jjlharrison.config.FixedWeekdayBetweenFixed",
            "parser.impl.uk.me.jjlharrison.config.FixedWeekdayRelativeToFixed",
            "parser.impl.uk.me.jjlharrison.config.EthiopianOrthodoxHoliday",
            "parser.impl.uk.me.jjlharrison.config.RelativeToEasterSunday"));

    DefaultConfigurationProvider configurationProvider = new DefaultConfigurationProvider();

    @Test
    public void testPutConfiguration() {
        Properties p = configurationProvider.getProperties();
        assertFalse("Properties shouldn't be empty.", p.isEmpty());
        assertEquals("Default properties are not as expected.", KEYS_DEFAULT_CONFIG, p.keySet());
    }

}
