package com.example.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    //view Objects
    private Button buttonScan;
    private TextView textViewNumber;

    //qr code scanner object
    private IntentIntegrator qrScan;

    //Page Change
    private Button buttonMove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        API();

        buttonScan = (Button) findViewById(R.id.buttonScan);
        textViewNumber = (TextView) findViewById(R.id.textViewNumber);

        qrScan = new IntentIntegrator(this);

        buttonScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //scan option
                qrScan.setPrompt("Scanning...");
                qrScan.initiateScan();
            }
        });

        buttonMove = (Button) findViewById(R.id.buttonMove);
        buttonMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubActivity.class);
                startActivity(intent);
                //startJoin(new joinData(textViewNumber));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Scan complete", Toast.LENGTH_SHORT).show();
                try {
                    JSONObject obj = new JSONObject(result.getContents());
                    textViewNumber.setText(obj.getString("name"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    void API (){
        RetrofitManager.getInstance().create(MobiusService.class).info().enqueue(
                new Callback<MobiusResult>() {
                    @Override
                    public void onResponse(Call<MobiusResult> call, Response<MobiusResult> response) {
                        MobiusResult result = response.body();
                        Log.d("Success", "성공");
                        System.out.println(result.getItem().rn);
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