package com.example.tong.teachsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tong.teachsystem.Util.App;

/**
 * Created by Tong on 2017/3/19.
 */

public class UserTest extends AppCompatActivity {
    private TextView textView;
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_test);
        textView = (TextView) findViewById(R.id.res);
        button = (Button) findViewById(R.id.back);

        if (App.user.getResultNum() == -1){
            textView.setText("java基础：未考");
        }else{
            textView.setText("java基础："+ App.user.getResultNum()+"分");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
