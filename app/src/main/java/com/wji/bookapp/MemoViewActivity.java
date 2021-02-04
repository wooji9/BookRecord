package com.wji.bookapp;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import db.MemoDatabaseManager;
import vo.MemoVO;

public class MemoViewActivity extends AppCompatActivity {

    TextView txt_title, txt_content;
    EditText et_title, et_content;
    Button btn_del, btn_save;

    MemoDatabaseManager memoDB;
    String m_idx;
    MemoVO vo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_view);

        txt_title = findViewById(R.id.txt_title);
        txt_content = findViewById(R.id.txt_content);
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        btn_del = findViewById(R.id.btn_del);
        btn_save = findViewById(R.id.btn_save);

        memoDB = MemoDatabaseManager.getInstance(MemoViewActivity.this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras(); // bundle 꺼내기
        m_idx = bundle.getString("m_idx"); // 클릭 한 메모의 idx

        // select
        getMemoData();


        txt_title.setText(vo.getM_title());
        txt_content.setText(vo.getM_content());
        et_title.setText(vo.getM_title());
        et_content.setText(vo.getM_content());


        btn_save.setOnClickListener(click);
        btn_del.setOnClickListener(click);


    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          switch (view.getId()){
              case R.id.btn_save: // update(수정)
                  String title = et_title.getText().toString();
                  String content = et_content.getText().toString();

                  // == update memo set m_title = title, m_content = content where m_idx = 수정하고 싶은 메모 idx(클릭 한 메모의 idx)
                  ContentValues updateRowValue = new ContentValues();
                  updateRowValue.put("m_title", title);
                  updateRowValue.put("m_content",content);

                  String whereClause = "m_idx = ?";
                  String[] whereArgs = {m_idx};

                  int n = memoDB.update(updateRowValue, whereClause, whereArgs);
                  if(n>0){ // update가 완료 되었으면 Toast 띄우기
                      Toast.makeText(MemoViewActivity.this,"수정되었습니다",Toast.LENGTH_SHORT).show();
                      finish();
                      // 수정 후 내용 업데이트 되도록 MemoListActitvity로 전환

                  }
                  break;

              case R.id.btn_del:
                  AlertDialog.Builder dial = new AlertDialog.Builder(MemoViewActivity.this);
                  dial.setTitle("삭제 확인");
                  dial.setMessage("정말 삭제하시겠습니까?");
                  dial.setNegativeButton("아니요",null);
                  dial.setPositiveButton("네", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialogInterface, int i) {
                          // delete from memo where m_idx = 삭제하고 싶은 메모의 idx
                          String whereClause = "m_idx = ?"; // where절
                          String[] whereArgs = {m_idx}; // where절의 ?
                          int n = memoDB.delete(whereClause,whereArgs);

                          if(n>0){
                              Toast.makeText(MemoViewActivity.this,"삭제되었습니다",Toast.LENGTH_SHORT).show();
                              finish();
                              // 삭제 후 내용 업데이트 되도록 MemoListActitvity로 전환
                          }
                      }
                  });

                  dial.show();
                  break;
          }
        }
    };

    // select 메서드
    public void getMemoData()
    {

        String[] columns = new String[] {"m_title","m_content"}; // == select m_title, m_content from memo where m_idx = 메모 내용을 보고싶은 메모의 idx


        String selection = "m_idx = ?"; // where절
        String [] selectionArgs = {m_idx}; // where절 ?의 인자

        Cursor cursor = memoDB.query(columns, selection, selectionArgs,null,null,null);

        if(cursor != null)
        {
            while (cursor.moveToNext())
            {
                vo = new MemoVO();

                vo.setM_title(cursor.getString(0)); // title 넣기
                vo.setM_content(cursor.getString(1)); // content 넣기
            }
        }


        cursor.close();

    }

    public void attemptEdit(View view) { // 화면 터치 시 TextView -> EditText로 변함(메모를 수정 할 수 있도록)

        txt_title.setVisibility(View.INVISIBLE);
        txt_content.setVisibility(View.INVISIBLE);
        et_title.setVisibility(View.VISIBLE);
        et_content.setVisibility(View.VISIBLE);
        btn_del.setVisibility(View.GONE);
        btn_save.setVisibility(View.VISIBLE);

    }
}