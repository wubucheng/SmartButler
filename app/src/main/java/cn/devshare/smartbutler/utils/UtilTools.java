package cn.devshare.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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

    //保存图片
    public static void putImageToSharePre(Context context,ImageView imageView){
        BitmapDrawable bitmapDrawable= (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();
        ByteArrayOutputStream byteOutStrem=new ByteArrayOutputStream();
        //压缩，并将压缩到ByteArrayOutputStream，再将其转换为字节数组
        bitmap.compress(Bitmap.CompressFormat.JPEG,80,byteOutStrem);
        byte[]byteArray=byteOutStrem.toByteArray();

        String imgString=Base64.encodeToString(byteArray, Base64.DEFAULT);
        SharePreUtil.putString(context,"img_head",imgString);

    }

    //读取图片
    public static void getImageFromSharePre(Context context,ImageView imageView){
        String imgString=SharePreUtil.getString(context,"img_head","");
        if(!imgString.equals("")){
            byte[]byteArray=Base64.decode(imgString, Base64.DEFAULT);
            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(byteArray);
            Bitmap bitmap= BitmapFactory.decodeStream(byteInputStream);
            imageView.setImageBitmap(bitmap);

        }
    }
}
