package com.demo.slidebar.roomdatabsae;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;


/**
 * 数据库操作类
 */
@Dao
public interface ContactDao {
    //使用RxJava2转化类型
    @Query("SELECT * FROM Contact")
    Flowable<List<Contact>> getAll();

    @Query("SELECT * FROM Contact")
    List<Contact> loadAll();

    @Query("SELECT * FROM Contact WHERE name LIKE :name")
    Contact findByName(String name);

    @Insert
    void insertAll(Contact... contacts);

    @Insert
    void insert(Contact contact);

    @Delete
    void delete(Contact user);
}
