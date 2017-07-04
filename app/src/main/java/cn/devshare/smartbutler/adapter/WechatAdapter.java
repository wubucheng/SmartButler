package cn.devshare.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.entity.WeChatData;
import cn.devshare.smartbutler.utils.L;
import cn.devshare.smartbutler.utils.PicassoUtils;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.adapter
 * Class describe:
 * Author: cheng
 * Create time: 2017/7/3 15:21
 */
public class WechatAdapter extends BaseAdapter {
    private Context context;
    private List<WeChatData>weChatDatas;
    private LayoutInflater layoutInflater;
    private int width,height;
    private WindowManager windowManager;
    public WechatAdapter(Context context,List<WeChatData>weChatDatas){
        this.context=context;
        this.weChatDatas=weChatDatas;
        L.i("the data is "+weChatDatas);
        layoutInflater=LayoutInflater.from(context);

        windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width=windowManager.getDefaultDisplay().getWidth();
        height=windowManager.getDefaultDisplay().getHeight();
    }
    @Override
    public int getCount() {
        if(weChatDatas.size()>0){
            return weChatDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return weChatDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.template_wechat_item,null);
            viewHolder.wechatView= (ImageView) convertView.findViewById(R.id.wechat_iv);
            viewHolder.title= (TextView) convertView.findViewById(R.id.wechat_title_tv);
            viewHolder.ctime= (TextView) convertView.findViewById(R.id.time_tv);
            viewHolder.source= (TextView) convertView.findViewById(R.id.source_tv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        WeChatData weChatData=weChatDatas.get(position);
        PicassoUtils.loadImageViewSize(context,weChatData.getImgUrl(),width/3,250,viewHolder.wechatView);
        viewHolder.title.setText(weChatData.getTitle());
        viewHolder.ctime.setText(weChatData.getCtime());
        viewHolder.source.setText(weChatData.getSource());
        return convertView;
    }

    class ViewHolder{
        private ImageView wechatView;
        private TextView title;
        private TextView ctime;
        private TextView source;
    }
}
