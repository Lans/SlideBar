package com.demo.slidebar.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.slidebar.R;
import com.demo.slidebar.bean.ContactBean;

import java.util.List;

public class SearchAdapter extends BaseQuickAdapter<ContactBean, BaseViewHolder> {
    public SearchAdapter(@Nullable List<ContactBean> data) {
        super(R.layout.contact_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactBean item) {
        helper.setText(R.id.name, item.getName());
    }
}
