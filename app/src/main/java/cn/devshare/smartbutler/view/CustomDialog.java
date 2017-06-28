package cn.devshare.smartbutler.view;

import android.app.Dialog;
import android.content.Context;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.view
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/28 20:21
 */
public class CustomDialog extends Dialog {
    public CustomDialog(Context context) {
        super(context);
    }

    public CustomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
