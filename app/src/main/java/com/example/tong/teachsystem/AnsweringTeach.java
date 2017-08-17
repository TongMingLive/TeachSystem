package com.example.tong.teachsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tong.teachsystem.Util.App;
import com.example.tong.teachsystem.Util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tong on 2017/3/19.
 */

public class AnsweringTeach extends AppCompatActivity {
    private EditText title,page;
    private Button btn;
    private String strTitle,strPage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answering_teach);
        title = (EditText) findViewById(R.id.answering_title);
        page = (EditText) findViewById(R.id.answering_page);
        btn = (Button) findViewById(R.id.answering_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strTitle = title.getText().toString();
                strPage = page.getText().toString();

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Map<String, Object> map = new HashMap<>();
                        map.put("userNumber", App.user.getUserNumber());
                        map.put("answeringTitle", strTitle);
                        map.put("answeringPage",strPage);
                        String str = HttpUtil.doPost(HttpUtil.path + "PushAnsweringServlet", map);
                        if ("error".equals(str)) {
                            handler.sendEmptyMessage(0x000);
                        } else if ("true".equals(str)) {
                            handler.sendEmptyMessage(0x123);
                        } else {
                            handler.sendEmptyMessage(0x124);
                        }
                    }
                }.start();
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                startActivity(new Intent(AnsweringTeach.this,UserAllAnswering.class));
                Toast.makeText(AnsweringTeach.this, "发布成功", Toast.LENGTH_SHORT).show();
                finish();
            } else if (msg.what == 0x124) {
                Snackbar.make(btn, "发布失败，请重试", Snackbar.LENGTH_LONG).show();
            } else if (msg.what == 0x000) {
                Snackbar.make(btn, "网络连接失败，请检查您的网络", Snackbar.LENGTH_LONG).show();
            }
        }
    };
}
