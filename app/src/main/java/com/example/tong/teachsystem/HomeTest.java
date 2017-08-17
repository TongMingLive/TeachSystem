package com.example.tong.teachsystem;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.app.AlertDialog;

import com.example.tong.teachsystem.Bean.Test;
import com.example.tong.teachsystem.Util.App;
import com.example.tong.teachsystem.Util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tong on 2017/3/17.
 */

public class HomeTest extends AppCompatActivity {
    private ListView listView;
    private myAdapter adapter = new myAdapter();
    private List<Test> list = new ArrayList<>();
    private List<Integer> re = new ArrayList<>();
    private Button rs;
    private int res = 0;
    private TextView timeView;
    private String time;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_test);

        listView = (ListView) findViewById(R.id.test_lv);
        rs = (Button) findViewById(R.id.test_rs);
        timeView = (TextView) findViewById(R.id.test_time);

        new Thread(){
            @Override
            public void run() {
                super.run();
                Map<String,Object> map =new HashMap<String, Object>();
                String timeStr = HttpUtil.doPost(HttpUtil.path+"SelectTimeServlet",map);
                String testStr = HttpUtil.doPost(HttpUtil.path+"SelectTestServlet",map);
                if (testStr.equals("error") || timeStr.equals("error")) {
                    handler.sendEmptyMessage(0x000);
                }else {
                    time = timeStr;
                    Gson gson = new Gson();
                    JsonParser parser = new JsonParser();
                    JsonArray jsonArray = parser.parse(testStr).getAsJsonArray();
                    for (JsonElement json : jsonArray) {
                        list.add(gson.fromJson(json , Test.class));
                    }
                    handler.sendEmptyMessage(0x123);
                }
            }
        }.start();

        rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(HomeTest.this);
            }
        });
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    private void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示");
        builder.setMessage("你确定要交卷吗？");
        builder.setPositiveButton("确认交卷", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int n = 0;
                for (int i = 0;i < list.size();i++){
                    if (list.get(i).getTestAnswer() == re.get(i)){
                        n+=1;
                    }
                }
                res = 100 / list.size() * n;
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        Map<String,Object> map =new HashMap<String, Object>();
                        map.put("userNumber",App.user.getUserNumber());
                        map.put("resultNum",res);
                        String str = HttpUtil.doPost(HttpUtil.path+"UpdateResultServlet",map);
                        if ("error".equals(str)) {
                            handler.sendEmptyMessage(0x000);
                        }else if ("true".equals(str)){
                            handler.sendEmptyMessage(0x223);
                        }else {
                            handler.sendEmptyMessage(0x224);
                        }
                    }
                }.start();
            }
        });
        builder.setNeutralButton("返回检查",null);
        builder.show();
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
            LayoutInflater layoutInflater = LayoutInflater.from(HomeTest.this);
            View v = layoutInflater.inflate(R.layout.home_test_item,null);
            RadioGroup group = (RadioGroup) v.findViewById(R.id.test_group);
            TextView textView = (TextView) v.findViewById(R.id.test_title);
            RadioButton radio1 = (RadioButton) v.findViewById(R.id.test_radio1);
            RadioButton radio2 = (RadioButton) v.findViewById(R.id.test_radio2);
            RadioButton radio3 = (RadioButton) v.findViewById(R.id.test_radio3);
            RadioButton radio4 = (RadioButton) v.findViewById(R.id.test_radio4);
            textView.setText(list.get(position).getTestTitle());
            radio1.setText(list.get(position).getTestPage1());
            radio2.setText(list.get(position).getTestPage2());
            radio3.setText(list.get(position).getTestPage3());
            radio4.setText(list.get(position).getTestPage4());

            re.add(position,0);
            group.setTag(position);

            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                    int tag = Integer.parseInt(String.valueOf(group.getTag()));
                    switch (checkedId){
                        case R.id.test_radio1:
                            re.add(tag,1);
                            break;
                        case R.id.test_radio2:
                            re.add(tag,2);
                            break;
                        case R.id.test_radio3:
                            re.add(tag,3);
                            break;
                        case R.id.test_radio4:
                            re.add(tag,4);
                            break;
                    }
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
                timeView.setText("剩余时间："+time+"分钟");
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                setListViewHeightBasedOnChildren(listView);
            }else if (msg.what == 0x000){
                Snackbar.make(listView, "网络连接失败，请检查您的网络", Snackbar.LENGTH_LONG).show();
            }else if (msg.what == 0x223){
                App.user.setResultNum(res);
                Intent intent = new Intent();
                intent.setClass(HomeTest.this,HomeTestOk.class);
                intent.putExtra("res",res+"");
                startActivity(intent);
                finish();
            }else if (msg.what == 0x224){
                Snackbar.make(listView, "交卷失败，请重试", Snackbar.LENGTH_LONG).show();
            }
        }
    };
}
