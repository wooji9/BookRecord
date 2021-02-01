package com.wji.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import parse.Parser;
import parse.ViewModelAdapter;
import vo.BookVO;

public class HomeActivity extends AppCompatActivity {

    Button flowerButton, recordButton, profileButton;
    public static EditText et_search; // 검색어

    Button btn_search; // 검색 버튼
    ListView bookSearchList; // 검색 한 책들이 나오는 ListView
    Parser parser; // XML 파싱
    ArrayList<BookVO> list; // 책 1권의 정보가 들어있는 vo들을 모아 둔 list

    ViewModelAdapter adapter; // ListView 각 항목을 검색 결과를 나타내기 위한 어댑터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        et_search = findViewById(R.id.et_search);
        btn_search = findViewById(R.id.btn_search);
        bookSearchList = findViewById(R.id.bookSearchList);
        parser = new Parser();

        flowerButton = findViewById(R.id.flowerButton);
        recordButton = findViewById(R.id.recordButton);
        profileButton = findViewById(R.id.profileButton);

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

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list = new ArrayList<>(); // 검색 버튼을 클릭할 때마다 초기화가 필요
                adapter = null; // 검색 버튼을 클릭할 때마다 초기화가 필요

                //Async 클래스를 통한 서버 통신
                new NaverAsync().execute(); // doInBackground메서드를 호출
                //new NaverAsync().execute("홍","길","동");

                //parser.connectNaver(list); // 서버 통신을 Background에서 하도록 규약을 지정했기 때문에 onCreate()에서는 서버 통신이 불가
            }
        });


        //가상 키보드에서 돋보기 버튼(actionSearch)을 클릭했는지 감지
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                // i : 클릭한 버튼 감지

                if(i == EditorInfo.IME_ACTION_SEARCH){ // 가상 키보드에 있는 돋보기 버튼을 클릭했다면
                    //돋보기 버튼 클릭 시 btn_search를 강제로 클릭
                    btn_search.performClick(); // 강제로 검색 버튼을 클릭하게 만드는 메서드

                }
                return true;
            }
        });
    }

    class NaverAsync extends AsyncTask<Void, Void, ArrayList<BookVO>> {


        @Override
        protected ArrayList<BookVO> doInBackground(Void... voids) {

            return parser.connectNaver(list); // 통신 시작
        }

        // 통신이 완료되었을 때 호출되는 메서드
        @Override
        protected void onPostExecute(ArrayList<BookVO> bookVOS) {


            // 통신이 끝나면 adapter로 검색 결과 정보를 보내줘야 함
            adapter = new ViewModelAdapter(HomeActivity.this,
                    R.layout.book_search, // ListView의 항목을 디자인한 xml파일
                    bookVOS,
                    bookSearchList);

            //생성된 adapter를 ListView에 세팅
            // setAdapter() 메서드가 호출되면 해당 ViewModelAdapter의 getView() 메서드가 bookVOS의 size()만큼 자동으로 반복된다
            bookSearchList.setAdapter(adapter);

        }
    }

}