package in.codehex.office;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by Kesavaraj on 5/8/2016.
 */
public class Internet {

    /*
    * Checks if WiFi or 3G is enabled or not
    */
    public static boolean isInternetAvailable(Context context) {
        return isWiFiAvailable(context) || isMobileDateAvailable(context);
    }

    /**
     * Checks if the WiFi is enabled on user's device
     */
    public static boolean isWiFiAvailable(Context context) {
        // ConnectivityManager is used to check available wifi network
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network_info = connectivityManager.getActiveNetworkInfo();
        // Wifi network is available.
        return network_info != null
                && network_info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Checks if the mobile data is enabled on user's device
     */
    public static boolean isMobileDateAvailable(Context context) {
        // ConnectivityManager is used to check available 3G network
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // 3G network is available
        return networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

}
