package com.codes.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD = 101;
    public static final int REQUEST_CODE_DETAIL = 102;
    private DatabaseReference mDatabase;
    ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    Splash splash;
    fbData fbdata = splash.fbdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.e("INNER", "777" + fbdata.data.size());

        ListView listView = (ListView) findViewById(R.id.listview);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
                new String[]{"title", "context"},
                new int[]{android.R.id.text1, android.R.id.text2});
        listView.setAdapter(simpleAdapter);

        //listView Click 이벤트 리스너 등록
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(MainActivity.this, list.get(position).get("title"), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), DetailRecord.class);
                intent.putExtra("index", list.get(position).get("index"));
                intent.putExtra("date", list.get(position).get("title"));
                intent.putExtra("context", list.get(position).get("context"));
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_DETAIL);
            }
        });

        for (int i = 1; i < fbdata.data.size(); i++) {
            if(fbdata.data.get(i) != null){
                //item 마지막 요소는 index 요소 추가
                HashMap<String, String> item;
                String title = fbdata.data.get(i).get("title");
                String context = fbdata.data.get(i).get("context");
                item = new HashMap<>();
                item.put("title", title);
                item.put("context", context);
                item.put("index", i+"");
                list.add(item);
            }
        }

        //버튼 클릭 시 추가하기로 넘어가기
        Button button = findViewById(R.id.moveAddRecord);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddRecord.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //firebase에 Data 추가
        if(requestCode == REQUEST_CODE_ADD){
//            Toast.makeText(getApplicationContext(),
//                    "onActivityResult 메서드 요청됨, 요청코드 : " + requestCode +
//                    " , 결과 코드 : " + resultCode, Toast.LENGTH_LONG).show();

            if(resultCode == RESULT_OK){
                String recive_date = data.getStringExtra("date");
                String recive_context = data.getStringExtra("context");
//                Toast.makeText(getApplicationContext(),
//                        "응답으로 전달된 title : " + recive_date +
//                                "내용 : " + recive_context, Toast.LENGTH_LONG).show();
                //firebase에 Data 추가
                Integer size = fbdata.data.size();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("data").child(size.toString());
                mDatabase.child("title").setValue(recive_date);
                mDatabase.child("context").setValue(recive_context);
                mDatabase.push();
            }
        }

        //firebase에 Data 업데이트, 삭제
        if(requestCode == REQUEST_CODE_DETAIL){
//            Toast.makeText(getApplicationContext(),
//                    "onActivityResult 메서드 요청됨, 요청코드 : " + requestCode +
//                            " , 결과 코드 : " + resultCode, Toast.LENGTH_LONG).show();
            if (resultCode == RESULT_OK){
                String recive_index = data.getStringExtra("index");
                //Log.e("essase",recive_index);
                String recive_date = data.getStringExtra("date");
                String recive_context = data.getStringExtra("context");

                mDatabase = FirebaseDatabase.getInstance().getReference().child("data").child(recive_index);
                mDatabase.child("title").setValue(recive_date);
                mDatabase.child("context").setValue(recive_context);
                mDatabase.push();
            }
            else if (resultCode == RESULT_FIRST_USER){
                String recive_index = data.getStringExtra("index");
                mDatabase = FirebaseDatabase.getInstance().getReference().child("data").child(recive_index);
                mDatabase.removeValue();
            }
        }
    }
}