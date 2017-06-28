package cn.devshare.smartbutler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.devshare.smartbutler.fragment.ButlerFragment;
import cn.devshare.smartbutler.fragment.GirlFragment;
import cn.devshare.smartbutler.fragment.UserFragment;
import cn.devshare.smartbutler.fragment.WechatFragment;
import cn.devshare.smartbutler.ui.SettingActivity;

/**
 * ProjectName: SmartButler
 * PackName：cn.devshare.smartbutler
 * Class describe:管理4个fragment
 * Author: cheng
 * Create time: 2017/6/26 19:40
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<String> titleList;
    private List<Fragment> fragmentList;

    private FloatingActionButton settingFloat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //去掉阴影
        getSupportActionBar().setElevation(0);

        initData();
        initView();

    }

    private void initData() {
        titleList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        titleList.add(getString(R.string.text_butler_service));
        titleList.add(getString(R.string.text_wechat));
        titleList.add(getString(R.string.text_girl));
        titleList.add(getString(R.string.text_user_info));

        fragmentList.add(new ButlerFragment());
        fragmentList.add(new WechatFragment());
        fragmentList.add(new GirlFragment());
        fragmentList.add(new UserFragment());

    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        settingFloat= (FloatingActionButton) findViewById(R.id.fab_setting);
        settingFloat.setOnClickListener(this);
        settingFloat.setVisibility(ViewPager.GONE);
        mViewPager.setOffscreenPageLimit(fragmentList.size());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    settingFloat.setVisibility(ViewPager.GONE);
                }else{
                    settingFloat.setVisibility(ViewPager.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titleList.get(position);
            }
        });
        //绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
        }


    }
}
