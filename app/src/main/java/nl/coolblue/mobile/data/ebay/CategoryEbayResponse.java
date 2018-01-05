package nl.coolblue.mobile.data.ebay;

import java.util.List;

/**
 * Created by philippecreytens on 04/01/2018.
 */

public class CategoryEbayResponse implements EbayTopResponse{

    List<DetailEbayResponse> findItemsByCategoryResponse;

    public DetailEbayResponse getResponse(){ return findItemsByCategoryResponse != null && findItemsByCategoryResponse.size() > 0 ? findItemsByCategoryResponse.get(0): null;}
}


