package cn.devshare.smartbutler.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.utils
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/29 10:27
 */
public class ToastUtil {

    public static void TS(Context mContext,String content){
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
    }

    public static void TL(Context mContext,String content){
        Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
    }
}
