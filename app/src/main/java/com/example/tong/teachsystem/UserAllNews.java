package com.example.tong.teachsystem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tong.teachsystem.Bean.News;
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
 * Created by Tong on 2017/3/20.
 */

public class UserAllNews extends AppCompatActivity {
    private ListView listView;
    private List<News> list = new ArrayList<>();
    private ListAdapter adapter = new myAdapter();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_all_news);

        listView = (ListView) findViewById(R.id.news_lv);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String,Object> map =new HashMap<>();
                map.put("userNumber", App.user.getUserNumber());
                String str = HttpUtil.doPost(HttpUtil.path+"SelectNewsServlet",map);
                if (str.equals("error")) {
                    handler.sendEmptyMessage(0x000);
                }else {
                    Gson gson = new Gson();
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(str).getAsJsonArray();
                    for (JsonElement json : jsonArray) {
                        list.add(gson.fromJson(json , News.class));
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
            LayoutInflater layoutInflater = LayoutInflater.from(UserAllNews.this);
            View v = layoutInflater.inflate(R.layout.user_all_answering_item, null);
            LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.user_answering_item);
            TextView title = (TextView) v.findViewById(R.id.answering_title);
            TextView page = (TextView) v.findViewById(R.id.answering_page);

            title.setText(list.get(position).getNewsTitle());
            page.setText(list.get(position).getNewsPage());

            linearLayout.setTag(position);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = Integer.parseInt(String.valueOf(v.getTag()));
                    Intent intent = new Intent();
                    intent.setClass(UserAllNews.this,UserAnswering.class);
                    intent.putExtra("T","资讯详情");
                    intent.putExtra("title",list.get(tag).getNewsTitle());
                    intent.putExtra("page",list.get(tag).getNewsPage());
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
