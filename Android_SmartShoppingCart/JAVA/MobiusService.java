package com.example.shoppingcart;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface MobiusService {
    @GET("/Mobius/CART_A/weight_sum/la")
    Call<MobiusResult> info();

    @FormUrlEncoded
    @POST("/Mobius/userapp/QR")
    Call<MobiusResult> createPost(
            @Field("con") String con
    );
}