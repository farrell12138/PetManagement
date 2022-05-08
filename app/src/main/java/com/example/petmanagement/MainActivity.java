package com.example.petmanagement;

import static com.example.petmanagement.LoginActivity.isLogin;
import static com.example.petmanagement.LoginActivity.userId;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView hint;
    private List<FosterInfo> fosterList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        getSupportActionBar().hide();//隐藏标题栏


        Button userInfo = findViewById(R.id.user_info);
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin) {
                    startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });

        RelativeLayout layout = findViewById(R.id.layout);
        if(isLogin){
            updateFosterInfo();
            addListView();
            if(hint != null){
                layout.removeView(hint);
            }
        }
        else{
            addHintView(layout);
        }
    }

    private void addHintView(RelativeLayout layout) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 200);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        if(hint == null){
            hint = new TextView(this);
            hint.setText(R.string.hint);
            hint.setTextSize(30);
            hint.setTypeface(Typeface.DEFAULT_BOLD);
            hint.setGravity(Gravity.CENTER);
        }
        layout.addView(hint, lp);
    }


    private void addListView() {
        PetAdapter adapter = new PetAdapter(MainActivity.this, R.layout.item, fosterList);

        ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private void updateFosterInfo() {
        new Thread() {
            @Override
            public void run() {
                FosterInfoDao fosterInfoDao = new FosterInfoDao();
                ArrayList<FosterInfo> list = fosterInfoDao.getAllFosterInfo(Integer.toString(userId));
                for (int i = 0; i < list.size(); i++) {
                    FosterInfo info = list.get(i);
                    fosterList.add(info);
                }
            }
        }.start();
        try { // 连接数据库并进行查询需要时间，此处等待200毫秒
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isLogin) {
            if (fosterList.size() == 0) {
                updateFosterInfo();
                ((RelativeLayout)findViewById(R.id.layout)).removeView(hint);
            }
        } else {
            fosterList.clear();
            addListView();
        }
    }

}