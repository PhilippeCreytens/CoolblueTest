package nl.coolblue.mobile.data.ebay;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by philippecreytens on 05/01/2018.
 */

public class ConnectivityCheckImpl implements ConnectivityCheck {

    private final Context context;

    public ConnectivityCheckImpl(Context context){
        this.context = context;
    }

    @Override
    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
