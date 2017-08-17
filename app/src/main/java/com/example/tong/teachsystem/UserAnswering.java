package com.example.tong.teachsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Tong on 2017/3/19.
 */

public class UserAnswering extends AppCompatActivity{
    private TextView title,page,res,T;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_answering);

        T = (TextView) findViewById(R.id.T);
        title = (TextView) findViewById(R.id.answering_title);
        page = (TextView) findViewById(R.id.answering_page);
        res = (TextView) findViewById(R.id.answering_res);

        String strT = getIntent().getStringExtra("T");
        String strTitle = getIntent().getStringExtra("title");
        String strPage = getIntent().getStringExtra("page");
        String strRes = getIntent().getStringExtra("res");

        T.setText(strT);
        title.setText(strTitle);
        page.setText(strPage);
        res.setText(strRes);
    }
}
