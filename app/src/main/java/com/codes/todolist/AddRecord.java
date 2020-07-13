package com.codes.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddRecord extends AppCompatActivity {

    final Context context = this;
    String selectDate, selectcontext, selectName;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        //캘린더 데이터
        calendarView = (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectDate = ""+year+"/"+(month+1)+"/"+dayOfMonth;
                Toast.makeText(AddRecord.this, selectDate ,Toast.LENGTH_LONG).show();
            }
        });

        //Add 버튼 클릭 시
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Context 데이터
                EditText editText = (EditText)findViewById(R.id.editText);
                EditText editText2 = (EditText)findViewById(R.id.editTextName);
                selectcontext = editText.getText().toString();
                selectName = editText2.getText().toString();
                //날짜 데이터 체킹
                checkingDate();

                if(editText.getText().toString().isEmpty()){
                    //Log.e("esse", "////Init " + selectcontext);
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("경고");
                    builder.setMessage("내용을 입력해주세요!!");
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("date", selectDate);
                    intent.putExtra("context", selectcontext);
                    intent.putExtra("name", selectName);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    // 검사코드 함수로 만들기
    public void checkingDate() {
        if(selectDate == null){
            Date date = new Date(calendarView.getDate());
            SimpleDateFormat mFormat = new SimpleDateFormat("yyyy/MM/d");
            selectDate =  mFormat.format(date);
        }
    }
}
