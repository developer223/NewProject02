package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {
    private MediaPlayer mediaPlayer;
    public MyService() {
    }
public void onCreate(){
    super.onCreate();
    //初始化
    mediaPlayer=MediaPlayer.create(this,R.raw.wav);
    mediaPlayer.setLooping(true);
    Log.i(MyService.class.toString(),"onCreate");
}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //主要业务
        mediaPlayer.start();
        Log.i(MyService.class.toString(),"onStartCommand");
        return super.onStartCommand(intent, flags, startId);

    }


    @Override
    public void onDestroy() {

        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
        Log.i(MyService.class.toString(),"onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
