/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.rxjava.sucer.master.retrofit;

import android.util.Log;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;
import rx.schedulers.Schedulers;


public class DemoRetrofit {
    private String TAG = DemoRetrofit.class.getSimpleName();



    public void request() {
        RequestApi apiService = RetrofitFactory.getInstance().create(RequestApi.class);

        Call<HashMap<String, Object>> call = apiService.requestDemo();
        call.enqueue(new Callback<HashMap<String, Object>>() {

            @Override
            public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {

                Log.d(TAG, call.toString() + "  " + response.body() + "  class= " + response.body().getClass());
            }

            @Override
            public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {
                Log.d(TAG, call + "  " + t.toString());
            }


        });

        apiService.requestDemo2()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Object>() {


            @Override
            public void onCompleted() {
                Log.d(TAG,   "onCompleted  ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,   "onError  " + e.toString());
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG,   "onNext  " + o.toString());
            }
        });
    }

}
