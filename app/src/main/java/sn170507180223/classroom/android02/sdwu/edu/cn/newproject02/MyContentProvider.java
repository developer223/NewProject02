package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase db;
    private static final String MIME_MAINTYPE_DIR="vnd.android.cursor.dir";
    private static final String MIME_MAINTYPE_ITEM="vnd.android.cursor.item";
    private static final String AUTHORITY="com.inspur.android02";
    private static final int SINGLE_STUDENT=1;
    private static final int MULTIPLE_STUDENT=2;
    private static UriMatcher uriMatcher;
    private static final String CONTENT_URI_STRING="content://"+AUTHORITY+"/sudent";
    public static final Uri CONTENT_URI=Uri.parse(CONTENT_URI_STRING);
    //实例化工具类
    static {
        uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);//参数代表当前的匹配器无法匹配时的返回值
        uriMatcher.addURI(AUTHORITY,"student",MULTIPLE_STUDENT);/*CONTENT://COM/INSPUR.ANDROID02/STUDENT*/
        uriMatcher.addURI(AUTHORITY,"student/#",SINGLE_STUDENT);/*CONTENT://COM/INSPUR.ANDROID02/STUDENT/1*/

    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        //返回值：删除了几条
        int count=0;
        switch (uriMatcher.match(uri)){
            case MULTIPLE_STUDENT:
                count=db.delete("student",selection,selectionArgs);
                break;
            case SINGLE_STUDENT:
                String id=uri.getLastPathSegment();
                if(selection!=null&&selection.length()>0){
                    selection+="and id="+id;

                }else{
                    selection=" id="+id;
                }
                count=db.delete("student",selection,selectionArgs);
                break;


        }
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        //返回共享数据的MIMI类型
        //通过uri数据判断请求为单条数据还是多条数据 工具栏：UriMatcher
        int code=uriMatcher.match(uri);
        switch (code){
            case MULTIPLE_STUDENT:
                return MIME_MAINTYPE_DIR+"/student";//vnd.android.cursor.dir/student
             case SINGLE_STUDENT:
                return MIME_MAINTYPE_DIR+"/student";
        }

        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id=db.insert("student",null,values);
        Uri newUri=null;
        if(id>0){
            newUri=ContentUris.withAppendedId(CONTENT_URI,id);
        }
        return newUri;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        myOpenHelper=new MyOpenHelper(this.getContext());
        db=myOpenHelper.getWritableDatabase();
        return true;//初始的结果
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if(uriMatcher.match(uri)==SINGLE_STUDENT){
            String id=uri.getLastPathSegment();
            if(selection!=null&&selection.length()>0){
                selection+="and id="+id;

            }else{
                selection=" id="+id;
            }
        }
        return db.query("student",projection,selection,selectionArgs,null,null,sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        //返回值代表更新记录的数目

        int count=0;
        switch (uriMatcher.match(uri)){
            case MULTIPLE_STUDENT:
                count=db.update("student",values,selection,selectionArgs);
                break;
            case SINGLE_STUDENT:
                String id=uri.getLastPathSegment();
                if(selection!=null&&selection.length()>0){
                    selection+="and id="+id;

                }else{
                    selection=" id="+id;
                }
                count=db.update("student",values,selection,selectionArgs);
        }
        return count;

    }
}
