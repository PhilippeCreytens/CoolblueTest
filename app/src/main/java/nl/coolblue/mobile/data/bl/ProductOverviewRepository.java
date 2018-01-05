package nl.coolblue.mobile.data.bl;

import java.util.ArrayList;
import java.util.List;

import nl.coolblue.mobile.data.ebay.AdvancedEbayResponse;
import nl.coolblue.mobile.data.ebay.CategoryEbayResponse;
import nl.coolblue.mobile.data.ebay.ConnectivityCheck;
import nl.coolblue.mobile.data.ebay.EbayService;
import nl.coolblue.mobile.data.ebay.EbayTopResponse;
import nl.coolblue.mobile.data.factory.ProductModelFactory;
import nl.coolblue.mobile.data.models.ProductModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by philippecreytens on 05/01/2018.
 */

public class ProductOverviewRepository implements ProductOverviewObersable {
    public enum SearchType {
        NO_PRICE,
        MIN_PRICE,
        MAX_PRICE,
        ALL_PRICES
    }
    private static final String ebay_app_id = "Philippe-Coolblue-PRD-b5d7a0307-6c4fbd0f";
    private ArrayList<ProductOverviewDataObserver> mObservers;
    private final EbayService service;

    private final ConnectivityCheck connectivityCheck;
    public ProductOverviewRepository(ConnectivityCheck connectivityCheck){
        this.connectivityCheck = connectivityCheck;
        this.mObservers = new ArrayList<>();
        service = EbayService.retrofit.create(EbayService.class);
    }

    public boolean loadAllProducts(){
        if(connectivityCheck.isOnline()){
            // perform call to collect all products
            Call<CategoryEbayResponse> call = service.getAllItemsForTech(ebay_app_id);
            processCall(call);
            return true;
        }
        return false;
    }

    public boolean loadSearchedProducts(String searchQuery, String minPrice, String maxPrice){
        if(connectivityCheck.isOnline()){
            if(!searchQuery.isEmpty()){
                // check which call to use
                Call<AdvancedEbayResponse> call;
                SearchType searchType = determineCorrectCall(minPrice, maxPrice);
                switch(searchType){
                    case ALL_PRICES:
                        call = service.getAllItemsForTechWithSearchMinMax(ebay_app_id, searchQuery, minPrice, maxPrice);
                        break;
                    case MIN_PRICE:
                        call = service.getAllItemsForTechWithSearchMin(ebay_app_id, searchQuery, minPrice);
                        break;
                    case MAX_PRICE:
                        call = service.getAllItemsForTechWithSearchMax(ebay_app_id, searchQuery, maxPrice);
                        break;
                    default:
                        call = service.getAllItemsForTechWithSearch(ebay_app_id, searchQuery);
                }

                processCall(call);
            }else{
                return loadAllProducts();
            }
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

    <T> void processCall(Call<T> call){
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                // great success
                if(response.body() != null) {
                    // format result to become a products list
                    List<ProductModel> productsList = ProductModelFactory.formatEbayResponse((EbayTopResponse)response.body());
                    if(productsList.size() > 0) {
                        // notify formatting is succeeded
                        notifySuccessObservers(productsList);
                    }else{
                        notifyErrorObservers( "Oops geen producten zijn gevonden.");
                    }
                }else{
                    // notify an error has occured
                    notifyErrorObservers("error "+response.toString());
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                // fail
                // notify an error has occured
                notifyErrorObservers( "Oops er ging iets verkeerd, gelieve later opnieuw te proberen.");
            }
        });
    }

    public SearchType determineCorrectCall(String minPrice, String maxPrice){
        if(isValidPrice(minPrice) && isValidPrice(maxPrice)){
            Double epsilon = 1e-15;
            if(Double.parseDouble(maxPrice)-Double.parseDouble(minPrice) > epsilon)
                return SearchType.ALL_PRICES;
            else
                return SearchType.MIN_PRICE;
        }else if(isValidPrice(minPrice)){
            return SearchType.MIN_PRICE;
        }else if(isValidPrice(maxPrice)) {
            return SearchType.MAX_PRICE;
        }
        return SearchType.NO_PRICE;

    }

    public boolean isValidPrice (String price){
        return !(price.isEmpty() || price.equals("0") || !price.matches("\\d*\\.?\\d+"));
    }
}
