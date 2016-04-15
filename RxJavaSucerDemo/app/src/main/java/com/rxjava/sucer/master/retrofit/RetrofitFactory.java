package com.rxjava.sucer.master.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Description:
 * Created by liw on 2016/4/15.
 */
public class RetrofitFactory {

    public static final String BASE_URL = "https://openapi.iqiyi.com";


    private static Retrofit retrofit = null;

    synchronized public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())//json
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//rxjav
                    .build();
        }
        return retrofit;

    }

    ;
}
