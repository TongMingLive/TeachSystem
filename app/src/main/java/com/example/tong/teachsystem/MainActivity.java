package com.example.tong.teachsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.tong.teachsystem.Fragment.Fragment1;
import com.example.tong.teachsystem.Fragment.Fragment2;
import com.example.tong.teachsystem.Fragment.Fragment3;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private long exitTime = 0;
    private BottomNavigationView navigation;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private ViewPager viewPager;
    private List<Fragment> FgList = new ArrayList<>();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    viewPager.setCurrentItem(1,false);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    viewPager.setCurrentItem(2,false);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    viewPager.setCurrentItem(0,false);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewPager = (ViewPager) findViewById(R.id.content_vp);

        //获取fragment管理者对象
        fragmentManager = getSupportFragmentManager();
        //将碎片添加到fglist内
        Fragment1 f1 = new Fragment1();
        Fragment2 f2 = new Fragment2();
        Fragment3 f3 = new Fragment3();
        FgList.add(f1);FgList.add(f2);FgList.add(f3);

        //禁止viewPager滑动
        viewPager.setOnTouchListener( new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;  //修改为true
            }

        });

        viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return FgList.get(position);
            }

            @Override
            public int getCount() {
                //返回item个数
                return FgList.size();
            }
        });

    }

    //再次返回键退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Snackbar.make(navigation,"再按一次退出程序",Snackbar.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
