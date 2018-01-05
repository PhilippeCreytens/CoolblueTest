package nl.coolblue.mobile.data.ebay;

import java.util.List;

/**
 * Created by philippecreytens on 04/01/2018.
 */

public class CategoryEbayResponse {

    List<CategoryDetailEbayResponse> findItemsByCategoryResponse;

    public CategoryDetailEbayResponse getFindItemsByCategoryResponse(){ return findItemsByCategoryResponse != null && findItemsByCategoryResponse.size() > 0 ? findItemsByCategoryResponse.get(0): null;}
}


