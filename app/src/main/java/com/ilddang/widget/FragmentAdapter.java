package com.ilddang.widget;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ilddang.fragment.MyJobCalendarFragment;
import com.ilddang.fragment.MyJobListFragment;

public class FragmentAdapter extends FragmentStateAdapter {

    public FragmentAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return MyJobListFragment.newInstance();
            case 1:
                return MyJobCalendarFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
