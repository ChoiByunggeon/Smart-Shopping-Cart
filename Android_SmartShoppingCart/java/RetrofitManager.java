package com.example.shoppingcart;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static Retrofit retrofit;

    private RetrofitManager() {
        retrofit = new Retrofit.Builder().baseUrl("http://3.23.221.118:7579")
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
                                Request original = chain.request();
                                Log.d("된다", "통신된다!!");
                                // Request customization: add request headers
                                Request.Builder requestBuilder = original.newBuilder();
                                    requestBuilder = requestBuilder
                                            .header("X-M2M-RI", "12345")
                                            .header("X-M2M-Origin", "jws");

                                Request request = requestBuilder.build();
                                Log.d("된다", "통신된다!!!!!!");
                                return chain.proceed(request);
                            }
                        })

                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static Retrofit getInstance() {
        if(retrofit == null) {
            new RetrofitManager();
        }
        return retrofit;
    }


}
