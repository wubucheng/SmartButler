package cn.devshare.smartbutler.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.entity.User;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.ui
 * Class describe:注册
 * Author: cheng
 * Create time: 2017/6/28 10:37
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText userEt;
    private EditText ageEt;
    private EditText descEt;
    private RadioGroup mRadioGroup;
    private EditText passEt;
    private EditText passwordEt;//确认密码
    private EditText emailEt;
    private Button registerBt;
    //性别
    private boolean isGender = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regiser);
        initView();
    }

    private void initView() {
        userEt = (EditText) findViewById(R.id.user_et);
        ageEt = (EditText) findViewById(R.id.age_et);
        descEt = (EditText) findViewById(R.id.desc_et);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        passEt = (EditText) findViewById(R.id.pass_et);
        passwordEt = (EditText) findViewById(R.id.password_et);
        emailEt = (EditText) findViewById(R.id.email_et);
        registerBt= (Button) findViewById(R.id.regiser_bt);
        registerBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.regiser_bt:
                String name = userEt.getText().toString().trim();
                String age = ageEt.getText().toString().trim();
                String desc = descEt.getText().toString().trim();
                String pass = passEt.getText().toString().trim();
                String password = passwordEt.getText().toString().trim();
                String email = emailEt.getText().toString().trim();
                //判断是否为空
                if (!TextUtils.isEmpty(name) & !TextUtils.isEmpty(age) &
                        !TextUtils.isEmpty(pass) &
                        !TextUtils.isEmpty(password) &
                        !TextUtils.isEmpty(email)) {
                    //判断两次输入的密码是否一致
                    if (pass.equals(password)) {
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == R.id.boy_rb) {
                                    isGender = true;
                                } else if (checkedId == R.id.girl_rb) {
                                    isGender = false;
                                }
                            }
                        });
                        //判断简介是否为空
                        if (TextUtils.isEmpty(desc)) {
                            desc = getString(R.string.text_nothing);
                        }

                        //注册
                        User user=new User();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setAge(Integer.parseInt(age));
                        user.setSex(isGender);
                        user.setDesc(desc);
                        user.signUp(new SaveListener<User>() {
                            @Override
                            public void done(User user, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                }
        }
    }
}
