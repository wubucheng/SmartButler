package cn.devshare.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.entity.User;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.ui
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/28 16:38
 */
public class ForgetActivity extends  BaseActivity implements View.OnClickListener{

    private Button forgetPassBt;
    private EditText emailEt;

    private EditText nowEt;
    private EditText newEt;
    private EditText newPassEt;
    private Button updatePassBt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forget);
        initView();
    }

    private void initView() {
        forgetPassBt = (Button) findViewById(R.id.forget_password_bt);
        forgetPassBt.setOnClickListener(this);
        emailEt = (EditText) findViewById(R.id.email_et);

        nowEt = (EditText) findViewById(R.id.now_et);
        newEt = (EditText) findViewById(R.id.new_et);
        newPassEt = (EditText) findViewById(R.id.new_password_et);
        updatePassBt = (Button) findViewById(R.id.update_password_bt);
        updatePassBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forget_password_bt:
                String email=emailEt.getText().toString().trim();
                if(!TextUtils.isEmpty(email)){
                    User.resetPasswordByEmail(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(ForgetActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(ForgetActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(ForgetActivity.this,"输入邮箱不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.update_password_bt:
                String nowPass=nowEt.getText().toString().trim();
                String newPass=newEt.getText().toString().trim();
                String confirPass=newPassEt.getText().toString().trim();
                if(!TextUtils.isEmpty(nowPass) & !TextUtils.isEmpty(newPass) & !TextUtils.isEmpty(confirPass)){
                    if(newPass.equals(confirPass)){
                        User.updateCurrentUserPassword(nowPass, newPass, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    Toast.makeText(ForgetActivity.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                                            finish();
                                }else {
                                    Toast.makeText(ForgetActivity.this,"密码修改失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(ForgetActivity.this,"两次密码输入不一致",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ForgetActivity.this,"输入内容不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
