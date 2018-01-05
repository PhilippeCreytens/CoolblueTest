package nl.coolblue.mobile.data.ebay;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by philippecreytens on 04/01/2018.
 */

public interface EbayService {

    @GET("services/search/FindingService/v1?" +
            "OPERATION-NAME=findItemsByCategory&" +
            "X-EBAY-SOA-GLOBAL-ID=EBAY-NLBE&" +
            "SERVICE-VERSION=1.0.0&" +
            "RESPONSE-DATA-FORMAT=JSON&" +
            "REST-PAYLOAD" +
            "&categoryId=58058")
    Call<CategoryEbayResponse> getAllItemsForTech(@Query("SECURITY-APPNAME") String appName);


    @GET("services/search/FindingService/v1?" +
            "OPERATION-NAME=findItemsAdvanced&" +
            "X-EBAY-SOA-GLOBAL-ID=EBAY-NLBE&" +
            "SERVICE-VERSION=1.0.0&" +
            "RESPONSE-DATA-FORMAT=JSON&" +
            "REST-PAYLOAD" +
            "&categoryId=58058")
    Call<AdvancedEbayResponse> getAllItemsForTechWithSearch(@Query("SECURITY-APPNAME") String appName, @Query("keywords") String keywords);

    @GET("services/search/FindingService/v1?" +
            "OPERATION-NAME=findItemsAdvanced&" +
            "X-EBAY-SOA-GLOBAL-ID=EBAY-NLBE&" +
            "SERVICE-VERSION=1.0.0&" +
            "RESPONSE-DATA-FORMAT=JSON&" +
            "REST-PAYLOAD" +
            "&categoryId=58058&" +
            "itemFilter(0).name=MinPrice&" +
            "itemFilter(0).paramName=Currency&" +
            "itemFilter(0).paramValue=EUR")
    Call<AdvancedEbayResponse> getAllItemsForTechWithSearchMin(@Query("SECURITY-APPNAME") String appName, @Query("keywords") String keywords, @Query("itemFilter(0).value") String minPrice);

    @GET("services/search/FindingService/v1?" +
            "OPERATION-NAME=findItemsAdvanced&" +
            "X-EBAY-SOA-GLOBAL-ID=EBAY-NLBE&" +
            "SERVICE-VERSION=1.0.0&" +
            "RESPONSE-DATA-FORMAT=JSON&" +
            "REST-PAYLOAD" +
            "&categoryId=58058&" +
            "itemFilter(0).name=MaxPrice&" +
            "itemFilter(0).paramName=Currency&" +
            "itemFilter(0).paramValue=EUR")
    Call<AdvancedEbayResponse> getAllItemsForTechWithSearchMax(@Query("SECURITY-APPNAME") String appName, @Query("keywords") String keywords, @Query("itemFilter(0).value") String maxPrice);

    @GET("services/search/FindingService/v1?" +
            "OPERATION-NAME=findItemsAdvanced&" +
            "X-EBAY-SOA-GLOBAL-ID=EBAY-NLBE&" +
            "SERVICE-VERSION=1.0.0&" +
            "RESPONSE-DATA-FORMAT=JSON&" +
            "REST-PAYLOAD" +
            "&categoryId=58058&" +
            "itemFilter(0).name=MinPrice&" +
            "itemFilter(0).paramName=Currency&" +
            "itemFilter(0).paramValue=EUR&" +
            "itemFilter(1).name=MaxPrice&" +
            "itemFilter(1).paramName=Currency&" +
            "itemFilter(1).paramValue=EUR")
    Call<AdvancedEbayResponse> getAllItemsForTechWithSearchMinMax(@Query("SECURITY-APPNAME") String appName, @Query("keywords") String keywords, @Query("itemFilter(0).value") String minPrice, @Query("itemFilter(1).value") String maxPrice);


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://svcs.ebay.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
