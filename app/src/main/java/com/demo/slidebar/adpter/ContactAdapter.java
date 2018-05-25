package com.demo.slidebar.adpter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.slidebar.R;
import com.demo.slidebar.roomdatabsae.Contact;

import java.util.List;

public class ContactAdapter extends BaseQuickAdapter<Contact, BaseViewHolder> {

    private List<Contact> mData;

    public ContactAdapter(@Nullable List<Contact> data) {
        super(R.layout.contact_item, data);
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, Contact item) {

        TextView headerText = helper.getView(R.id.headerText);

        int layoutPosition = helper.getLayoutPosition();
        int section = item.getFirstPY().charAt(0);

        if (layoutPosition == getPositionForSection(section)) {
            headerText.setVisibility(View.VISIBLE);
            headerText.setText(item.getFirstPY());
        } else {
            headerText.setVisibility(View.GONE);
        }
        helper.setText(R.id.name, item.getName());


    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < mData.size(); i++) {
            String sortStr = mData.get(i).getFirstPY();
            if (sortStr != null) {
                char firstChar = sortStr.toUpperCase().charAt(0);
                if (firstChar == section) {
                    return i;
                }
            }
        }
        return -1;
    }
}
