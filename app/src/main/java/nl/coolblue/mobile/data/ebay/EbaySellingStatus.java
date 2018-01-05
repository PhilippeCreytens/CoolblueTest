package nl.coolblue.mobile.data.ebay;

import java.util.List;

/**
 * Created by philippecreytens on 05/01/2018.
 */

public class EbaySellingStatus {
    List<EbayPrice> currentPrice;

    public EbayPrice getCurrentPrice(){ return currentPrice.get(0);}
}
