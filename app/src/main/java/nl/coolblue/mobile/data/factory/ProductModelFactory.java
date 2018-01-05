package nl.coolblue.mobile.data.factory;

import java.util.ArrayList;
import java.util.List;

import nl.coolblue.mobile.data.ebay.EbayProduct;
import nl.coolblue.mobile.data.ebay.EbayTopResponse;
import nl.coolblue.mobile.models.ProductModel;

/**
 * Created by philippecreytens on 05/01/2018.
 */

public class ProductModelFactory {

    public static List<ProductModel> formatEbayResponse(EbayTopResponse response){
        ArrayList<ProductModel> productsList = new ArrayList<>();
        if(response.getResponse() != null && response.getResponse().getSearchResult() != null
                && response.getResponse().getSearchResult().item != null) {
            for (int i = 0; i < response.getResponse().getSearchResult().item.size(); i++) {
                EbayProduct product = response.getResponse().getSearchResult().item.get(i);
                productsList.add(new ProductModel(product.getTitle(), "", product.getImageUrl(), "", 0));
            }
        }
        return productsList;
    }
}
