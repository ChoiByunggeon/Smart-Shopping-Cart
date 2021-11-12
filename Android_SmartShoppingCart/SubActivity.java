package com.example.shoppingcart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SubActivity extends AppCompatActivity {
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Button button=findViewById(R.id.buttonPay);
        button.setOnClickListener(new View.OnClickListener() {//버튼 이벤트 처리
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Please scan RFID", Toast.LENGTH_SHORT).show();
            }
        });

        ListView listView = findViewById(R.id.listView);

        SingerAdapter adapter = new SingerAdapter();
        //adapter에 data값을 줘봅시다.
        adapter.addItem(new SingerItem("POCARI WEART","$8"));
        adapter.addItem(new SingerItem("TUNA","$26"));

        listView.setAdapter(adapter);
        /*List<String> list = new ArrayList<>();
        list.add("A");
        list.add("B");
        ArrayAdapter<String> adpater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list); listView.setAdapter(adpater);
        listView.setAdapter(adpater);*/
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    }

    class SingerAdapter extends BaseAdapter {
        //어뎁터가 데이터를 관리하며 데이터를 넣었다가 뺄 수도 있으므로 ArrayList를 활용하여 구현해보자.

        ArrayList<SingerItem> items = new ArrayList<SingerItem>();
        //걸그룹의 이름, 전화번호 등등이 필요할텐데 이거 하나로는 부족하니까
        //데이터형을 다양하게 담고있는 java파일을 하나 더 만들어줄거에요

        //!! 그런데 ArrayList에 데이터를 넣는 기능이 지금 없으므로 함수를 하나 더 만들어줄게요
        public void addItem(SingerItem item){
            items.add(item);
        }

        //너네 어뎁터 안에 몇 개의 아이템이 있니? 아이템갯수 반환함수
        @Override
        public int getCount() {
            return items.size(); //위의 ArrayList내부의 아이템이 몇 개나 들었는지 알려주게됨
        }

        @Override
        public Object getItem(int position) {
            return items.get(position); //position번째의 아이템을 얻을거야.
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        //이게 중요! 어뎁터가 데이터를 관리하기 때문에 화면에 보여질 각각의 화면에 보일 뷰도 만들어달라는 것
        //각각의 아이템 데이터 뷰(레이아웃)을 만들어주어 객체를 만든다음에 데이터를 넣고 리턴해줄 것임
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SingerItemView view = new SingerItemView(getApplicationContext());
            //어떤 뷰든 안드로이드에서는 Context객체를 받게 되어있으므로 getApplicationCotext로 넣어줍니다.

            //이제 이 뷰를 반환해주면 되는데 이 뷰가 몇 번째 뷰를 달라는 것인지 position값이 넘어오므로
            SingerItem item  = items.get(position); //SigerItem은 참고로 Dataset임. 따로 기본적인것만 구현해놓음
            //이 position값을 갖는 아이템의 SigerItem객체를 새로 만들어준 뒤
            view.setName(item.getName());
            view.setPrice(item.getPrice());
            //이렇게 해당 position에 맞는 값으로 설정해줍니다.

            //그렇게 설정을 잘 해놓은 다음에 view를 반환해야 데이터값이 들어간 레이아웃이 반환될거에요~
            return view;
        }
    }
}