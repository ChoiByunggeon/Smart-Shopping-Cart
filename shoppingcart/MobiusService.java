package com.example.shoppingcart;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MobiusService {
    @GET("/Mobius/jws/la/")
    Call<MobiusResult> info();
}
