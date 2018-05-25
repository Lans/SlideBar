package com.demo.slidebar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.slidebar.adpter.SearchAdapter;
import com.demo.slidebar.bean.ContactBean;
import com.demo.slidebar.roomdatabsae.Contact;

import java.io.Serializable;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private List<Contact> mContactBeanList;

    public SearchFragment() {
    }

    public static SearchFragment newInstance(List<Contact> mContactBeanList) {
        Bundle args = new Bundle();
        args.putSerializable("ContactList", (Serializable) mContactBeanList);
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContactBeanList = (List<Contact>) getArguments().getSerializable("ContactList");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_search, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View mView) {
        RecyclerView recyclerView = mView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SearchAdapter mContactAdapter = new SearchAdapter(mContactBeanList);
        recyclerView.setAdapter(mContactAdapter);
    }

}
