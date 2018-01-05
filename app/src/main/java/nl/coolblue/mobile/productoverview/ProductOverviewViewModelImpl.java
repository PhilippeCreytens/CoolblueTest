package nl.coolblue.mobile.productoverview;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.coolblue.mobile.BR;
import nl.coolblue.mobile.data.bl.ProductOverviewRepository;
import nl.coolblue.mobile.data.bl.ProductOverviewDataObserver;
import nl.coolblue.mobile.data.ebay.ConnectivityCheck;
import nl.coolblue.mobile.data.models.ProductModel;

/**
 * Created by philippecreytens on 04/01/2018.
 */

public class ProductOverviewViewModelImpl extends BaseObservable implements ProductOverviewViewModel, ProductOverviewDataObserver {


    private boolean loadingProducts = false;
    private boolean firstLoad = true;
    private boolean isSearching = false;

    private String searchQuery = "";
    private String minPrice = "";
    private String maxPrice = "";
    private String loadingText = "";
    private List<ProductModel> productsList;
    private final ProductOverviewRepository dataAccess;

    public ProductOverviewViewModelImpl(ConnectivityCheck connectivityCheck){
        productsList = Collections.emptyList();
        dataAccess = new ProductOverviewRepository(connectivityCheck);
        dataAccess.registerObserver(this);
    }

    @Override
    public void loadAllProducts() {
        // check if not loading already
        if(!loadingProducts) {
            isSearching = false;
            notifyPropertyChanged(BR.isSearching);
            // notify loading is in progress
            loadingProducts = true;
            notifyPropertyChanged(BR.isLoading);
            // notify loading if the first time loading the list
            if(firstLoad) {
                loadingText = "Inladen van producten";
                notifyPropertyChanged(BR.loadingText);
                firstLoad = false;
            }
            if(!dataAccess.loadAllProducts()){
                // notify no internet connection is available
                errorOccured("Geen internet beschikbaar");
            }
        }
    }

    @Override
    public void searchProduct(String searchQuery) {
        if(!loadingProducts) {
            this.searchQuery = searchQuery;
            // notify loading is in progress
            loadingProducts = true;
            notifyPropertyChanged(BR.isLoading);
            isSearching = true;
            notifyPropertyChanged(BR.isSearching);
            if(!dataAccess.loadSearchedProducts(searchQuery, minPrice, maxPrice)){
                isSearching = false;
                notifyPropertyChanged(BR.isSearching);
                // notify no internet connection is available
                errorOccured("Geen internet beschikbaar");
            }
        }
    }

    @Override
    public void searchProductWithPrice(String minPrice, String maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        searchProduct(searchQuery);
    }

    @Override
    public void errorOccured(String errorMessage) {
        // reset list
        this.productsList = new ArrayList<>();
        notifyPropertyChanged(BR.productsList);
        this.loadingText = errorMessage;
        notifyPropertyChanged(BR.loadingText);
        loadingProducts = false;
        notifyPropertyChanged(BR.isLoading);
    }

    @Override
    public void productsLoaded(List<ProductModel> products) {
        this.productsList = products;
        notifyPropertyChanged(BR.productsList);
        loadingText = "";
        notifyPropertyChanged(BR.loadingText);
        loadingProducts = false;
        notifyPropertyChanged(BR.isLoading);
    }

    @Bindable public List<ProductModel> getProductsList(){return productsList;}

    @Bindable public String getLoadingText(){ return loadingText; }

    @Bindable public Boolean getIsSearching(){ return isSearching; }

    @Bindable public Boolean getIsLoading(){ return loadingProducts; }
}
