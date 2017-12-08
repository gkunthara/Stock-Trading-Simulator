package com.nizhegorodtsev;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by senizhegorodtsev on 7/7/2015.
 */
public class StockYahooMappingTest {

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testBuildFetchUrl() throws Exception {
        StockYahooMapping stockYahooMapping = new StockYahooMapping();
        assertNotNull(stockYahooMapping);

        String result = stockYahooMapping.BuildFetchUrl("FB");
        assertTrue(result.startsWith("http://finance.yahoo.com/d/quotes.csv?s=FB"));
        assertTrue(result.contains("b4"));
        assertTrue(result.contains("t6"));
        assertTrue(result.contains("x"));
    }
}