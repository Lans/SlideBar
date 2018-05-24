package com.demo.slidebar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.demo.slidebar.bean.ContactBean;
import com.demo.slidebar.view.ClearEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TextWatcher {


    private List<ContactBean> mContactBeanList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createData();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.contentPanel, ContactFragment.newInstance(mContactBeanList), "ContactFragment")
                .commit();
        ClearEditText clearEditText = findViewById(R.id.clearEditText);
        clearEditText.addTextChangedListener(this);
    }

    private void createData() {
        mContactBeanList = new ArrayList<>();
        mContactBeanList.add(new ContactBean("Angel", "Angel"));
        mContactBeanList.add(new ContactBean("保时捷", "baoshijie"));
        mContactBeanList.add(new ContactBean("符合", "fuhe"));
        mContactBeanList.add(new ContactBean("方法", "fangfa"));
        mContactBeanList.add(new ContactBean("大哥", "dage"));
        mContactBeanList.add(new ContactBean("账号", "zahnghao"));
        mContactBeanList.add(new ContactBean("何荣", "herong"));
        mContactBeanList.add(new ContactBean("格格", "gege"));
        mContactBeanList.add(new ContactBean("阿斯顿", "asd"));
        mContactBeanList.add(new ContactBean("账号", "zahnghao"));
        mContactBeanList.add(new ContactBean("何荣", "herong"));
        mContactBeanList.add(new ContactBean("格格", "gege"));
        mContactBeanList.add(new ContactBean("阿斯顿", "asd"));

        for (int i = 0; i < mContactBeanList.size(); i++) {
            ContactBean contactBean = mContactBeanList.get(i);
            contactBean.setSortLetters();
        }

        //字母排序
        Collections.sort(mContactBeanList);

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            createData();

            List<ContactBean> searchBeanList = new ArrayList<>();
            for (int i = 0; i < mContactBeanList.size(); i++) {
                ContactBean contactBean = mContactBeanList.get(i);
                if (contactBean.getName().contains(s)) {
                    searchBeanList.add(contactBean);
                }
            }
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentPanel, SearchFragment.newInstance(searchBeanList)).commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentPanel, ContactFragment.newInstance(mContactBeanList)).commit();
        }
    }
}
