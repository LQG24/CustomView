package com.example.customview.NestedScrolling;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.example.customview.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
/**
 * NestedTraditionActivity里边 NestedTraditionLayout实现NestedScrollingParent滑动嵌套 
 */
public class NestedTraditionActivity extends AppCompatActivity {


    private List<Fragment> fragmentList;
    private List<String> titles;
    private ViewPager2 mViewPager2;
    private TabLayout tab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nested_tradition);
        mViewPager2 = findViewById(R.id.view_pager);
        tab = findViewById(R.id.tab);
        mViewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        fragmentList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        for(int i = 0 ; i < 4;i++){
            fragmentList.add(NestedTestFragment.newIntance("传统嵌套滑动"));
        }
        titles = new ArrayList<>();
        titles.add("首页");
        titles.add("商品");
        titles.add("个人");
        titles.add("关于");



        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(this,fragmentList);
        mViewPager2.setAdapter(myFragmentAdapter);


        new TabLayoutMediator(tab, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles.get(position));
            }
        }).attach();




    }



}
