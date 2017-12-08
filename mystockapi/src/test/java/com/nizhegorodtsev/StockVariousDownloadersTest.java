package com.nizhegorodtsev;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by senizhegorodtsev on 7/8/2015.
 * Tests downloads from various sources: Yahoo, Google, etc
 */
public class StockVariousDownloadersTest {

    @Test
    public void testGoogleStockDownload() throws Exception {
        StockGoogleMapping stockMapping = new StockGoogleMapping();
        StockDownloader stockDownloader = new StockDownloader(stockMapping);
        Stock stock = stockDownloader.Download("FB");

        assertNotNull(stock);
        assertTrue(stock.getLastTrade() > 0);
        assertTrue(stock.getTicker().equals("FB"));
        assertTrue(stock.getChange() != null);

    }
    @Test
    public void testYahooStockDownload() throws Exception {

        StockYahooMapping stockYahooMapping = new StockYahooMapping();
        StockDownloader stockDownloader = new StockDownloader(stockYahooMapping);

        Stock stock = stockDownloader.Download("FB");

        assertNotNull(stock);
        assertTrue(stock.getAsk() > 0);
        assertTrue(stock.getName().contains("Facebook"));
        assertTrue(stock.getChange() != null);
    }
    @Test
    public void testMarkitStockDownload() throws Exception {

        StockMarkitondemandMapping stockMapping = new StockMarkitondemandMapping();
        StockDownloader stockDownloader = new StockDownloader(stockMapping);

        Stock stock = stockDownloader.Download("PAYC");

        assertNotNull(stock);
        assertTrue(stock.getLastTrade() > 0);
        assertTrue(stock.getTicker().equals("PAYC"));
        assertTrue(stock.getName().contains("Paycom Software Inc"));
        assertTrue(stock.getChange() != null);
    }
}