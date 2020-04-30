package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by user on 2020/4/29.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    private String STUDENT_TB_SQL="create table student(id integer primary key autoincrement,stuname text,stutel text)";
    public MyOpenHelper(Context context){
        //Context context上下文 ，String name数据库名字,
        // CursorFactory factory传入Null, int version数据库的版本
        super(context,"stud.db",null,2);//创建数据库,原来版本号为1，后升级为2
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //当打开数据库时，发现数据库并不存在，此时会自动创建数据库，然后调用o nCreate 方法
        //在本方法中完成数据库对象（表，视图，索引等）的创建
        //通过SQLiteDatabase对象，完成SQL的执行
        sqLiteDatabase.execSQL(STUDENT_TB_SQL);
        Log.i(MyOpenHelper.class.toString(),"onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //升级时自动调用
        //当构造方法中指定的版本号，与手机中已有的数据库版本号更新的时候，调用本方法
        sqLiteDatabase.execSQL("alter table student add colum stuadd text");
    }
}
