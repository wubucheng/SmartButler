package cn.devshare.smartbutler.utils;

import android.util.Log;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.utils
 * Class describe:Log工具类
 * Author: cheng
 * Create time: 2017/6/26 20:53
 */
public class L {

    public static final String TAG="Smartbutler";
    private static boolean DEBUG=true;

    public static void d(String text){
        if(DEBUG){
            Log.d(TAG,text);
        }
    }

    public static void i(String text){
        if(DEBUG){
            Log.i(TAG,text);
        }
    }

    public static void w(String text){
        if(DEBUG){
            Log.w(TAG,text);
        }
    }

    public static void e(String text){
        if(DEBUG){
            Log.e(TAG,text);
        }
    }


}
