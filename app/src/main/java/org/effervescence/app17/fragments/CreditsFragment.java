package org.effervescence.app17.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.effervescence.app17.R;
import org.effervescence.app17.utils.CreditsFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreditsFragment extends Fragment {


    CreditsFragmentAdapter mCreditsFragmentAdapter;
    ViewPager mViewPager;

    public CreditsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_credits, container, false);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        List<CreditsTabFragment> fragments = new ArrayList<>();
        fragments.add(new CreditsTabFragment());
        fragments.add(new CreditsTabFragment());
        fragments.add(new CreditsTabFragment());
        fragments.add(new CreditsTabFragment());
        mCreditsFragmentAdapter = new CreditsFragmentAdapter(getFragmentManager(), fragments);

        mViewPager = v.findViewById(R.id.pager);
        mViewPager.setAdapter(mCreditsFragmentAdapter);

        TabLayout tabLayout = v.findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);


        /*TabLayout tabLayout = getActivity().findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Day 0"));
        tabLayout.addTab(tabLayout.newTab().setText("Day 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Day 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Day 3"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);*/

        //setupViewPager();

        return v;
    }

}
