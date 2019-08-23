package com.example.darpa.handycandidateapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.example.darpa.handycandidateapp.Cleaning.CleaningQuestionsActivity;
import com.example.darpa.handycandidateapp.Handyman.HandymanQuestionActivity;
import com.example.darpa.handycandidateapp.Helper.DatabaseHelper;

public class ProfilePageActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    CardView cleaning, handyman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        databaseHelper = new DatabaseHelper(ProfilePageActivity.this);
        cleaning = (CardView) findViewById(R.id.cleaning);
        handyman = (CardView) findViewById(R.id.handymancard);

        cleaning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePageActivity.this, CleaningQuestionsActivity.class);
                startActivity(intent);
            }
        });

        handyman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePageActivity.this, HandymanQuestionActivity.class);
                startActivity(intent);
            }
        });


    }
}
