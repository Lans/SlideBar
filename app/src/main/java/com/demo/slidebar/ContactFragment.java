package com.demo.slidebar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.slidebar.adpter.ContactAdapter;
import com.demo.slidebar.bean.ContactBean;
import com.demo.slidebar.roomdatabsae.Contact;
import com.demo.slidebar.view.SlideBar;

import java.io.Serializable;
import java.util.List;


/**
 */
public class ContactFragment extends Fragment implements SlideBar.OnTouchChangeListener {
    private TextView hitText;
    private RecyclerView recyclerView;
    private List<Contact> mContactBeanList;
    private ContactAdapter mContactAdapter;
    private LinearLayoutManager layoutManager;

    public ContactFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static ContactFragment newInstance(List<Contact> mContactBeanList) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putSerializable("ContactList", (Serializable) mContactBeanList);
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
        View mView = inflater.inflate(R.layout.fragment_contact, container, false);
        initView(mView);
        return mView;
    }

    private void initView(View mView) {
        hitText = mView.findViewById(R.id.hitText);
        SlideBar slideBar = mView.findViewById(R.id.slideBar);
        recyclerView = mView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mContactAdapter = new ContactAdapter(mContactBeanList);
        recyclerView.setAdapter(mContactAdapter);
        slideBar.setOnTouchChangeListener(this);
    }

    @Override
    public void onLetterTouched(String touchLetter) {
        hitText.setVisibility(View.VISIBLE);
        hitText.setText(touchLetter);

        //滑动到第一个对应字母开头的联系人
        if ("A".equalsIgnoreCase(touchLetter)) {
            recyclerView.scrollToPosition(0);
        } else {
            // 该字母首次出现的位置
            int position = mContactAdapter.getPositionForSection(touchLetter.charAt(0));
            if (position != -1) {
                layoutManager.scrollToPositionWithOffset(position, 0);
            }
        }
    }

    @Override
    public void onLetterTouchedCancel() {
        hitText.setVisibility(View.GONE);

    }
}
