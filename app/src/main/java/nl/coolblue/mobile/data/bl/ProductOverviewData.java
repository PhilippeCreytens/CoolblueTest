package nl.coolblue.mobile.data.bl;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;

import nl.coolblue.mobile.BR;
import nl.coolblue.mobile.data.ebay.CategoryEbayResponse;
import nl.coolblue.mobile.data.ebay.ConnectivityCheck;
import nl.coolblue.mobile.data.ebay.EbayProduct;
import nl.coolblue.mobile.data.ebay.EbayService;
import nl.coolblue.mobile.models.ProductModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by philippecreytens on 05/01/2018.
 */

public class ProductOverviewData implements ProductOverviewObersable {

    private static final String ebay_app_id = "Philippe-Coolblue-PRD-b5d7a0307-6c4fbd0f";
    private ArrayList<ProductOverviewDataObserver> mObservers;

    private final ConnectivityCheck connectivityCheck;
    public ProductOverviewData(ConnectivityCheck connectivityCheck){
        this.connectivityCheck = connectivityCheck;
        this.mObservers = new ArrayList<>();
    }

    public boolean loadAllProducts(){
        if(connectivityCheck.isOnline()){
            // perform call to collect all products
            EbayService service = EbayService.retrofit.create(EbayService.class);
            Call<CategoryEbayResponse> call = service.getAllItemsForTech(ebay_app_id);
            call.enqueue(new Callback<CategoryEbayResponse>() {
                @Override
                public void onResponse(Call<CategoryEbayResponse> call, Response<CategoryEbayResponse> response) {
                    // great success
                    if(response.body() != null) {
                        // format result to become a products list
                        ArrayList<ProductModel> productsList = new ArrayList<>();
                        for(int i = 0; i<response.body().getFindItemsByCategoryResponse().getSearchResult().item.size(); i++){
                            EbayProduct product = response.body().getFindItemsByCategoryResponse().getSearchResult().item.get(i);
                            productsList.add(new ProductModel(product.getTitle(), "",product.getImageUrl(),"", 0));
                        }
                        // notify formatting is succeeded
                        notifySuccessObservers(productsList);
                    }else{
                        // notify an error has occured
                        notifyErrorObservers("error "+response.toString());
                    }
                }

                @Override
                public void onFailure(Call<CategoryEbayResponse> call, Throwable t) {
                    // fail
                    // notify an error has occured
                    notifyErrorObservers( "Oops er ging iets verkeerd, gelieve later opnieuw te proberen.");
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public void registerObserver(ProductOverviewDataObserver observer) {
        if(!mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    @Override
    public void removeObserver(ProductOverviewDataObserver observer) {
        if(mObservers.contains(observer)) {
            mObservers.remove(observer);
        }
    }

    @Override
    public void notifySuccessObservers(List<ProductModel> products) {
        for (ProductOverviewDataObserver observer: mObservers) {
            observer.productsLoaded(products);
        }
    }

    @Override
    public void notifyErrorObservers(String errorMessage){
        for (ProductOverviewDataObserver observer: mObservers) {
            observer.errorOccured(errorMessage);
        }
    }

}
