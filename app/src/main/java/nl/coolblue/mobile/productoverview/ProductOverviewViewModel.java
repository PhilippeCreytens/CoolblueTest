package nl.coolblue.mobile.productoverview;

import java.util.List;

import nl.coolblue.mobile.models.ProductModel;

/**
 * Created by philippecreytens on 04/01/2018.
 */

public interface ProductOverviewViewModel {

    String getLoadingText ();

    List<ProductModel> getProductsList();

    Boolean getIsLoading();

    void loadAllProducts();

    void searchProduct(String searchQuery);

}
