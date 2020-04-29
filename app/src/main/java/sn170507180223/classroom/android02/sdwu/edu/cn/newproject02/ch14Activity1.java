package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ch14Activity1 extends AppCompatActivity {
    private MyOpenHelper myOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch14_1);

        myOpenHelper=new MyOpenHelper(this);
        //以可写方式打开数据库（还有只读方式）,如果数据库不存在，自动创建数据库
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();

        sqLiteDatabase.close();//关闭数据库
    }

    public void insert(View view){
        //以可写方式打开数据库（还有只读方式）,如果数据库不存在，自动创建数据库
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try{
            //将插入的数据放在ContentValues中
            //事物处理
            sqLiteDatabase.beginTransaction();//开启事物
            ContentValues contentValues=new ContentValues();
            contentValues.put("stuname","Tom");
            contentValues.put("stutel","13666666666");
            sqLiteDatabase.insert("student",null,contentValues);
            sqLiteDatabase.setTransactionSuccessful();///所有操作结束后调用本方法，将数据保存在数据库

        }catch (Exception e){
                Log.e(ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();//结束事物
            sqLiteDatabase.close();//关闭数据库
        }

    }
    public void query(View view){
        //以只读方式打开数据库
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getReadableDatabase();
        try{
            //游标类型
          Cursor cursor=sqLiteDatabase.rawQuery("select * from student where stuname=?",new String[]{"Tom"});
            //利用循环遍历游标
            while (cursor.moveToNext()){
               int id=cursor.getInt(cursor.getColumnIndex("id"));
                String stuname=cursor.getString(cursor.getColumnIndex("stuname"));
                String stutel=cursor.getString(cursor.getColumnIndex("stutel"));
                Log.i(ch14Activity1.class.toString(),"id:"+id+",stuname:"+stuname+",stutel:"+stutel);
            }
            cursor.close();//游标关闭
        }catch (Exception e){
            Log.e(ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();//结束事物
            sqLiteDatabase.close();//关闭数据库
        }

    }

    public void delete(View view){
        //以可写方式打开数据库（还有只读方式）,如果数据库不存在，自动创建数据库
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try{
            //将插入的数据放在ContentValues中
            //事物处理
            sqLiteDatabase.beginTransaction();//开启事物

           sqLiteDatabase.delete("student","id=?",new String[]{"1"});
            sqLiteDatabase.setTransactionSuccessful();///所有操作结束后调用本方法，将数据保存在数据库

        }catch (Exception e){
            Log.e(ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();//结束事物
            sqLiteDatabase.close();//关闭数据库
        }
    }

    public void modify(View view){
        //以可写方式打开数据库（还有只读方式）,如果数据库不存在，自动创建数据库
        SQLiteDatabase sqLiteDatabase=myOpenHelper.getWritableDatabase();
        try{
            //将插入的数据放在ContentValues中
            //事物处理
            sqLiteDatabase.beginTransaction();//开启事物

            ContentValues contentValues=new ContentValues();
            contentValues.put("stutel","136999999999");

            sqLiteDatabase.update("student",contentValues,"id=?",new String[]{"2"});
            sqLiteDatabase.setTransactionSuccessful();///所有操作结束后调用本方法，将数据保存在数据库

        }catch (Exception e){
            Log.e(ch14Activity1.class.toString(),e.toString());
        }finally {
            sqLiteDatabase.endTransaction();//结束事物
            sqLiteDatabase.close();//关闭数据库
        }
    }

}
