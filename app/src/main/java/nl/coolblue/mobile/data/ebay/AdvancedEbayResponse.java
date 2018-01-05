package nl.coolblue.mobile.data.ebay;

import java.util.List;

/**
 * Created by philippecreytens on 05/01/2018.
 */

public class AdvancedEbayResponse implements EbayTopResponse {
    List<DetailEbayResponse> findItemsAdvancedResponse;

    public DetailEbayResponse getResponse(){ return findItemsAdvancedResponse != null && findItemsAdvancedResponse.size() > 0 ? findItemsAdvancedResponse.get(0): null;}

}
