package com.test.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoteActivity2_1 extends AppCompatActivity {
    Button button = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note2_1);
        button = (Button)findViewById(R.id.bt_android);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity2_1.this,ViewPointActivity2_1.class);
                startActivity(intent);
            }
        });

    }
}