package cn.devshare.smartbutler.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.adapter.CourierAdapter;
import cn.devshare.smartbutler.entity.CourierData;
import cn.devshare.smartbutler.utils.L;
import cn.devshare.smartbutler.utils.StaticClass;
import cn.devshare.smartbutler.utils.ToastUtil;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.ui
 * Class describe:
 * Author: cheng
 * Create time: 2017/7/1 15:21
 */
public class CourierActivity extends BaseActivity implements View.OnClickListener{

    private EditText compNameEt;
    private EditText numberEt;
    private Button getCourierBt;
    private ListView mListView;

    private List<CourierData> mList = new ArrayList<>();
    private CourierAdapter courierAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        initView();
    }

    private void initView() {
        compNameEt = (EditText) findViewById(R.id.compname_et);
        numberEt = (EditText) findViewById(R.id.courier_number_et);
        getCourierBt = (Button) findViewById(R.id.get_courier_bt);
        getCourierBt.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.mListView);
    }

    @Override
    public void onClick(View v) {
        String compName = compNameEt.getText().toString().trim();
        String number = numberEt.getText().toString().trim();
        if (!TextUtils.isEmpty(compName) && !TextUtils.isEmpty(number)) {
            String url="http://v.juhe.cn/exp/index?key="+ StaticClass.COURIER_KEY+"&com="+compName+"&no="+number;
            L.i("the reques url is"+url);
          RxVolley.get(url, new HttpCallback() {
              @Override
              public void onSuccess(String t) {
                  L.i("zhixing");
                  ToastUtil.TS(CourierActivity.this,t);

                  super.onSuccess("Courier:"+t);
                  L.i(t);
                  parsingJson(t);
              }

              @Override
                public void onFailure(int errorNo, String strMsg) {
                    super.onFailure(errorNo, strMsg);
                  L.i("error: "+errorNo);
                    L.i("error: "+strMsg);
                }

            });
        }else{
            ToastUtil.TS(this,"输入内容不能为空");
        }
    }

    private void parsingJson(String t){
        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(t);
            JSONObject jsonResult=jsonObject.getJSONObject("result");
            JSONArray jsonArray=jsonResult.getJSONArray("list");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject json= (JSONObject) jsonArray.get(i);
                CourierData courierData=new CourierData();
                courierData.setRemark(json.getString("remark"));
                if(json.getString("zone")!=null&&!json.getString("zone").equals("")){
                    courierData.setZone(json.getString("zone"));
                }else{
                    courierData.setZone("暂无地址信息");
                }

                courierData.setDatetime(json.getString("datetime"));
                mList.add(courierData);

            }
            Collections.reverse(mList);
            courierAdapter=new CourierAdapter(this,mList);
            mListView.setAdapter(courierAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
