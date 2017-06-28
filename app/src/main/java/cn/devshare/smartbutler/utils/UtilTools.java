package cn.devshare.smartbutler.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.utils
 * Class describe:工具的统一类
 * Author: cheng
 * Create time: 2017/6/26 16:51
 */
public class UtilTools {

    //设置字体
    public static void setFont(Context mContext,TextView textView){
        Typeface fontType=Typeface.createFromAsset(mContext.getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(fontType);
    }
}
