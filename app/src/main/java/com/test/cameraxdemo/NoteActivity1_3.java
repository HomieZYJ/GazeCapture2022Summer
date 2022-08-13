package com.test.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoteActivity1_3 extends AppCompatActivity {

    Button button = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note1_3);
        button = (Button) findViewById(R.id.bt_android);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoteActivity1_3.this, ViewPointActivity1_3.class);
                startActivity(intent);
            }
        });
    }
}