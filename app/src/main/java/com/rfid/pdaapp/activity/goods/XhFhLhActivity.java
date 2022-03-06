package com.rfid.pdaapp.activity.goods;

import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.rfid.pdaapp.R;
import com.rfid.pdaapp.adapters.FragmentAdapter;
import com.rfid.pdaapp.common.base.BaseActivity;
import com.rfid.pdaapp.fragments.ManageGoodsFragment;
import com.rfid.pdaapp.utils.DisplayUtil;
import com.rfid.pdaapp.views.TablayoutTabView;
import com.rfid.pdaapp.views.TitleSearchBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ydh on 2022/3/6
 * 现货发货理货
 */
public class XhFhLhActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TitleSearchBar tvTitle;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private List<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private ManageGoodsFragment mFragmentMe;
    private ManageGoodsFragment mFragmentOther;
    private FragmentAdapter mVpAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xhfhlh;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle.setListener(new TitleSearchBar.TextListener() {
            @Override
            public void onClick(TextView textView) {//点击搜索

            }
        });
        tvTitle.addTextChangedListener(new TitleSearchBar.EditeListener() {
            @Override
            public void afterTextChanged(String s) {//搜索输入框

            }
        });
        addFragment();
    }

    private void addFragment() {
        mTitleList.clear();
        mTitleList.add("我的");
        mTitleList.add("其他");
        mFragmentMe = ManageGoodsFragment.newInstance(0);//我的
        mFragmentOther = ManageGoodsFragment.newInstance(1);//其他
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