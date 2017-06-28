package cn.devshare.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.devshare.smartbutler.MainActivity;
import cn.devshare.smartbutler.R;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler.ui
 * Class describe:
 * Author: cheng
 * Create time: 2017/6/27 11:09
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener{
    private ViewPager viewPager;
    private List<View> viewList=new ArrayList<>();
    private View view1,view2,view3;
    private ImageView point1,point2,point3;
    private ImageView backIv;
    private Button startBt;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        initView();

    }

    private void initView() {
        point1= (ImageView) findViewById(R.id.point1);
        point2= (ImageView) findViewById(R.id.point2);
        point3= (ImageView) findViewById(R.id.point3);

        setPointImag(true, false, false);
        backIv= (ImageView) findViewById(R.id.back_iv);
        backIv.setOnClickListener(this);
        viewPager= (ViewPager) findViewById(R.id.guide_vp);

        view1=View.inflate(this,R.layout.pager_item_one,null);
        view2=View.inflate(this,R.layout.pager_item_two,null);
        view3=View.inflate(this,R.layout.pager_item_three,null);
        startBt= (Button) view3.findViewById(R.id.start_bt);
        startBt.setOnClickListener(this);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        viewPager.setAdapter(new  GuideAdapter());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        setPointImag(true,false,false);
                        backIv.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setPointImag(false,true,false);
                        backIv.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setPointImag(true,false,true);
                        backIv.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    //进入首页
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_iv:
            case R.id.start_bt:
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    private class GuideAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return viewList.size();
        }

        //
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        //此方法作用：
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(viewList.get(position));

            //super.destroyItem(container, position, object);
        }
    }

    //设置圆点的选中效果
    private void setPointImag(boolean isCheck1, boolean isCheck2, boolean isCheck3){
        if (isCheck1) {
            point1.setBackgroundResource(R.drawable.point_on);
        } else {
            point1.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheck2) {
            point2.setBackgroundResource(R.drawable.point_on);
        } else {
            point2.setBackgroundResource(R.drawable.point_off);
        }

        if (isCheck3) {
            point3.setBackgroundResource(R.drawable.point_on);
        } else {
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }
}
