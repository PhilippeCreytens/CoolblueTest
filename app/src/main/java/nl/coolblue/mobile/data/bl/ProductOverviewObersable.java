package nl.coolblue.mobile.data.bl;

import java.util.List;

import nl.coolblue.mobile.models.ProductModel;

/**
 * Created by philippecreytens on 05/01/2018.
 */

public interface ProductOverviewObersable {
    void registerObserver(ProductOverviewDataObserver observer);

    void removeObserver(ProductOverviewDataObserver observer);

    void notifySuccessObservers(List<ProductModel> products);

    void notifyErrorObservers(String errorMessage);
}
