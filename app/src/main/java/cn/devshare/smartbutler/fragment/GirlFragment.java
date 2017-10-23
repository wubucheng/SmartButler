package cn.devshare.smartbutler.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.devshare.smartbutler.R;
import cn.devshare.smartbutler.adapter.GirlAdapter;
import cn.devshare.smartbutler.entity.GirlData;
import cn.devshare.smartbutler.utils.PicassoUtils;
import cn.devshare.smartbutler.utils.StaticClass;
import cn.devshare.smartbutler.view.CustomDialog;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.fragment
 * Class describe: 美女板块
 * Author: cheng
 * Create time: 2017/6/26 19:46
 */
public class GirlFragment extends Fragment {
    private GridView girlGv;
    private GirlData girlData;
    private List<GirlData> girlDataList=new ArrayList<GirlData>();
    private CustomDialog dialog;
    private ImageView girlView;
    private PhotoViewAttacher photoAttacher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_girl, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        girlGv= (GridView) view.findViewById(R.id.girl_gv);
        //初始化提示框
        dialog = new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, R.layout.dialog_girl,
                R.style.Theme_dialog, Gravity.CENTER,R.style.pop_anim_style);
        girlView= (ImageView) dialog.findViewById(R.id.girl_item_iv);

        getData();
        girlGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GirlData girlData=girlDataList.get(position);
                PicassoUtils.loadImageView(getContext(),girlData.getImagUrl(),girlView);
                //设置绑定
                photoAttacher=new PhotoViewAttacher(girlView);
                //设置刷新
                photoAttacher.update();
                dialog.show();
            }
        });
    }

    private void getData() {
        RxVolley.get(StaticClass.API.GANGK_GIRL_API, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                parseJson(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }

    private void parseJson(String t) {
        try {
            JSONObject jsonObject=new JSONObject(t);
            JSONArray jsonArray=jsonObject.getJSONArray("results");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject urlJson= (JSONObject) jsonArray.get(i);
                String imgUrl=urlJson.getString("url");
                GirlData girlData=new GirlData();
                girlData.setImagUrl(imgUrl);
                girlDataList.add(girlData);

            }
            GirlAdapter girlAdapter=new GirlAdapter(getContext(),girlDataList);
            girlGv.setAdapter(girlAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
