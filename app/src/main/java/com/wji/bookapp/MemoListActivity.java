package com.wji.bookapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import db.MemoDatabaseManager;
import parse.MemoListAdapter;
import vo.MemoVO;

public class MemoListActivity extends AppCompatActivity {

    Button btn_memo;
    ListView memoList;
    TextView txt_memo;
    ArrayList<MemoVO> list;
    MemoListAdapter adapter;
    String idx;

    MemoDatabaseManager memoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_list);



        btn_memo = findViewById(R.id.btn_memo);
        memoList = findViewById(R.id.memoList);
        txt_memo = findViewById(R.id.txt_memo);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras(); // bundle 꺼내기
        idx = bundle.getString("n"); // idx : 클릭 한 책

        memoDB = MemoDatabaseManager.getInstance(MemoListActivity.this);

        //책마다 초기화
        list = new ArrayList<>();

        // select
        getMemoData();


        adapter = new MemoListAdapter(MemoListActivity.this, R.layout.memo_save_list, list, memoList);

        // adapter가 비어있으면 기록이 안되어있다고 쓰여있는 TextView VISIBLE
        if(adapter.isEmpty()){
            txt_memo.setVisibility(View.VISIBLE);
        }
        // adapter가 비어있지 않으면 즉, 메모를 1개라도 작성했다면 memoList를 보여준다
        else{
            txt_memo.setVisibility(View.INVISIBLE);
            memoList.setAdapter(adapter);
        }



        btn_memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 메모 추가 버튼을 클릭했으면 MemoActivity로 전환
                Intent i = new Intent(MemoListActivity.this, MemoActivity.class);

                Bundle b = new Bundle();
                b.putString("n",idx); // 메모하고 싶은 책(클릭 한 책)의 idx를 가지고 액티비티 전환
                i.putExtras(b);

                startActivity(i);
            }
        });
    }

    // select
    public void getMemoData()
    {

        String[] columns = new String[] {"m_idx", "m_title"}; // == select m_idx, m_title from memo where idx = 클릭한 책의 idx

        // 클릭 한 책마다
        String selection = "idx = ?"; // where절
        String [] selectionArgs = {idx}; // where절 인자

        Cursor cursor = memoDB.query(columns, selection, selectionArgs,null,null,null);

        if(cursor != null)
        {
            while (cursor.moveToNext())
            {
                MemoVO currentData = new MemoVO();

                currentData.setM_idx(cursor.getInt(0)); // 메모 idx
                currentData.setM_title(cursor.getString(1)); // 메모 제목

                list.add(currentData); // list에 하나씩 담기
            }
        }


        cursor.close();

    }
}