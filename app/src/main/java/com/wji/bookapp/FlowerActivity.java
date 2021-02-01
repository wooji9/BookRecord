package com.wji.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FlowerActivity extends AppCompatActivity {

    Button homeButton, recordButton, profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower);

        homeButton = findViewById(R.id.homeButton);
        recordButton = findViewById(R.id.recordButton);
        profileButton = findViewById(R.id.profileButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlowerActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlowerActivity.this, RecordMActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /*profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlowerActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });*/
    }
}