package com.demo.slidebar.roomdatabsae;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;


/**
 * entities:数据库实体类
 * version:数据库版本
 *
 * 若果报错
 *          Error:(22, 17) 警告: Schema export directory is not provided to the annotation processor so we cannot export the schema.
 *
 *          You can either provide `room.schemaLocation` annotation processor argument OR set exportSchema to false.
 *
 *          exportSchema：默认为true
 * 需配置
 *
 * 如果为true ,需要在build.gradle中配置
 *       android {
 *               compileSdkVersion 26
 *               buildToolsVersion "26.0.2"
 *               defaultConfig {
 *               applicationId "xxx"
 *               minSdkVersion 15
 *               targetSdkVersion 26
 *               versionCode 1
 *               versionName "1.0"
 *               testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
 *
 *              //指定room.schemaLocation生成的文件路径，会生成一个表结构json
 *              javaCompileOptions {
 *              annotationProcessorOptions {
 *                 arguments = ["room.schemaLocation": "$projectDir/schemas".toString()]
 *             }
 *         }
 *     }
 * 如果为false 一切都ok
 */
@Database(entities = {Contact.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();

    private static AppDatabase INSTANCE;

    //获取数据库单例
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database_contact")
                            //允许在在主线程查询(一般数据库操作放在子线程)
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    //做数据库迁移用 ，具体百度
    private static final Migration MIGRATION_1_3 = new Migration(1, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }
    };

    //操作完数据关闭
    public void closeDatabase() {
        INSTANCE = null;
    }
}
