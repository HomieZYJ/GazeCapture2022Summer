package com.test.cameraxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SumActivity4_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum1_1);

    }

    public void jump(View v){
        int mRightAnswer=67;
        int mAnswer;
        EditText mEditText=(EditText)findViewById(R.id.text_sum);
        mAnswer=Integer.parseInt(mEditText.getText().toString());
        if(mAnswer==mRightAnswer){
            Toast.makeText(SumActivity4_1.this,"答案正确", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(SumActivity4_1.this,"答案错误", Toast.LENGTH_LONG).show();
        }
        Intent intent=new Intent(SumActivity4_1.this,NoteActivity4_2.class);
        startActivity(intent);
    }
}