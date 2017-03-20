package com.devindicator.hivaids.models;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by bangujo on 06/03/2017.
 */

public class Helper {
    public static final String URL_DETAILS = "http://tenderindicator.com/hiv/api/details.php";
    public static final String URL_PAGE = "http://tenderindicator.com/hiv/api/page.php";
    public static final String URL_NEWS = "http://tenderindicator.com/hiv/api/page.php";
    public static final String URL_CATEGORIES = "http://tenderindicator.com/hiv/api/page.php";
    public static final String URL_ARTICLES = "http://tenderindicator.com/hiv/api/page.php";
    public static final String URL_ARTICLE = "http://tenderindicator.com/hiv/api/page.php";

    public static final String PAGE_ABC = "abc";

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
