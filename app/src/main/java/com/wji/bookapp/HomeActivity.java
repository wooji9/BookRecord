package com.wji.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import db.BookDatabaseManager;
import parse.BookListAdapter;
import parse.Parser;
import parse.ViewModelAdapter;
import vo.BookItem;
import vo.BookVO;

public class HomeActivity extends AppCompatActivity {

    Button flowerButton, recordButton, profileButton;
    public static EditText et_search; // 검색어

    Button btn_insert; // 검색 버튼
    ListView bookSaveList; // book listView
    ArrayList<BookItem> list; // 책들이 있는 vo
    BookListAdapter adapter; // book listView의 adapter

    BookDatabaseManager databaseManager; // bookList 데이터베이스

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        et_search = findViewById(R.id.et_search);
        btn_insert = findViewById(R.id.btn_insert);
        bookSaveList = findViewById(R.id.bookSaveList);

        flowerButton = findViewById(R.id.flowerButton);
        recordButton = findViewById(R.id.recordButton);
        profileButton = findViewById(R.id.profileButton);

        databaseManager = BookDatabaseManager.getInstance(HomeActivity.this);

        //로그인 한 아이디마다 초기화
        list = new ArrayList<>();
        //로그인 한 아이디로 select
        getBookData();

        //listView룰 adapter에다 연결
        adapter = new BookListAdapter(this, R.layout.book_save_list, list, bookSaveList);
        bookSaveList.setAdapter(adapter);

        flowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FlowerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RecordMActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

        // 책 추가 버튼 클릭 시시
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, BookSearchActivity.class);
                startActivity(intent);

            }
        });


    }

    // select
    public void getBookData()
    {

        String[] columns = new String[] {"idx", "title", "book_img"}; // == select idx, title, book_img from where bookList id = 'aaa'

        // 아이디마다
        String selection = "id='aaa'"; // where절

        Cursor cursor = databaseManager.query(columns, selection, null,null,null,null);

        if(cursor != null)
        {
            while (cursor.moveToNext()) // 검색 다 되었으면 하나씩 BookItem vo로 옮기기
            {
                BookItem currentData = new BookItem();

                currentData.setIdx(cursor.getInt(0)); // idx
                currentData.setTitle(cursor.getString(1)); // title
                currentData.setBook_img(cursor.getString(2)); // book_img

                list.add(currentData); // list에다 검색한 것 하나씩 추가하기
            }
        }


        cursor.close(); // cursor 닫기

    }

}