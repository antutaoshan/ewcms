/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.common.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

/**
 * 测试Timestamp转换
 * 
 * @author 王伟
 */
public class SqlTimestampConvertTest {

    private ConvertDateable<Timestamp> handler;

    @Before
    public void setUp() {
        handler = new SqlTimestampConvert();
    }

    @Test
    public void testParseSuccess() throws Exception {

        String test = "2005-01-02 21:23:34";
        Timestamp timestamp = handler.parse(test);
        assertEqualsTimestamp(timestamp,2005,0,2,21,23,34);

        test = "2005-2-6 21:23:34";
        timestamp = handler.parse(test);
        assertEqualsTimestamp(timestamp,2005,1,6,21,23,34);

        test = "2005/06/01";
        handler.setFormat("yyyy/MM/dd");
        timestamp = handler.parse(test);
        assertEqualsTimestamp(timestamp, 2005, 5, 1,0,0,0);
    }

    private void assertEqualsTimestamp(Timestamp date,
            int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        assertEquals(year, calendar.get(Calendar.YEAR));
        assertEquals(month, calendar.get(Calendar.MONTH));
        assertEquals(day, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(hour, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(minute, calendar.get(Calendar.MINUTE));
        assertEquals(second, calendar.get(Calendar.SECOND));
    }

    @Test(expected=ConvertException.class)
    public void testParseFail() throws Exception {

        String test = "2005-2-6";
        assertNull(handler.parse(test));
    }

    @Test
    public void testParseString() throws Exception {
        String test = "2005-01-02 21:23:34";
        Timestamp testTimestamp = (Timestamp) handler.parse(test);
        assertEquals(handler.parseString(testTimestamp), test);
    }
}
