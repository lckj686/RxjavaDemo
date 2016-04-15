
package com.rxjava.sucer.master.retrofit;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;



public interface RequestApi {


    @GET("/api/oauth2/validation?access_token=ACCESS_TOKEN")
    Observable<Object> requestDemo2();


    @GET("/api/oauth2/validation?access_token=ACCESS_TOKEN")
    Call<HashMap<String, Object>> requestDemo();
}
