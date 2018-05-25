package com.demo.slidebar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;

import com.demo.slidebar.roomdatabsae.AppDatabase;
import com.demo.slidebar.roomdatabsae.Contact;
import com.demo.slidebar.roomdatabsae.ContactDao;
import com.demo.slidebar.view.ClearEditText;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements TextWatcher {


    private List<Contact> mContactBeanList;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ContactDao contactDao;
    private AppDatabase database;

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
        mContactBeanList.add(new Contact("Angel", "Angel"));
        mContactBeanList.add(new Contact("保时捷", "baoshijie"));
        mContactBeanList.add(new Contact("符合", "fuhe"));
        mContactBeanList.add(new Contact("方法", "fangfa"));
        mContactBeanList.add(new Contact("大哥", "dage"));
        mContactBeanList.add(new Contact("账号", "zahnghao"));
        mContactBeanList.add(new Contact("何荣", "herong"));
        mContactBeanList.add(new Contact("格格", "gege"));
        mContactBeanList.add(new Contact("阿斯顿", "asd"));
        mContactBeanList.add(new Contact("张三", "zahnghao"));
        mContactBeanList.add(new Contact("何荣", "herong"));
        mContactBeanList.add(new Contact("葛粉", "gefen"));
        mContactBeanList.add(new Contact("阿迪达斯", "adidasi"));

        for (int i = 0; i < mContactBeanList.size(); i++) {
            Contact mContact = mContactBeanList.get(i);
            mContact.setSortLetters();
        }

        //字母排序
        Collections.sort(mContactBeanList);

        //创建数据库
        database = AppDatabase.getDatabase(getApplicationContext());
        contactDao = database.contactDao();
        //判断数据库是否有数据，有就添加
        if (contactDao.loadAll().size() == 0) {
            Disposable subscribe = Flowable.just(mContactBeanList).flatMap(new Function<List<Contact>, Publisher<Contact>>() {
                @Override
                public Publisher<Contact> apply(List<Contact> contacts) {
                    return Flowable.fromIterable(contacts);
                }

            }).subscribeOn(Schedulers.io())
                    .subscribe(new Consumer<Contact>() {
                        @Override
                        public void accept(Contact contact) {
                            contactDao.insert(contact);
                        }
                    });
            compositeDisposable.add(subscribe);
        }


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(final Editable s) {
        if (s.length() > 0) {

            final List<Contact> searchBeanList = new ArrayList<>();
            //获取数据库中的数据做查询数据
            if (contactDao.loadAll().size() > 0) {
                Disposable subscribe = contactDao.getAll().flatMap(new Function<List<Contact>, Publisher<Contact>>() {
                    @Override
                    public Publisher<Contact> apply(List<Contact> contacts) {
                        return Flowable.fromIterable(contacts);
                    }
                }).filter(new Predicate<Contact>() {
                    @Override
                    public boolean test(Contact contact) {
                        return contact.getName().contains(s);
                    }
                }).subscribe(new Consumer<Contact>() {
                    @Override
                    public void accept(Contact contact) {
                        searchBeanList.add(contact);
                    }
                });
                compositeDisposable.add(subscribe);
            }
            //用完数据库后关闭
            database.closeDatabase();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentPanel, SearchFragment.newInstance(searchBeanList)).commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentPanel, ContactFragment.newInstance(mContactBeanList)).commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
        if (database != null) {
            database.closeDatabase();
        }
    }
}
