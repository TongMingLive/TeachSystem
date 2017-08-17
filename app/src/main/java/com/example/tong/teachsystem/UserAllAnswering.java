package com.example.tong.teachsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.tong.teachsystem.Bean.Answering;
import com.example.tong.teachsystem.Bean.Test;
import com.example.tong.teachsystem.Util.App;
import com.example.tong.teachsystem.Util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tong on 2017/3/19.
 */

public class UserAllAnswering extends AppCompatActivity {
    private ListView listView;
    private Button button;
    private ListAdapter adapter = new myAdapter();
    private List<Answering> list = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_all_answering);

        listView = (ListView) findViewById(R.id.answering_lv);
        button = (Button) findViewById(R.id.answering_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserAllAnswering.this,AnsweringTeach.class));
                finish();
            }
        });

        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String,Object> map =new HashMap<>();
                map.put("userNumber", App.user.getUserNumber());
                String str = HttpUtil.doPost(HttpUtil.path+"SelectUserAnsweringServlet",map);
                if (str.equals("error")) {
                    handler.sendEmptyMessage(0x000);
                }else {
                    Gson gson = new Gson();
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(str).getAsJsonArray();
                    for (JsonElement json : jsonArray) {
                        list.add(gson.fromJson(json , Answering.class));
                    }
                    handler.sendEmptyMessage(0x123);
                }
            }
        }.start();
    }

    private class myAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(UserAllAnswering.this);
            View v = layoutInflater.inflate(R.layout.user_all_answering_item, null);
            LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.user_answering_item);
            TextView title = (TextView) v.findViewById(R.id.answering_title);
            TextView page = (TextView) v.findViewById(R.id.answering_page);
            TextView res = (TextView) v.findViewById(R.id.answering_res);

            title.setText(list.get(position).getAnsweringTitle());
            page.setText(list.get(position).getAnsweringPage());
            if ("暂无老师解答".equals(list.get(position).getAnsweringQuestion())) {
                res.setText("未回复");
            }else {
                res.setText("已回复");
            }

            linearLayout.setTag(position);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = Integer.parseInt(String.valueOf(v.getTag()));
                    Intent intent = new Intent();
                    intent.setClass(UserAllAnswering.this,UserAnswering.class);
                    intent.putExtra("T","答疑详情");
                    intent.putExtra("title",list.get(tag).getAnsweringTitle());
                    intent.putExtra("page",list.get(tag).getAnsweringPage());
                    intent.putExtra("res",list.get(tag).getAnsweringQuestion());
                    startActivity(intent);
                }
            });
            return v;
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123){
                listView.setAdapter(adapter);
            }else if (msg.what == 0x000){
                Snackbar.make(listView, "网络连接失败，请检查您的网络", Snackbar.LENGTH_LONG).show();
            }
        }
    };
}
