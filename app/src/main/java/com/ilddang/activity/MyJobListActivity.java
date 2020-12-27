package com.ilddang.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.ilddang.R;
import com.ilddang.widget.FragmentAdapter;

public class MyJobListActivity extends BaseActivity {
    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_job_list_activity);
        createActionBar(getResources().getString(R.string.my_job));

        mTabLayout = findViewById(R.id.tab_layout);
        setViewPager();
        new TabLayoutMediator(mTabLayout, mViewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0 ) {
                            tab.setText(getResources().getString(R.string.my_job_to_see_list));
                        } else if (position == 1) {
                            tab.setText(getResources().getString(R.string.my_job_to_see_calendar));
                        }
                    }
                }
        ).attach();

    }

    private void setViewPager() {
        FragmentAdapter adapter = new FragmentAdapter(this);
        mViewPager = findViewById(R.id.my_job_list_pager);
        mViewPager.setAdapter(adapter);
        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }
}
