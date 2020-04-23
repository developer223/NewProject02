package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ch12Activity extends AppCompatActivity {
    private ServiceConnection serviceConnection;
    private MyService2 myService2;
    private boolean binfSucc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch12_1);
        binfSucc=false;
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                //当调用者与服务建立起链接后，会自动调用本方法
                //第二个参数，services中onBand方法的返回值
                MyService2.MyBinder myBinder=(MyService2.MyBinder)iBinder;
                myService2=myBinder.getRandomService();
                binfSucc=true;
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                //当调用者与服务断开起链接后，会自动调用本方法
                binfSucc=false;
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=new Intent(this,MyService2.class);
        //绑定
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        //解绑服务
        unbindService(serviceConnection);
    }

    public void start_service(View view){
        //使用intent服务
        Intent intent=new Intent(this,MyService.class);
        //使用startService以启动方式启动服务
        startService(intent);

    }

    public void stop_service(View view){
        //停止服务
        Intent intent=new Intent(this,MyService.class);
       stopService(intent);
    }

        public void getRandom(View view){
            if(binfSucc){

                int ran=myService2.genRandom();
                Toast.makeText(this,ran+"",Toast.LENGTH_SHORT).show();
            }
        }
}
