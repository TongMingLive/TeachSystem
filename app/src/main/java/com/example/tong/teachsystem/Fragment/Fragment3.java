package com.example.tong.teachsystem.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tong.teachsystem.Login;
import com.example.tong.teachsystem.R;
import com.example.tong.teachsystem.UpdatePassword;
import com.example.tong.teachsystem.UserAllAnswering;
import com.example.tong.teachsystem.UserAllNews;
import com.example.tong.teachsystem.UserAnswering;
import com.example.tong.teachsystem.UserTest;
import com.example.tong.teachsystem.UserAllWork;
import com.example.tong.teachsystem.Util.App;

/**
 * Created by Tong on 2017/3/17.
 */

public class Fragment3 extends Fragment implements View.OnClickListener {
    private TextView uid, number, password, work, test, answering, out,news;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.more, null);
        uid = (TextView) view.findViewById(R.id.user_uid);
        number = (TextView) view.findViewById(R.id.user_userNumber);
        password = (TextView) view.findViewById(R.id.user_updatePassword);
        work = (TextView) view.findViewById(R.id.user_work);
        test = (TextView) view.findViewById(R.id.user_test);
        answering = (TextView) view.findViewById(R.id.user_answering);
        out = (TextView) view.findViewById(R.id.user_out);
        news = (TextView) view.findViewById(R.id.user_news);

        uid.setText("姓名："+App.user.getUserName());
        number.setText("学号："+App.user.getUserNumber());

        password.setOnClickListener(this);
        work.setOnClickListener(this);
        test.setOnClickListener(this);
        answering.setOnClickListener(this);
        out.setOnClickListener(this);
        news.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_updatePassword:
                startActivity(new Intent(getActivity(), UpdatePassword.class));
                break;
            case R.id.user_work:
                startActivity(new Intent(getActivity(), UserAllWork.class));
                break;
            case R.id.user_test:
                startActivity(new Intent(getActivity(), UserTest.class));
                break;
            case R.id.user_answering:
                startActivity(new Intent(getActivity(), UserAllAnswering.class));
                break;
            case R.id.user_out:
                startActivity(new Intent(getActivity(), Login.class));
                App.user = null;
                getActivity().finish();
                break;
            case R.id.user_news:
                startActivity(new Intent(getActivity(), UserAllNews.class));
                break;
        }
    }
}
