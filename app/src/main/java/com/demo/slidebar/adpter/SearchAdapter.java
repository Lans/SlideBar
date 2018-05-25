package com.demo.slidebar.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.slidebar.R;
import com.demo.slidebar.roomdatabsae.Contact;

import java.util.List;

public class SearchAdapter extends BaseQuickAdapter<Contact, BaseViewHolder> {
    public SearchAdapter(@Nullable List<Contact> data) {
        super(R.layout.contact_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Contact item) {
        helper.setText(R.id.name, item.getName());
    }
}
