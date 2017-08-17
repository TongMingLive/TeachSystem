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
 * Created by Tong on 2017/3/17.
 */

public class HomeWork extends AppCompatActivity {
    private EditText editText;
    private Button button;
    private String page;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_work);

        editText = (EditText) findViewById(R.id.work_edit);
        button = (Button) findViewById(R.id.work_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = editText.getText().toString();
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("userNumber", App.user.getUserNumber());
                        map.put("workPage", page);
                        String str = HttpUtil.doPost(HttpUtil.path + "InsertWorkServlet", map);
                        if ("error".equals(str)) {
                            Log.d("login", "isNot");
                            handler.sendEmptyMessage(0x000);
                        } else if ("true".equals(str)){
                            handler.sendEmptyMessage(0x123);
                        }else {
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
                Toast.makeText(HomeWork.this, "作业上传成功", Toast.LENGTH_SHORT).show();
                finish();
            } else if (msg.what == 0x124) {
                Toast.makeText(HomeWork.this, "作业上传失败，请重试", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 0x000) {
                Snackbar.make(button, "网络连接失败，请检查您的网络", Snackbar.LENGTH_LONG).show();
            }
        }
    };
}
