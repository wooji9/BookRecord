package com.wji.bookapp;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import db.MemoDatabaseManager;

public class MemoActivity extends AppCompatActivity {

    EditText et_title, et_content;
    Button btn_save;

    MemoDatabaseManager memoDB; // memo 데이터베이스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        btn_save = findViewById(R.id.btn_save);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras(); // bundle 꺼내기
        String idx = bundle.getString("n"); // idx : 클릭 한 책

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memoDB = MemoDatabaseManager.getInstance(MemoActivity.this);

                String title = et_title.getText().toString();
                String content = et_content.getText().toString();

                // insert
                ContentValues addRowValue = new ContentValues();

                addRowValue.put("idx", idx); // 어떤 책에 메모를 했는지 구별하기 위한 idx 추가
                addRowValue.put("m_title", title); // 메모 제목
                addRowValue.put("m_content",content); // 메모 내용
                addRowValue.put("m_register", getNow()); // 메모 등록 날짜

                long id = memoDB.insert(addRowValue);


               if(id > 0){ // insert가 되었으면 Toast 띄우기
                   Toast.makeText(MemoActivity.this,"저장되었습니다",Toast.LENGTH_SHORT).show();
                   finish();
               }
            }
        });

    }

    // 오늘 날짜를 가져오기 위한 메서드
    private String getNow(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
}