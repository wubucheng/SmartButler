package cn.devshare.smartbutler.application;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.application
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/26 16:52
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Bmob.initialize(this, "b4b3d80d517f7a0c3f56f08a5a3c7ec4");
    }
}
