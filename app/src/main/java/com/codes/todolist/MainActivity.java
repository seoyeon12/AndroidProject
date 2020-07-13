package com.codes.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

        ListView listView = (ListView) findViewById(R.id.listview);

        ListViewAdapter adapter = new ListViewAdapter();
        listView.setAdapter(adapter);
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, android.R.layout.simple_list_item_2,
//                new String[]{"title", "context"},
//                new int[]{android.R.id.text1, android.R.id.text2});
//        listView.setAdapter(simpleAdapter);

        // 파이어베이스에서 가져온 데이터를 listview에 넣기
        for (int i = 1; i < fbdata.data.size(); i++) {
            //item 마지막 요소는 index 요소 추가
            if(fbdata.data.get(i) != null){
                //item 마지막 요소는 index 요소 추가
                /*
                    HashMap<String, String> item;
                    String title = fbdata.data.get(i).get("title");
                    String context = fbdata.data.get(i).get("context");
                    String name = fbdata.data.get(i).get("name");
                    item = new HashMap<>();
                    item.put("title", title);
                    item.put("context", context);
                    item.put("index", i+"");
                    item.put("name", name);
                    list.add(item);
                */
                String title = fbdata.data.get(i).get("title");
                String context = fbdata.data.get(i).get("context");
                String name = fbdata.data.get(i).get("name");
                adapter.addItem(i,name,title,context);
            }else{
                System.out.println("Eles NULL!!!!");
            }
        }

        //listView Click 이벤트 리스너 등록
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0){
                    Toast.makeText(MainActivity.this, "관리자 공지입니다.", Toast.LENGTH_LONG).show();
                }else {
                    /*
                    Intent intent = new Intent(getApplicationContext(), DetailRecord.class);
                    intent.putExtra("index", list.get(position).get("index"));
                    intent.putExtra("date", list.get(position).get("title"));
                    intent.putExtra("context", list.get(position).get("context"));
                    intent.putExtra("name", list.get(position).get("name"));
                    //startActivity(intent);
                    startActivityForResult(intent, REQUEST_CODE_DETAIL);
                     */
                    ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                    Intent intent = new Intent(getApplicationContext(), DetailRecord.class);
                    intent.putExtra("index", item.getindex()+"");
                    intent.putExtra("date", item.getTime());
                    intent.putExtra("context", item.getContext());
                    intent.putExtra("name", item.getName());
                    startActivityForResult(intent, REQUEST_CODE_DETAIL);
                }
            }
        });

        //추가하기 버튼 클릭 시 넘어가기
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
                String recive_name = data.getStringExtra("name");
//                Toast.makeText(getApplicationContext(),
//                        "응답으로 전달된 title : " + recive_date +
//                                "내용 : " + recive_context, Toast.LENGTH_LONG).show();

                //firebase에 Data 추가
                Integer size = fbdata.data.size();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("data").child(size.toString());
                mDatabase.child("title").setValue(recive_date);
                mDatabase.child("context").setValue(recive_context);
                mDatabase.child("name").setValue(recive_name);
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
                String recive_date = data.getStringExtra("date");
                String recive_context = data.getStringExtra("context");
                String recive_name = data.getStringExtra("name");
                //Toast.makeText(MainActivity.this, "" + recive_index, Toast.LENGTH_LONG).show();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("data").child(recive_index);
                mDatabase.child("title").setValue(recive_date);
                mDatabase.child("context").setValue(recive_context);
                mDatabase.child("name").setValue(recive_name);
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