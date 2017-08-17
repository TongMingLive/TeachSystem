package com.example.tong.teachsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Tong on 2017/3/18.
 */

public class HomeTestOk extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_test_ok);

        String res = getIntent().getStringExtra("res");

        TextView textView = (TextView) findViewById(R.id.test_ok_tv);
        Button button = (Button) findViewById(R.id.test_ok_btn);

        textView.setText("你考了："+res+"分");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
