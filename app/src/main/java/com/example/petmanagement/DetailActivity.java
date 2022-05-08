package com.example.petmanagement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
        getSupportActionBar().hide();//隐藏标题栏

        initFosterCard(getIntent());

        findViewById(R.id.foster_info2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
            }
        });

        findViewById(R.id.user_info2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailActivity.this, UserInfoActivity.class));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initFosterCard(Intent intent) {
        FosterInfo info = (FosterInfo) intent.getSerializableExtra("fosterInfo");
        ((TextView)findViewById(R.id.foster_id_in_detail)).setText(Integer.toString(info.getFosterId()));
        ((TextView)findViewById(R.id.pet_id_in_detail)).setText(Integer.toString(info.getPetId()));
        ((TextView)findViewById(R.id.pet_name_in_detail)).setText(info.getPetName());
        ((TextView)findViewById(R.id.duration)).setText(Integer.toString(info.getDuration()));
        ((TextView)findViewById(R.id.bathing_time)).setText(Integer.toString(info.getBathingTimes()));
        ((TextView)findViewById(R.id.total_cost)).setText(Integer.toString(info.getTotalCost()));
        ((TextView)findViewById(R.id.activity)).setText(info.getActivity());
        ((TextView)findViewById(R.id.health_in_detail)).setText(info.getHealth());
    }
}
