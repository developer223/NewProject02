package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ch10Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch10_2);
    }
    public void send_broadcast(View view){
        //发送广播
        Intent intent=new Intent("com.inspur.broadcast");//制定频道
        intent.putExtra("key1","message");

        sendBroadcast(intent);//发送

    }

    public void ch10Actity(View view){
        Intent intent=new Intent(this,ch10Actity.class);
        EditText editText=(EditText)findViewById(R.id.ch10_2_et);
        intent.putExtra("text",editText.getText().toString());//设置传递的数据
        startActivity(intent);

    }

    public void startSubActity(View view){
        //以sub_actity的方式启动子actity
        Intent intent=new Intent(this,ch10Activity_3.class);
        startActivityForResult(intent,101);

    }


    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        //3.在父actity中获取返回值
        //requeCode用来区分从哪一个子actity中返回的结果
        if(requestCode==101){
            if(resultCode==RESULT_OK){
                //用户点确认，进一步获取数据
                String name=data.getStringExtra("name");
                Toast.makeText(this,name,Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(this,"cancle",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void web(View view){
        //使用隐式启动方法打开网页
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://baidu.com"));
        startActivity(intent);

    }

}
