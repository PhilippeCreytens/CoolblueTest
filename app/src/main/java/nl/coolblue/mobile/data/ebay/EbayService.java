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


    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://svcs.ebay.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
