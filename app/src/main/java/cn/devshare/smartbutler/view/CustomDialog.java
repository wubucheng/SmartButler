package cn.devshare.smartbutler.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import cn.devshare.smartbutler.R;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.view
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/28 20:21
 */
public class CustomDialog extends Dialog {

    //定义模板
    public CustomDialog(Context context,int layout,int style) {
        this(context,WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT,layout,style, Gravity.CENTER);
    }



    //定义属性
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity,int anim) {
        super(context,style);
        setContentView(layout);
        Window window=getWindow();
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        layoutParams.width=WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity=gravity;
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);
    }

    //实例
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity){
        this(context,width,height,layout,style,gravity,R.style.pop_anim_style);
    }
}
