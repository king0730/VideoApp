package com.example.videoapp;

import android.view.View;
import android.widget.Button;

import com.example.videoapp.activity.BaseActivity;
import com.example.videoapp.activity.HomeActivity;
import com.example.videoapp.activity.LoginActivity;
import com.example.videoapp.activity.RegisterActivity;
import com.example.videoapp.util.StringUtils;

public class MainActivity extends BaseActivity {

    private Button btnLogin;
    private Button btnRegister;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
    }

    @Override
    protected void initData() {

        if (!StringUtils.isEmpty(findByKey("token"))) {
            navigateTo(HomeActivity.class);
            finish();
        }

        //设置登录监听事件
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
                navigateTo(LoginActivity.class);
            }
        });

        //设置注册监听事件
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//                startActivity(intent);
                navigateTo(RegisterActivity.class);
            }
        });
    }
}