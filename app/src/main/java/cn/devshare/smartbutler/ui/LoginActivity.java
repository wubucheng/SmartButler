package cn.devshare.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.devshare.smartbutler.MainActivity;
import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.entity.User;
import cn.devshare.smartbutler.utils.SharePreUtil;
import cn.devshare.smartbutler.view.CustomDialog;

import static cn.devshare.smartbutler.R.id.login_bt;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.ui
 * Class describe:登录
 * Author: cheng
 * Create time: 2017/6/27 11:09
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button registerBt;
    private Button loginBt;
    private EditText usernameEt;
    private EditText passwordEt;
    private CheckBox remPass;
    private TextView forgetPass;
    private CustomDialog dialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();


    }

    private void initView() {
        registerBt= (Button) findViewById(R.id.regiser_bt);
        loginBt= (Button) findViewById(login_bt);
        usernameEt= (EditText) findViewById(R.id.account_et);
        passwordEt= (EditText) findViewById(R.id.passwrod_et);
        remPass= (CheckBox) findViewById(R.id.remepass_cb);
        forgetPass= (TextView) findViewById(R.id.forgetpass_tv);
        forgetPass.setOnClickListener(this);
        loginBt.setOnClickListener(this);
        registerBt.setOnClickListener(this);

        boolean isRemberPass=SharePreUtil.getBoolean(this,"rempass",false);
        remPass.setChecked(isRemberPass);
        if(isRemberPass){
            String name = SharePreUtil.getString(this, "username", "");
            String password = SharePreUtil.getString(this, "password", "");
            usernameEt.setText(name);
            passwordEt.setText(password);
        }

        dialog=new CustomDialog(this,R.layout.dialog_loding,R.style.Theme_dialog);
        dialog.setCancelable(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.regiser_bt:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.login_bt:
                login();
                break;
            case R.id.forgetpass_tv:
                startActivity(new Intent(LoginActivity.this,ForgetActivity.class));
                break;

        }
    }

    private void login(){
        String username=usernameEt.getText().toString().trim();
        String password=passwordEt.getText().toString().trim();
        if(!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
            dialog.show();
            User user=new User();
            user.setUsername(username);
            user.setPassword(password);
            user.login(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    dialog.dismiss();
                    if(e==null){
                        //判断邮箱是否验证
                        if(user.getEmailVerified()){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "登录失败"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharePreUtil.putBoolean(this,"rempass",remPass.isChecked());
        if(remPass.isChecked()){
            SharePreUtil.putString(this,"username",usernameEt.getText().toString().trim());
            SharePreUtil.putString(this,"password",passwordEt.getText().toString().trim());
        }else {
            SharePreUtil.deleteShare(this,"username");
            SharePreUtil.deleteShare(this,"password");
        }
    }
}
