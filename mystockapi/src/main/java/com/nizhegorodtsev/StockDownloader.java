package com.nizhegorodtsev;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import sun.rmi.runtime.Log;

/**
 * Created by senizhegorodtsev on 7/9/2015.
 * This is a general downloader which can download stock info from different sources
 */
public class StockDownloader implements IStockDownloader {

    private IStockMapping _stockMapping;

    public StockDownloader(IStockMapping stockMapping)
    {
        _stockMapping = stockMapping;
    }

    @Override
    public Stock Download(String ticker) {
        Stock stock = null;
        try {
            URL yahoo = new URL(_stockMapping.BuildFetchUrl(ticker));
            URLConnection connection = yahoo.openConnection();
            InputStreamReader is = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(is);
            // Parse the object
            StringBuilder builder = new StringBuilder();
            String aux;
            while ((aux = br.readLine()) != null) {
                builder.append(aux);
            }
            String text = builder.toString();
            stock = _stockMapping.MapStock(text);
        } catch (Exception e) {

            Logger log = Logger.getLogger(StockDownloader.class.getName());
            log.log(Level.SEVERE, e.toString(), e);
        }
        return stock;
    }
}
