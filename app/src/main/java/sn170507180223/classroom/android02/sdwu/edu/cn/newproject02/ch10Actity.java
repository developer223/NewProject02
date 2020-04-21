package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ch10Actity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ch10Actity.class.toString(),"onCreate");
        setContentView(R.layout.layout_ch10_1);
    }
public  void finishClick(View view){
    finish();//关闭界面
}

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ch10Actity.class.toString(),"onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ch10Actity.class.toString(),"onStop");

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(ch10Actity.class.toString(),"onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(ch10Actity.class.toString(),"onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ch10Actity.class.toString(),"onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
