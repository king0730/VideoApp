package com.example.videoapp.activity;

import android.os.Bundle;
import android.webkit.WebSettings;

import com.example.videoapp.R;
import com.example.videoapp.jsbridge.BridgeHandler;
import com.example.videoapp.jsbridge.BridgeWebView;
import com.example.videoapp.jsbridge.CallBackFunction;

public class WebActivity extends BaseActivity{

    private BridgeWebView bridgeWebView;
    private String url;

    @Override
    protected int initLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        bridgeWebView = findViewById(R.id.bridgeWebView);
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            url = bundle.getString("url");

        }
        registerJavaHandler();
        initWebView();
    }

    private void initWebView() {
        WebSettings settings = bridgeWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        bridgeWebView.loadUrl(url);
    }

    private void registerJavaHandler() {
        bridgeWebView.registerHandler("goback", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                finish();
            }
        });
    }

}
