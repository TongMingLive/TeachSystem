package com.example.tong.teachsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Tong on 2017/3/17.
 */

public class HomeJava extends AppCompatActivity implements View.OnClickListener {
    private Button work,test;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_java);
        work = (Button) findViewById(R.id.home_work);
        test = (Button) findViewById(R.id.home_test);

        work.setOnClickListener(this);
        test.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_work:
                startActivity(new Intent(HomeJava.this, HomeWork.class));
                break;
            case R.id.home_test:
                startActivity(new Intent(HomeJava.this, HomeTest.class));
                break;
        }
    }
}
