package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//子actity
public class ch10Activity_3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch10_3);
    }
    public void ok(View view){
        //设置子actity返回值
        EditText editText=(EditText)findViewById(R.id.ch10_3_tv);
        String content=editText.getText().toString();
        //将数据放在intent里
        Intent intent=new Intent();
        intent.putExtra("name",content);
        setResult(RESULT_OK);//设置返回值
    }
    public void cancle(View view){

        setResult(RESULT_CANCELED);//取消
        finish();//关闭当前界面
    }

}
