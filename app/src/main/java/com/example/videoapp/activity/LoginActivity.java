package com.example.videoapp.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.videoapp.R;
import com.example.videoapp.api.Api;
import com.example.videoapp.api.ApiConfig;
import com.example.videoapp.api.TestCallback;
import com.example.videoapp.entity.LoginResponse;
import com.example.videoapp.util.StringUtils;
import com.google.gson.Gson;

import java.util.HashMap;

public class LoginActivity extends BaseActivity {

    private EditText etAccount;
    private EditText etPwd;
    private Button btnLogin;


    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_pwd);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void initData() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                login(account, pwd);
            }
        });
    }

    /*
    * 登录
    * */
    private void login(String account, String pwd) {
        if (StringUtils.isEmpty(account)) {
            showToast("请输入账号");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            showToast("请输入密码");
            return;
        }
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("mobile", account);
        params.put("password", pwd);
        Api.config(ApiConfig.LOGIN, params).postRequest(mContext, new TestCallback() {
            @Override
            public void onSuccess(final String res) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast(res);
//                    }
//                });
                Log.e("onSuccess", res);
                Gson gson = new Gson();
                LoginResponse loginResponse = gson.fromJson(res, LoginResponse.class);
                if (loginResponse.getCode() == 0) {
                    String token = loginResponse.getToken();
//                    SharedPreferences sp = getSharedPreferences("sp_test", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString("token", token);
//                    editor.commit();
                    insertVal("token", token);
                    navigateToWithFlag(HomeActivity.class,
                            Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    showToastSync("登陆成功");
                }else {
                    showToastSync("登陆失败");
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
    }

    /*
    * 判断账号和密码是否为空
    * */
//    private void login(String account, String pwd) {
//        if (StringUtils.isEmpty(account)) {
//            showToast("请输入账号");
//            return;
//        }
//        if (StringUtils.isEmpty(pwd)) {
//            showToast("请输入密码");
//            return;
//        }
//
//        //第一步创建OKHttpClient
//        OkHttpClient client = new OkHttpClient.Builder()
//                .build();
//        Map m = new HashMap();
//        m.put("mobile", account);
//        m.put("password", pwd);
//        JSONObject jsonObject = new JSONObject(m);
//        String jsonStr = jsonObject.toString();
//        RequestBody requestBodyJson =
//                RequestBody.create(MediaType.parse("application/json;charset=utf-8")
//                        , jsonStr);
//        //第三步创建Rquest
//        Request request = new Request.Builder()
//                .url(AppConfig.BASE_URl + "/app/login")
//                .addHeader("contentType", "application/json;charset=UTF-8")
//                .post(requestBodyJson)
//                .build();
//        //第四步创建call回调对象
//        final Call call = client.newCall(request);
//        //第五步发起请求
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("onFailure", e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                final String result = response.body().string();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showToast(result);
//                    }
//                });
//            }
//        });
//    }

}