package com.jordanhuus.www.newsudacityproject;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by jordanhuus on 5/19/2018.
 */

public class MainActivityPagerAdapter extends FragmentStatePagerAdapter {

    public MainActivityPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment mainPageFragment = null;
        switch (position) {
            case 0:
                mainPageFragment = ArticlesFragment.newInstance();
                break;
            case 1:
                mainPageFragment = CategoriesFragment.newInstance();
                break;
            default:
                break;
        }

        return mainPageFragment;
   }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Articles";
            default:
                return "Search";
        }
    }
}
