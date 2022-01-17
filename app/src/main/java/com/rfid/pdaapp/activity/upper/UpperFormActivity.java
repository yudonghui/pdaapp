package com.rfid.pdaapp.activity.upper;

import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rfid.pdaapp.R;
import com.rfid.pdaapp.adapters.FragmentAdapter;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.fragments.UpperFragment;
import com.rfid.pdaapp.utils.DisplayUtil;
import com.rfid.pdaapp.views.TablayoutTabView;
import com.rfid.pdaapp.views.TitleSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ydh on 2022/1/17
 * 普通上架 减库上架 加工上架 锁定仓收货上架
 */
public class UpperFormActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleSearchBar tvTitle;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mTitleList = new ArrayList<>();
    private UpperFragment mFragmentMe;
    private UpperFragment mFragmentOther;

    private int type;
    private FragmentAdapter mVpAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upper_form;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("type", 0);
        tvTitle.setListener(new TitleSearchBar.TextListener() {
            @Override
            public void onClick(TextView textView) {

            }
        });
        tvTitle.addTextChangedListener(new TitleSearchBar.EditeListener() {
            @Override
            public void afterTextChanged(String s) {

            }
        });
        addFragment();
    }

    private void addFragment() {
        mTitleList.clear();
        mTitleList.add("我的");
        mTitleList.add("其他");
        mFragmentMe = UpperFragment.newInstance(type, 0);//我的
        mFragmentOther = UpperFragment.newInstance(type, 1);//其他
        mFragmentList.add(mFragmentMe);
        mFragmentList.add(mFragmentOther);
        mVpAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        //填充适配器
        viewPager.setAdapter(mVpAdapter);
        //TabLayout加载viewpager
        tablayout.setupWithViewPager(viewPager);
        tablayout.post(new Runnable() {
            @Override
            public void run() {
                new TablayoutTabView().setIndicator(tablayout, DisplayUtil.dp2px(30), DisplayUtil.dp2px(30));
            }
        });
    }

}