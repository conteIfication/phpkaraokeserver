package com.bku.cse.karaoke.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bku.cse.karaoke.fragment.HotFragment;
import com.bku.cse.karaoke.fragment.NewFragment;
import com.bku.cse.karaoke.fragment.RecentFragment;

/**
 * Created by quangthanh on 5/9/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:
                frag = new HotFragment();
                break;
            case 1:
                frag = new NewFragment();
                break;
            case 2:
                frag = new RecentFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        String title = "";
//        switch (position){
//            case 0:
//                title="New";
//                break;
//            case 1:
//                title="Hot";
//                break;
//            case 2:
//                title="Recent";
//                break;
//        }

        return null;
    }
}
