package com.wji.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecordMActivity extends AppCompatActivity {

    Button homeButton, flowerButton, profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_m);

        homeButton = findViewById(R.id.homeButton);
        flowerButton = findViewById(R.id.flowerButton);
        profileButton = findViewById(R.id.profileButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecordMActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        flowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecordMActivity.this, FlowerActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecordMActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });*/
    }
}