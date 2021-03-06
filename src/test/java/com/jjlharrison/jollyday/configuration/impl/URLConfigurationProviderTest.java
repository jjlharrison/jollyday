/**
 * Copyright 2012 Sven Diedrichsen
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
package com.jjlharrison.jollyday.configuration.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import com.jjlharrison.jollyday.configuration.ConfigurationProvider;
import org.junit.After;
import org.junit.Test;

public class URLConfigurationProviderTest
{

    private ConfigurationProvider urlConfigurationProvider = new URLConfigurationProvider();

    @Test public void testPutConfigurationWithPropertyNotSet()
    {
        Properties props = urlConfigurationProvider.getProperties();
        assertTrue(props.isEmpty());
    }

    @After public void teardown()
    {
        System.clearProperty(ConfigurationProvider.CONFIG_URLS_PROPERTY);
    }

    @Test public void testPutConfigurationWithPropertySetEmpty()
    {
        System.setProperty(ConfigurationProvider.CONFIG_URLS_PROPERTY, "");
        Properties props = urlConfigurationProvider.getProperties();
        assertTrue(props.isEmpty());
    }

    @Test public void testPutConfigurationWithPropertyWithIllegalURL()
    {
        System.setProperty(ConfigurationProvider.CONFIG_URLS_PROPERTY, "TestIllegalData");
        Properties props = urlConfigurationProvider.getProperties();
        assertTrue(props.isEmpty());
    }

    @Test public void testPutConfigurationWithPropertyWithCorrectURL()
    {
        System.setProperty(ConfigurationProvider.CONFIG_URLS_PROPERTY, "file:./src/test/resources/url.load.properties");
        Properties props = urlConfigurationProvider.getProperties();
        assertFalse(props.isEmpty());
        // FIXME assertEquals("Wrong new property.", "DefaultHolidayManager", props.getProperty("manager.impl.test"));
        assertEquals("Wrong overloaded property.", "ManagerOverloaded", props.getProperty("manager.impl"));
    }

}
