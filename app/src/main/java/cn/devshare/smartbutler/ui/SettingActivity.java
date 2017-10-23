package cn.devshare.smartbutler.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.utils.L;
import cn.devshare.smartbutler.utils.SharePreUtil;
import cn.devshare.smartbutler.utils.StaticClass;
import cn.devshare.smartbutler.utils.ToastUtil;

import static cn.devshare.smartbutler.utils.SharePreUtil.getBoolean;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.ui
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/26 20:29
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener{
    //语音播报
    private Switch sw_speak;
    //检测更新
    private LinearLayout ll_update;
    private TextView tv_version;

    private String versionName;
    private int versionCode;

    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        sw_speak = (Switch) findViewById(R.id.sw_speak);
        sw_speak.setOnClickListener(this);
        boolean isSpeak = getBoolean(this, "isSpeak", false);
        sw_speak.setChecked(isSpeak);

        ll_update = (LinearLayout) findViewById(R.id.ll_update);
        ll_update.setOnClickListener(this);

        tv_version = (TextView) findViewById(R.id.tv_version);
        try {
            getVersionNameCode();
            tv_version.setText(getString(R.string.text_test_version) + versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            tv_version.setText(getString(R.string.text_test_version) );
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sw_speak:
                //切换相反
                sw_speak.setSelected(!sw_speak.isSelected());
                //保存状态
                SharePreUtil.putBoolean(this, "isSpeak", sw_speak.isChecked());
                break;
            //升级
            case R.id.ll_update:
                L.i("检查更新");
                RxVolley.get(StaticClass.API.UPDATE_APK_API, new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.i(t);
                        parsingJson(t);
                    }
                });
                break;
        }
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            int latestCode=jsonObject.getInt("versionCode");
            if(latestCode>versionCode){
                showUpdateDialog(jsonObject.getString("content"));
            }else {
                ToastUtil.TL(this,"当前是最新版本");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showUpdateDialog(String content){
        new AlertDialog.Builder(this).setTitle("有新版本啦！")
                .setMessage("修复多项Bug!")
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                L.i("点击了更新");
                Intent intent=new Intent(SettingActivity.this, UpdatActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }

    private void getVersionNameCode()throws PackageManager.NameNotFoundException{
        PackageManager packageManager=getPackageManager();
        PackageInfo packageInfo=packageManager.getPackageInfo(getPackageName(),0);
        versionName=packageInfo.versionName;
        versionCode=packageInfo.versionCode;
    }


}
