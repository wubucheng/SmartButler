package cn.devshare.smartbutler.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.utils.L;
import cn.devshare.smartbutler.utils.StaticClass;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.ui
 * Class describe:
 * Author: cheng
 * Create time: 2017/7/2 14:59
 */
public class PhoneActivity extends BaseActivity implements View.OnClickListener {

    //输入框
    private EditText et_number;
    //公司logo
    private ImageView iv_company;
    //结果
    private TextView tv_result;

    private Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_del, btn_query;

    //标记位
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initView();
    }

    //初始化View
    private void initView() {
        et_number = (EditText) findViewById(R.id.number_et);
        iv_company = (ImageView) findViewById(R.id.iv_company);
        tv_result = (TextView) findViewById(R.id.result_tv);

        btn_0 = (Button) findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_3 = (Button) findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        btn_4 = (Button) findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        btn_5 = (Button) findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
        btn_6 = (Button) findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);
        btn_7 = (Button) findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);
        btn_8 = (Button) findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);
        btn_9 = (Button) findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);

        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_number.setText("");
                return false;
            }
        });
    }
        @Override
    public void onClick(View v) {
            String str = et_number.getText().toString();

            switch (v.getId()) {
                case R.id.btn_0:
                case R.id.btn_1:
                case R.id.btn_2:
                case R.id.btn_3:
                case R.id.btn_4:
                case R.id.btn_5:
                case R.id.btn_6:
                case R.id.btn_7:
                case R.id.btn_8:
                case R.id.btn_9:
                    if (flag) {
                        flag = false;
                        str = "";
                        et_number.setText("");
                    }
                    et_number.setText(str+((Button)v).getText());
                    et_number.setSelection(str.length()+1);
                    break;
                case R.id.btn_del:
                    if(!TextUtils.isEmpty(str)&& str.length() > 0){
                        et_number.setText(str.substring(0,str.length()-1));
                        et_number.setSelection(str.length()-1);
                    }
                    break;
                case R.id.btn_query:
                    if(!TextUtils.isEmpty(str)){
                        getPhone(str);
                    }
                    break;
            }
        }

    private void getPhone(String str) {
        String url=StaticClass.API.MOB_PHONE_API+"&phone="+str;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                L.i(t);
                pareseJson(t);
            }


        });
    }

/*
operator:卡类型
zipCode：邮编
 */
    private void pareseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String city=  jsonResult.getString("city");
            String cityCode=  jsonResult.getString("cityCode");
            String mobileNumber=  jsonResult.getString("mobileNumber");
            String operator=  jsonResult.getString("operator");
            String province=  jsonResult.getString("province");
            String zipCode=  jsonResult.getString("zipCode");

            tv_result.setText("归属地:" + province + city + "\n"
                    + "区号:" + cityCode + "\n"
                    + "邮编:" + zipCode + "\n"
                    + "类型:" + operator);
            String company="";
          if(operator.contains("移动")){
              company="移动";
          }else if(operator.contains("联通")){
              company="联通";
          }else if(operator.contains("电信")){
              company="电信";
            }
            switch (company){
                case "移动":
                    iv_company.setBackgroundResource(R.drawable.china_mobile);
                    break;
                case "联通":
                    iv_company.setBackgroundResource(R.drawable.china_unicom);
                    break;
                case "电信":
                    iv_company.setBackgroundResource(R.drawable.china_telecom);
                    break;
            }
            flag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
