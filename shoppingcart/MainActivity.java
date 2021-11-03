package com.example.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        API();
    }

    void API (){
        RetrofitManager.getInstance().create(MobiusService.class).info().enqueue(
                new Callback<MobiusResult>() {
                    @Override
                    public void onResponse(Call<MobiusResult> call, Response<MobiusResult> response) {

                        MobiusResult result = response.body();
                        Log.d("Success", "성공");
                        System.out.println(result);
                    }

                    @Override
                    public void onFailure(Call<MobiusResult> call, Throwable t) {
                        Log.d("Failure", "Failure!!!!");
                        Log.d("error", t.getMessage());
                    }
                }
        );
    }
}