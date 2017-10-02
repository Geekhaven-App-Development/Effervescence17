package org.effervescence.app17.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.effervescence.app17.R;
import org.effervescence.app17.recyclerview.adapters.CreditsRVAdapter;

public class CreditsTabFragment extends Fragment {
    RecyclerView mRecyclerScheduler;

    public CreditsTabFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_credits_tab, container, false);
        mRecyclerScheduler = (RecyclerView) v.findViewById(R.id.recycler_scheduler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        CreditsRVAdapter creditsRecyclerViewAdapter = new CreditsRVAdapter(getContext(), EventsList.eventsList);

        mRecyclerScheduler.setLayoutManager(layoutManager);
        mRecyclerScheduler.setAdapter(creditsRecyclerViewAdapter);

        return v;
    }
}
