package com.example.petmanagement;

import static com.example.petmanagement.LoginActivity.userId;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddPetActivity extends AppCompatActivity {
    private String[] sexArray = {"雄性", "雌性"};
    EditText petNameEd;
    Spinner petSexSp;
    EditText petAgeEd;
    EditText petBreedEd;
    public static final int PET_INFO_ERROR = 0;
    public static final int ADD_PET_ERROR = 1;
    public static final int ADD_PET_SUCCESS = 2;


    @SuppressLint("HandlerLeak")
    final Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                Toast.makeText(getApplicationContext(), "宠物信息错误", Toast.LENGTH_LONG).show();
            } else if (msg.what == 1) {
                Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_LONG).show();
            } else if (msg.what == 2) {
                Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_pet_info);
        ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftInput(view.getWindowToken());
            }
        });
        getSupportActionBar().hide();//隐藏标题栏

        petNameEd = findViewById(R.id.pet_name_edit);
        petSexSp = findViewById(R.id.spinner);
        petAgeEd = findViewById(R.id.pet_age_edit);
        petBreedEd = findViewById(R.id.pet_breed_edit);

        findViewById(R.id.foster_info3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPetActivity.this, MainActivity.class));
            }
        });

        findViewById(R.id.user_info3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPetActivity.this, UserInfoActivity.class));
            }
        });

        findViewById(R.id.add_pet_info).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pet pet = new Pet();
                new Thread() {
                    @Override
                    public void run() {
                        String petName = petNameEd.getText().toString();
                        String petSex = petSexSp.getSelectedItem().toString();
                        String petAge = petAgeEd.getText().toString();
                        String petBreed = petBreedEd.getText().toString();
                        if (TextUtils.isEmpty(petName) || TextUtils.isEmpty(petSex)
                                || Integer.parseInt(petAge) < 0 || Integer.parseInt(petAge) > 30
                                || TextUtils.isEmpty(petBreed)) {
                            hand.sendEmptyMessage(PET_INFO_ERROR);
                            return;
                        }

                        FosterInfoDao fosterInfoDao = new FosterInfoDao();
                        int msg = fosterInfoDao.addPetInfo(Integer.toString(userId), petName, petSex, petAge, petBreed);
                        if (msg != 0) {
                            hand.sendEmptyMessage(ADD_PET_SUCCESS);
                            finish();
                        } else {
                            hand.sendEmptyMessage(ADD_PET_ERROR);
                        }
                    }
                }.start();
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
