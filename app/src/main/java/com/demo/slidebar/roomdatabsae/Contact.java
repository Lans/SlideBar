package com.demo.slidebar.roomdatabsae;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.demo.slidebar.utils.CharacterParser;

import java.io.Serializable;

//定义表实体类
@Entity(tableName = "Contact")
public class Contact implements Serializable, Comparable<Contact> {

    //主键 和自增长
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "firstPY")
    private String firstPY;

    private String simplePinYin;

    public Contact(String name, String simplePinYin) {
        this.name = name;
        this.simplePinYin = simplePinYin;
    }

    @Override
    public int compareTo(@NonNull Contact o) {
        if (o.getFirstPY().equals("#")) {
            return -1;
        } else if (this.getFirstPY().equals("#")) {
            return 1;
        } else {
            return this.getFirstPY().compareTo(o.getFirstPY());
        }
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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

    public String getSimplePinYin() {
        return simplePinYin;
    }

    public void setSimplePinYin(String simplePinYin) {
        this.simplePinYin = simplePinYin;
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
