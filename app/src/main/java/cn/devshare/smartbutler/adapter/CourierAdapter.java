package cn.devshare.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.PipedOutputStream;
import java.util.List;

import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.entity.CourierData;
import cn.devshare.smartbutler.utils.L;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.adapter
 * Class describe:
 * Author: cheng
 * Create time: 2017/7/2 9:43
 */
public class CourierAdapter extends BaseAdapter {

    private Context context;
    private List<CourierData> courierDataList;
    private LayoutInflater layoutInflater;
    public CourierAdapter(Context context,List<CourierData> courierDataList){
        this.context=context;
        this.courierDataList=courierDataList;
        layoutInflater=LayoutInflater.from(context);
       // L.i("the coureies is"+courierDataList);
        //layoutInflater.inflate(R.layout.temp_courier_item,null);
       // context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        L.i("the size is "+courierDataList.size());
        return courierDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return courierDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.temp_courier_item,null);
            viewHolder.remarkTv= (TextView) convertView.findViewById(R.id.remark_tv);
            viewHolder.zoneTv= (TextView) convertView.findViewById(R.id.zone_tv);
            viewHolder.datetimeTv= (TextView) convertView.findViewById(R.id.datetime_tv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        CourierData courierData=courierDataList.get(position);
        viewHolder.remarkTv.setText(courierData.getRemark());
        viewHolder.zoneTv.setText(courierData.getZone());
        viewHolder.datetimeTv.setText(courierData.getDatetime());

        return convertView;
    }

    class  ViewHolder{
        TextView remarkTv;
        TextView zoneTv;
        TextView datetimeTv;

    }
}
