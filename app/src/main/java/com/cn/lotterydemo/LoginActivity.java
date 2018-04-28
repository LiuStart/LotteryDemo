package com.cn.lotterydemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/4/20.
 */

public class LoginActivity extends AppCompatActivity{
    Tencent mTencent;
    private SharedPreferences USER;
    private EditText userName;
    private EditText passWord;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        USER =getSharedPreferences("USER",MODE_PRIVATE);
        mTencent = Tencent.createInstance("1106779649",getApplicationContext());
        Button login=findViewById(R.id.bt_login);
        userName=findViewById(R.id.username);
        passWord=findViewById(R.id.password);
        Button regist=findViewById(R.id.bt_register);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(userName.getText().toString())||TextUtils.isEmpty(passWord.getText().toString())){
                    Toast.makeText(LoginActivity.this,"不能为空！！",Toast.LENGTH_SHORT).show();
                    return;
                }
                String name=userName.getText().toString();
                if(!isEmail(name)){
                    Toast.makeText(LoginActivity.this,"邮箱格式不正确！！",Toast.LENGTH_SHORT).show();
                    return;
                }
                String pass=passWord.getText().toString();
                if(pass.length()<8){
                    Toast.makeText(LoginActivity.this,"密码过短！！",Toast.LENGTH_SHORT).show();
                    return;
                }
                USER.edit().putString(name,name).commit();
                USER.edit().putString(pass,pass).commit();
                USER.edit().putString("NAME",name).commit();
                USER.edit().putString("PASS",pass).commit();
                Toast.makeText(LoginActivity.this,"注册成功！！",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(userName.getText().toString())||TextUtils.isEmpty(passWord.getText().toString())){
                    Toast.makeText(LoginActivity.this,"不能为空！！",Toast.LENGTH_SHORT).show();
                    return;
                }
                String name=userName.getText().toString();
                if(!isEmail(name)){
                    Toast.makeText(LoginActivity.this,"邮箱格式不正确！！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(USER.getString(name,""))){
                    Toast.makeText(LoginActivity.this,"用户名不存在！！",Toast.LENGTH_SHORT).show();
                    return;
                }
                String pass=passWord.getText().toString();
                if(!pass.equals(TextUtils.isEmpty(USER.getString(pass,"")))){
                    Toast.makeText(LoginActivity.this,"密码不正确！！",Toast.LENGTH_SHORT).show();
                    return;
                }
                USER.edit().putString("NAME",name).commit();
                USER.edit().putString("PASS",pass).commit();
                Toast.makeText(LoginActivity.this,"登陆成功！！",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        ImageView qqlogin=findViewById(R.id.qq_login);
        qqlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTencent.login(LoginActivity.this,"all",new BaseUiListener());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());

        if(requestCode == Constants.REQUEST_API) {
            if(resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new BaseUiListener());
            }
        }
    }
    //判断email格式是否正确
    public boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    private class BaseUiListener implements IUiListener {

// onError onCancel 方法具体内容自己搜索
String openidString;
        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
            try {
                //获得的数据是JSON格式的，获得你想获得的内容
                //如果你不知道你能获得什么，看一下下面的LOG
                Log.v("----TAG--", "-------------" + response.toString());
                openidString = ((JSONObject) response).getString("openid");
                mTencent.setOpenId(openidString);
              //  saveUser("44", "text", "text", 1);
                mTencent.setAccessToken(((JSONObject) response).getString("access_token"), ((JSONObject) response).getString("expires_in"));


                Log.v("TAG", "-------------" + openidString);
                //access_token= ((JSONObject) response).getString("access_token");
                //expires_in = ((JSONObject) response).getString("expires_in");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            /**到此已经获得OpneID以及其他你想获得的内容了
             QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？
             sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
             如何得到这个UserInfo类呢？ 获取详细信息的UserInfo ，返回的信息参看下面地址：
             http://wiki.open.qq.com/wiki/%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E4%BF%A1%E6%81%AF#1._Tencent.E7.B1.BB.E7.9A.84request.E6.88.96requestAsync.E6.8E.A5.E5.8F.A3.E7.AE.80.E4.BB.8B
             */

            QQToken qqToken = mTencent.getQQToken();
            UserInfo info = new UserInfo(getApplicationContext(), qqToken);

            //    info.getUserInfo(new BaseUIListener(this,"get_simple_userinfo"));
            info.getUserInfo(new IUiListener() {
                @Override
                public void onComplete(Object o) {
                    //用户信息获取到了
                    try {
                        Log.v("用户名", ((JSONObject) o).getString("nickname"));
                        Log.v("用户姓名", ((JSONObject) o).getString("gender"));
                        Log.v("UserInfo",o.toString());
                        Intent intent1 = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent1);
                        finish();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(UiError uiError) {
                    Log.v("UserInfo","onError");
                }

                @Override
                public void onCancel() {
                    Log.v("UserInfo","onCancel");
                }
            });



        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getApplicationContext(), "登陆异常", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "登陆取消", Toast.LENGTH_SHORT).show();
        }


    }
}