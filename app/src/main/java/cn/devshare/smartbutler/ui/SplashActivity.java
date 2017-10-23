package cn.devshare.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.utils.SharePreUtil;
import cn.devshare.smartbutler.utils.StaticClass;
import cn.devshare.smartbutler.utils.UtilTools;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.ui
 * Class describe:启动页
 * Author: cheng
 * Create time: 2017/6/27 10:50
 */
public class SplashActivity extends AppCompatActivity {
    TextView splashTv;
    Handler handler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //后面两句设置全屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);;

        setContentView(R.layout.activity_splash);


       handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case StaticClass.HANDLER_SPLASH:
                        if(isFirst()){
                            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                        }else {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        }
                        finish();
                        break;
                }
            }
        };

        initView();
    }

    private void initView() {
        splashTv= (TextView) findViewById(R.id.splash_tv);
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,2000);
        UtilTools.setFont(this,splashTv);
    }

    private boolean isFirst(){
        boolean isFirst= SharePreUtil.getBoolean(this,StaticClass.SHARE_IS_FIRST,true);
        if(isFirst){
            SharePreUtil.putBoolean(this,StaticClass.SHARE_IS_FIRST,false);
            return  true;
        }else {
            return false;
        }

    }
}
