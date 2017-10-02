package org.effervescence.app17.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by odin on 2/10/17.
 */

public class CreditsFragmentAdapter extends FragmentStatePagerAdapter {

    private List<CreditsFragmentAdapter> fragments = new ArrayList<>();
    public String titles[] = {"ABOUT EFFE", "TEAM", "SPONSORS", "DEVELOPERS"};

    public CreditsFragmentAdapter(FragmentManager fm, List<CreditsFragmentAdapter> fragments) {
        super(fm);
        this.fragments.addAll(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}