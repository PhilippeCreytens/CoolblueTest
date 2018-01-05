package nl.coolblue.mobile.data.bl;

import java.util.List;

import nl.coolblue.mobile.data.models.ProductModel;

/**
 * Created by philippecreytens on 05/01/2018.
 */

public interface ProductOverviewDataObserver {
    void errorOccured (String errorMessage);

    void productsLoaded(List<ProductModel> products);
}
