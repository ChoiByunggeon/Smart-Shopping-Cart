package com.example.shoppingcart;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface MobiusService {
    String path = "/Mobius/userapp/item/la";
    @GET(path)
    Call<MobiusResult> info();

    @FormUrlEncoded
    @POST("/Mobius/CART_A/status")
    Call<Void> createPost(
            @Field("cart") String cart
            /*@Field("userId") String userId,
            @Field("content") String content,
            @Field("imageUrl") String imageUrl,
            @Field("minAge") String minAge,
            @Field("userCount") String userCount,
            @Field("ssomType") String ssomType,
            @Field("latitude") double latitude,
            @Field("longitude") double longitude*/
    );
}