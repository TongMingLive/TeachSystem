package com.example.tong.teachsystem;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tong.teachsystem.Bean.Studyclass;
import com.example.tong.teachsystem.Util.App;
import com.example.tong.teachsystem.Util.HttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tong- on 2017/5/4.
 */

public class ClassUpdate extends AppCompatActivity {
    private TextView title;
    private EditText name;
    private EditText time;
    private Button button;
    private Studyclass studyclass;
    private String nameStr,timeStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.class_update);

        title = (TextView) findViewById(R.id.index_tex_title);
        name = (EditText) findViewById(R.id.hd_main_name);
        time = (EditText) findViewById(R.id.hd_main_time);
        button = (Button) findViewById(R.id.hd_main_btn);

        studyclass = (Studyclass) getIntent().getSerializableExtra("class");

        if (studyclass!=null){
            title.setText("课程修改");
            name.setHint(studyclass.getClassName());
            time.setHint(studyclass.getClassTime());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nameStr = name.getText().toString();
                    timeStr = time.getText().toString();
                    if (TextUtils.isEmpty(nameStr)) {
                        Toast.makeText(ClassUpdate.this, "请输入课程名称", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(timeStr)) {
                        Toast.makeText(ClassUpdate.this, "请输入上课时间", Toast.LENGTH_SHORT).show();
                    }else {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("classId", studyclass.getClassId());
                                map.put("className",nameStr);
                                map.put("classTime",timeStr);
                                String str = HttpUtil.doPost(HttpUtil.path + "UpdateClassByIdServlet", map);
                                Log.d("Login", str);
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
                }
            });
        }else {
            title.setText("添加课程");
            name.setHint("请输入课程名称");
            time.setHint("请输入上课时间");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nameStr = name.getText().toString();
                    timeStr = time.getText().toString();
                    if (TextUtils.isEmpty(nameStr)) {
                        Toast.makeText(ClassUpdate.this, "请输入课程名称", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(timeStr)) {
                        Toast.makeText(ClassUpdate.this, "请输入上课时间", Toast.LENGTH_SHORT).show();
                    }else {
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("userId", App.user.getUserId());
                                map.put("className",nameStr);
                                map.put("classTime",timeStr);
                                String str = HttpUtil.doPost(HttpUtil.path + "AddClassByUserServlet", map);
                                Log.d("Login", str);
                                if ("error".equals(str)) {
                                    Log.d("login", "isNot");
                                    handler.sendEmptyMessage(0x000);
                                } else if ("true".equals(str)){
                                    handler.sendEmptyMessage(0x223);
                                }else {
                                    handler.sendEmptyMessage(0x224);
                                }
                            }
                        }.start();
                    }
                }
            });
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                finish();
                Toast.makeText(ClassUpdate.this, "修改成功", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 0x124) {
                Snackbar.make(button, "修改失败，请重试", Snackbar.LENGTH_LONG).show();
            } else if (msg.what == 0x223) {
                finish();
                Toast.makeText(ClassUpdate.this, "添加成功", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 0x224){
                Snackbar.make(button, "添加失败，请重试", Snackbar.LENGTH_LONG).show();
            }else if (msg.what == 0x000) {
                Snackbar.make(button, "网络连接失败，请检查您的网络", Snackbar.LENGTH_LONG).show();
            }
        }
    };
}
