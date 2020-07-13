package com.codes.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class DetailRecord extends AppCompatActivity {

    EditText editTextContext, editTextDate, editTextIndex;
    String index,date,context,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_record);

        editTextDate = findViewById(R.id.TimeText);
        editTextContext = findViewById(R.id.editText);
        editTextIndex = findViewById(R.id.textViewIndex);
        ImageButton DeleteButton = findViewById(R.id.DeleteButton);
        Button button = findViewById(R.id.backButton);

        Intent intent = getIntent();
        index = intent.getExtras().getString("index");
        date = intent.getExtras().getString("date");
        context = intent.getExtras().getString("context");
        name = intent.getExtras().getString("name");

        editTextDate.setText(date);
        editTextContext.setText(context);
        editTextIndex.setText(name);

        // ADD 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // firebase 데이터 업데이트하기, REQUEST_CODE_DETAIL IN MainActivity
                // 업데이트 => RESULT_OK
                String updateContext = editTextContext.getText().toString();
                String updateDate = editTextDate.getText().toString();
                String updateName = editTextIndex.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("index", index);
                intent.putExtra("date", updateDate);
                intent.putExtra("context", updateContext);
                intent.putExtra("name", updateName);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        // DELET 버튼
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // firebase 데이터 삭제하기, REQUEST_CODE_DETAIL IN MainActivity
                // 삭제 => RESULT_FIRST_USER
                Intent intent = new Intent();
                intent.putExtra("index", index);
                setResult(RESULT_FIRST_USER, intent);
                finish();
            }
        });
    }
}
