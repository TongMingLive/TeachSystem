package com.example.tong.teachsystem.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tong.teachsystem.Bean.Studyclass;
import com.example.tong.teachsystem.ClassUpdate;
import com.example.tong.teachsystem.R;
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
 * Created by Tong on 2017/3/17.
 */

public class Fragment2 extends Fragment {
    private android.widget.TextView introducetitle;
    private android.widget.ListView introducelv;
    private android.support.design.widget.FloatingActionButton fab;
    private List<Studyclass> list = new ArrayList<>();
    private ListAdapter adapter = new myAdapter();
    private String str;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home,null);

        this.fab = (FloatingActionButton) view.findViewById(R.id.fab);
        this.introducelv = (ListView) view.findViewById(R.id.introduce_lv);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ClassUpdate.class));
            }
        });

        return view;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.class_item, null);
            final LinearLayout layout = (LinearLayout) v.findViewById(R.id.hd_item);
            TextView title = (TextView) v.findViewById(R.id.hd_title);
            TextView time = (TextView) v.findViewById(R.id.hd_time);

            title.setText(list.get(position).getClassName());
            time.setText(list.get(position).getClassTime());

            layout.setTag(position);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    Intent intent = new Intent();
                    intent.setClass(getContext(), ClassUpdate.class);
                    intent.putExtra("class",list.get(position));
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
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(str).getAsJsonArray();
                for (JsonElement json : jsonArray) {
                    list.add(gson.fromJson(json , Studyclass.class));
                }
                introducelv.setAdapter(adapter);
            }else if (msg.what == 0x000){
                Snackbar.make(introducelv, "网络连接失败，请检查您的网络", Snackbar.LENGTH_LONG).show();
            }
        }
    };

    //获取网络数据
    private void getintent(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String,Object> map =new HashMap<>();
                map.put("userId", App.user.getUserId());
                str = HttpUtil.doPost(HttpUtil.path+"SelectClassByUserServlet",map);
                if (str.equals("error")) {
                    handler.sendEmptyMessage(0x000);
                }else {
                    handler.sendEmptyMessage(0x123);
                }
            }
        }.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
        getintent();
    }
}
