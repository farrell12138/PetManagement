package com.example.petmanagement;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    static public boolean isLogin = false;
    public static final int EMPTY_ERROR = 0;
    public static final int LOGIN_ERROR = 1;
    public static final int LOGIN_SUCCESS = 2;
    public static int userId = 0;

    @SuppressLint("HandlerLeak")
    final Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(getApplicationContext(), "用户名、密码不能为空", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "登录失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 2) {
                Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftInput(view.getWindowToken());
            }
        });
        getSupportActionBar().hide();//隐藏标题栏

        EditText account = findViewById(R.id.account_edit);
        EditText password = findViewById(R.id.password_edit);

        findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        String userName = account.getText().toString();
                        String usePassword = password.getText().toString();
                        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(usePassword)) {
                            hand.sendEmptyMessage(EMPTY_ERROR);
                            return;
                        }
                        UserDao userDao = new UserDao();
                        User user = userDao.login(userName, usePassword);
                        if (user != null) {
                            hand.sendEmptyMessage(LOGIN_SUCCESS);
                            Intent intent = new Intent(LoginActivity.this, UserInfoActivity.class);
//                            intent.putExtra("userId", user.getId())
//                                    .putExtra("userName", user.getUserName())
//                                    .putExtra("phone", user.getPhone());
                            SharedPreferences.Editor editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                            editor.putString("userName", user.getUserName())
                                    .putInt("userId", user.getId())
                                    .putString("phone", user.getPhone())
                                    .apply();

                            userId = user.getId();
                            startActivity(intent);
                        } else {
                            hand.sendEmptyMessage(LOGIN_ERROR);
                        }
                    }
                }.start();
                finish();
            }
        });
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
