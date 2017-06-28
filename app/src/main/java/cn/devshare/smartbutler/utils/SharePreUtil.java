package cn.devshare.smartbutler.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.utils
 * Class describe:SharedPreferences工具类
 * Author: cheng
 * Create time: 2017/6/27 10:04
 */
public class SharePreUtil {
    public static final String NAME="config";

    public static void putString(Context context,String key,String value){
        SharedPreferences sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static String getString(Context context,String key,String defValue){
        SharedPreferences sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    public static void putInt(Context context,String key,int value){
        SharedPreferences sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt(key,value);
        editor.commit();
    }

    public static int getInt(Context context,String key,int  defValue){
        SharedPreferences sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    public static void putBoolean(Context context,String key,boolean value){
        SharedPreferences sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static boolean getBoolean(Context context,String key,boolean  defValue){
        SharedPreferences sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    public static void deleteShare(Context context,String key){
        SharedPreferences sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.remove(key).commit();
    }

    public static void deleteAll(Context context,String key){
        SharedPreferences sp=context.getSharedPreferences(NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.clear().commit();
    }
}
