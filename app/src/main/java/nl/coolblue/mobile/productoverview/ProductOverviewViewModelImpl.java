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
import nl.coolblue.mobile.data.bl.ProductOverviewData;
import nl.coolblue.mobile.data.bl.ProductOverviewDataObserver;
import nl.coolblue.mobile.data.ebay.ConnectivityCheck;
import nl.coolblue.mobile.data.ebay.EbayProduct;
import nl.coolblue.mobile.data.ebay.EbayService;
import nl.coolblue.mobile.data.ebay.CategoryEbayResponse;
import nl.coolblue.mobile.models.ProductModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by philippecreytens on 04/01/2018.
 */

public class ProductOverviewViewModelImpl extends BaseObservable implements ProductOverviewViewModel, ProductOverviewDataObserver {


    private boolean loadingProducts = false;
    private boolean firstLoad = true;
    private String loadingText = "";
    private List<ProductModel> productsList;
    private final ProductOverviewData dataAccess;

    public ProductOverviewViewModelImpl(ConnectivityCheck connectivityCheck){
        productsList = Collections.emptyList();
        dataAccess = new ProductOverviewData(connectivityCheck);
        dataAccess.registerObserver(this);
    }

    @Override
    public void loadAllProducts() {
        // check if not loading already
        if(!loadingProducts) {
            // check internet connection

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
    public void searchProduct(String searchQuery) { }

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

    @Bindable public Boolean getIsLoading(){
        Log.d("bla","get is loading!!");
        return loadingProducts; }

    @BindingAdapter("android:visibility")
    public static void setVisibility(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
