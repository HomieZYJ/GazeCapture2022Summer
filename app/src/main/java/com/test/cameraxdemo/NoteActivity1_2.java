package com.test.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoteActivity1_2 extends AppCompatActivity {

    Button button = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note1_2);
        button = (Button) findViewById(R.id.bt_android);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity1_2.this, ViewPointActivity1_2.class);
                startActivity(intent);
            }
        });
    }
}