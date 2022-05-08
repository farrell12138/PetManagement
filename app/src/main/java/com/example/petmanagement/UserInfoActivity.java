package com.example.petmanagement;

import static com.example.petmanagement.LoginActivity.isLogin;
import static com.example.petmanagement.LoginActivity.userId;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UserInfoActivity extends AppCompatActivity {

    private List<Pet> petList = new ArrayList<>();
    private int id;
    private String userName;
    private String phone;
    TextView userIdTx;
    TextView userNameTx;
    TextView phoneTx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        getSupportActionBar().hide();//隐藏标题栏

        userIdTx = findViewById(R.id.user_id_tx);
        userNameTx = findViewById(R.id.user_name_tx);
        phoneTx = findViewById(R.id.phone_tx);

        if (!isLogin || !TextUtils.equals(Integer.toString(userId), userIdTx.getText())) {
            SharedPreferences sharedPreferences= getSharedPreferences("data", Context .MODE_PRIVATE);
            id = sharedPreferences.getInt("userId", 0);
            userIdTx.setText(Integer.toString(id));
            userName = sharedPreferences.getString("userName", "");
            userNameTx.setText(userName);
            phone = sharedPreferences.getString("phone", "");
            phoneTx.setText(phone);
            isLogin = true;
        }

        findViewById(R.id.foster_info1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoActivity.this, MainActivity.class));
            }
        });

        findViewById(R.id.logout_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoActivity.this, LoginActivity.class));
                isLogin = false;
                finish();
            }
        });

        Button addPet = findViewById(R.id.add_pet);
        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserInfoActivity.this, AddPetActivity.class));
            }
        });

        updatePetList();
        addListView();
    }

    private void addListView() {
        PetAdapter1 adapter = new PetAdapter1(UserInfoActivity.this, R.layout.pet_item, petList);

        ListView listView = findViewById(R.id.pet_list);
        listView.setAdapter(adapter);
    }

    private void updatePetList() {
        new Thread() {
            @Override
            public void run() {
                FosterInfoDao fosterInfoDao = new FosterInfoDao();
                petList = fosterInfoDao.getAllPetInfo(Integer.toString(userId));
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
        updatePetList();
        addListView();
    }
}
