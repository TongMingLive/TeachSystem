package com.example.tong.teachsystem.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tong.teachsystem.Bean.User;
import com.example.tong.teachsystem.HomeTest;
import com.example.tong.teachsystem.HomeWork;
import com.example.tong.teachsystem.R;
import com.example.tong.teachsystem.Util.App;
import com.example.tong.teachsystem.Util.HttpUtil;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by Tong on 2017/3/16.
 */

public class Fragment1 extends Fragment implements View.OnClickListener {
    Button signIn,signOut;
    private Button homework;
    private Button hometest;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign,null);
        this.hometest = (Button) view.findViewById(R.id.home_test);
        this.homework = (Button) view.findViewById(R.id.home_work);
        signIn = (Button) view.findViewById(R.id.sign_in);
        signOut = (Button) view.findViewById(R.id.sign_out);

        signIn.setOnClickListener(this);
        signOut.setOnClickListener(this);
        homework.setOnClickListener(this);
        hometest.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_in:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("userNumber", App.user.getUserNumber());
                        map.put("userPassword", App.user.getUserPassword());
                        String loginStr = HttpUtil.doPost(HttpUtil.path + "UserLoginServlet", map);
                        if ("error".equals(loginStr)) {
                            Log.d("fragment1", "isNot");
                            handler.sendEmptyMessage(0x000);
                        }else{
                            Gson gson = new Gson();
                            App.user = gson.fromJson(loginStr, User.class);
                            Log.d("Fragment1", "App.user.getSignInType():" + App.user.getSignInType());
                            if (App.user.getSignInType()>0){
                                handler.sendEmptyMessage(0x125);
                            }else {
                                String signInStr = HttpUtil.doPost(HttpUtil.path + "SignInServlet", map);
                                if ("true".equals(signInStr)){
                                    handler.sendEmptyMessage(0x123);
                                } else {
                                    handler.sendEmptyMessage(0x124);
                                }
                            }
                        }
                    }
                }.start();
                break;
            case R.id.sign_out:
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("userNumber", App.user.getUserNumber());
                        map.put("userPassword", App.user.getUserPassword());
                        String loginStr = HttpUtil.doPost(HttpUtil.path + "UserLoginServlet", map);
                        if ("error".equals(loginStr)) {
                            Log.d("fragment1", "isNot");
                            handler.sendEmptyMessage(0x000);
                        }else{
                            Gson gson = new Gson();
                            App.user = gson.fromJson(loginStr, User.class);
                            Log.d("Fragment1", "App.user.getSignOutType():" + App.user.getSignOutType());
                            if (App.user.getSignOutType()>0){
                                handler.sendEmptyMessage(0x225);
                            }else {
                                String signInStr = HttpUtil.doPost(HttpUtil.path + "SignOutServlet", map);
                                if ("true".equals(signInStr)){
                                    handler.sendEmptyMessage(0x223);
                                } else {
                                    handler.sendEmptyMessage(0x224);
                                }
                            }
                        }
                    }
                }.start();
                break;
            case R.id.home_work:
                startActivity(new Intent(getActivity(), HomeWork.class));
                break;
            case R.id.home_test:
                startActivity(new Intent(getActivity(), HomeTest.class));
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x123) {
                Snackbar.make(signOut, "上课签到成功", Snackbar.LENGTH_LONG).show();
            } else if (msg.what == 0x124) {
                Snackbar.make(signOut, "上课签到失败，请重试", Snackbar.LENGTH_LONG).show();
            } else if (msg.what == 0x125) {
                Snackbar.make(signOut, "您已进行过上课签到，请勿重复签到", Snackbar.LENGTH_LONG).show();
            } else if (msg.what == 0x223) {
                Snackbar.make(signOut, "下课签到成功", Snackbar.LENGTH_LONG).show();
            } else if (msg.what == 0x224) {
                Snackbar.make(signOut, "下课签到失败，请重试", Snackbar.LENGTH_LONG).show();
            } else if (msg.what == 0x225) {
                Snackbar.make(signOut, "您已进行过下课签到，请勿重复签到", Snackbar.LENGTH_LONG).show();
            } else if (msg.what == 0x000) {
                Snackbar.make(signOut, "网络连接失败，请检查您的网络", Snackbar.LENGTH_LONG).show();
            }
        }
    };
}
