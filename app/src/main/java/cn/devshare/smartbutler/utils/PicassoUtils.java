package cn.devshare.smartbutler.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.utils
 * Class describe:
 * Author: cheng
 * Create time: 2017/7/4 10:02
 */
public class PicassoUtils {

    //加载图片
    public static void loadImageView(Context context, String url, ImageView imageView) {
        Picasso.with(context).load(url).into(imageView);
    }

    //加载图片时指定宽高
    public static void loadImageViewSize(Context context, String url, int width, int height, ImageView imageView) {
        Picasso.with(context).load(url).config(Bitmap.Config.RGB_565).resize(width, height).centerCrop().into(imageView);
    }

    //
    public static void loadImageViewHolder(Context context, String url, int loadImg, int errorImg, ImageView imageView){
        Picasso.with(context).load(url).placeholder(loadImg).error(errorImg).into(imageView);
    }

    //裁剪图片
    public static void loadImageViewGroup(Context context, String url,ImageView imageView){
        Picasso.with(context).load(url).transform(new CropSquareTransformation()).into(imageView);
    }

    private static class CropSquareTransformation implements Transformation {
        @Override
        public Bitmap transform(Bitmap source) {
            int size=Math.min(source.getWidth(),source.getHeight());
            int x=(source.getWidth()-size)/2;
            int y = (source.getHeight() - size) / 2;
            Bitmap bitmap=Bitmap.createBitmap(source,x,y,size,size);
            if(bitmap!=null){
                source.recycle();
            }
            return bitmap;
        }

        @Override
        public String key() {
            return "cheng";
        }
    }
}
