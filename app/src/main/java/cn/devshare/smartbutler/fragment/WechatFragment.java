package cn.devshare.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.adapter.WechatAdapter;
import cn.devshare.smartbutler.entity.WeChatData;
import cn.devshare.smartbutler.ui.WebViewActivity;
import cn.devshare.smartbutler.utils.L;
import cn.devshare.smartbutler.utils.StaticClass;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.fragment
 * Class describe:微信精选
 * Author: cheng
 * Create time: 2017/6/26 19:44
 */
public class WechatFragment extends Fragment {
    private ListView wechatLV;
    private List<WeChatData> weChatDatas;
    private WechatAdapter wechatAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        weChatDatas=new ArrayList<WeChatData>();
        wechatLV= (ListView) view.findViewById(R.id.wechat_lv);
        wechatLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title",weChatDatas.get(position).getTitle());
                intent.putExtra("url",weChatDatas.get(position).getUrl());
                startActivity(intent);
            }
        });
        getData();

    }

    private void getData(){
        final String url= StaticClass.API.TIANXING_WECHAT_API;
        L.i(url);
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                L.i("the url is "+url);
                parseJson(t);

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                L.w(strMsg);
            }
        });

    }

    private void parseJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONArray jsonArray=jsonObject.getJSONArray("newslist");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);
                WeChatData weChatData=new WeChatData();
                String ctime=json.getString("ctime");
                String title=json.getString("title");
                String source=json.getString("description");
                String picUrl=json.getString("picUrl");
                String url=json.getString("url");
                weChatData.setCtime(ctime);
                weChatData.setTitle(title);
                weChatData.setSource(source);
                weChatData.setImgUrl(picUrl);
                weChatData.setUrl(url);

                weChatDatas.add(weChatData);
                wechatAdapter=new WechatAdapter(getContext(),weChatDatas);
                wechatLV.setAdapter(wechatAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
