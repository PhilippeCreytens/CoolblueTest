package nl.coolblue.mobile.data.ebay;

import java.util.List;

/**
 * Created by philippecreytens on 05/01/2018.
 */

public class DetailEbayResponse {

    public List<String> timestamp;

    List<SearchEbayResponse> searchResult;

    public SearchEbayResponse getSearchResult(){ return searchResult != null && searchResult.size() > 0 ? searchResult.get(0) : null; }

}
