package com.example.tong.teachsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Tong on 2017/3/19.
 */

public class UserWork extends AppCompatActivity {
    private TextView page,res;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_work);

        page = (TextView) findViewById(R.id.user_page);
        res = (TextView) findViewById(R.id.work_res);

        String title = getIntent().getStringExtra("title");
        String strRes = getIntent().getStringExtra("res");

        page.setText(title);

        if ("-1".equals(strRes)){
            res.setText("评分："+"未评分");
        }else {
            res.setText("评分："+strRes+"分");
        }
    }
}
