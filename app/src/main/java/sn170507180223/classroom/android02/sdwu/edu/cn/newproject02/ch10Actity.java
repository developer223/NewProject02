package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ch10Actity extends AppCompatActivity {
private Integer count;//点击按键的计数器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(ch10Actity.class.toString(),"onCreate");
        setContentView(R.layout.layout_ch10_1);
        count=0;


        //接收数据
       Intent intent=getIntent();//获取界面跳转时的intent
        String text=intent.getStringExtra("text");
        TextView textView=(TextView)findViewById(R.id.ch10_1_tv);
        textView.setText(text);

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

    //计数的方法
    public void counter(View view){
        count++;
        Log.i(ch10Actity.class.toString(),"counter"+count);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //数据保存在bindle对象中，保存一些界面信息
        outState.putInt("counter",count);
        super.onSaveInstanceState(outState);
        Log.i(ch10Actity.class.toString(),"onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //恢复之前保存的状态信息
        count=savedInstanceState.getInt("counter");
        Log.i(ch10Actity.class.toString(),"onRestoreInstanceState");
    }
}
