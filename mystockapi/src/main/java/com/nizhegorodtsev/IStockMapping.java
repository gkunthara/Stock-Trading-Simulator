package com.nizhegorodtsev;

/**
 * Created by senizhegorodtsev on 7/6/2015.
 * Operates with stock mapping from the string recieved from
 * the Internet sources Yahoo finance, Google finance
 */
public interface IStockMapping {
    Stock MapStock(String resultString);
    String BuildFetchUrl(String ticker);
}
