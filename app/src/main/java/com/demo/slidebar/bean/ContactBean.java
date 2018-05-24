package com.demo.slidebar.bean;

import android.support.annotation.NonNull;

import com.demo.slidebar.utils.CharacterParser;

import java.io.Serializable;

public class ContactBean implements Serializable, Comparable<ContactBean> {
    private String name;
    private String simplePinYin;
    private String firstPY;

    public ContactBean(String name, String simplePinYin) {
        this.name = name;
        this.simplePinYin = simplePinYin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstPY() {
        return firstPY;
    }

    public void setFirstPY(String firstPY) {
        this.firstPY = firstPY;
    }

    @Override
    public int compareTo(@NonNull ContactBean o) {
        if (o.getFirstPY().equals("#")) {
            return -1;
        } else if (this.getFirstPY().equals("#")) {
            return 1;
        } else {
            return this.getFirstPY().compareTo(o.getFirstPY());
        }
    }

    public void setSortLetters() {
        // 汉字转换成拼音
        String pinyin = CharacterParser.getInstance().getSelling(this.getName());
        String sortString = pinyin.substring(0, 1).toUpperCase();
        // 判断首字母是否是英文字母
        if (sortString.matches("[A-Z]")) {
            this.setFirstPY(sortString);
        } else {
            this.setFirstPY("#");
        }
    }
}
