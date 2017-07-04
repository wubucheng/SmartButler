package cn.devshare.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.entity.GirlData;
import cn.devshare.smartbutler.utils.PicassoUtils;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.adapter
 * Class describe:
 * Author: cheng
 * Create time: 2017/7/4 11:23
 */
public class GirlAdapter extends BaseAdapter {
    private Context context;
    private List<GirlData> girlDatas;
    private LayoutInflater layoutInflater;
    private int width;
    private WindowManager windowManager;
    public GirlAdapter(Context context, List<GirlData> girlDatas) {
        this.context = context;
        this.girlDatas = girlDatas;
        layoutInflater = LayoutInflater.from(context);
        windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width=windowManager.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return girlDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return girlDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.girl_item,null);
            viewHolder.imgaeView= (ImageView) convertView.findViewById(R.id.girl_iv);
            convertView.setTag(viewHolder);

        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        GirlData girlData=girlDatas.get(position);
        PicassoUtils.loadImageViewSize(context,girlData.getImagUrl(),width/2,500,viewHolder.imgaeView);
        return convertView;
    }

    class ViewHolder{
        private ImageView imgaeView;

    }
}
