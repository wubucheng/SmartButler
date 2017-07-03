package cn.devshare.smartbutler.utils;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.utils
 * Class describe:存放数据/常量
 * Author: cheng
 * Create time: 2017/6/26 16:54
 */
public class StaticClass {
    //闪屏延时
    public static final int HANDLER_SPLASH = 1001;
    //判断程序是否是第一次运行
    public static final String SHARE_IS_FIRST = "isFirst";
    public static final String COURIER_KEY="677c7f3bd198b6912070a6dbab9d87df";
    public static final String MOB_PHONE_KEY="1f1aaddfcc9c0";

    public static class API{
        //快递查询接口
        public static final String JUHE_COURIER_API="http://v.juhe.cn/exp/index?key="+ StaticClass.COURIER_KEY;
        //号码归属地接口
        public static final String MOB_PHONE_API="http://apicloud.mob.com/v1/mobile/address/query?key"+ StaticClass.MOB_PHONE_KEY+"&phone=";
        //聊天机器人接口
        public static final String QIINGYUNKE_CHAT_API="http://api.qingyunke.com/api.php?key=free&appid=0&msg=";
    }
}
