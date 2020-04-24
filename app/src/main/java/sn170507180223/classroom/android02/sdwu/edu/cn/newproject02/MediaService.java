package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MediaService extends Service {
    private MediaPlayer mediaPlayer;//媒体播放器
    private MyBinder myBinder;
    public MediaService() {
    }

    @Override
    public void onCreate() {
        //初始化
        super.onCreate();
        mediaPlayer=MediaPlayer.create(this,R.raw.wav);
        mediaPlayer.setLooping(false);//不循环
        myBinder=new MyBinder();
        Log.i(MediaService.class.toString(),"onCreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(MediaService.class.toString(),"onStartCommand");
        //从intent中获取用户需要的操作，然后进一步处理
        String state=intent.getStringExtra("PlayerState");
        if(state!=null){
            if(state.equals("START")){//播放
                start();
            }
            if(state.equals("PAUSE")){//暂停
                pause();
            }
            if(state.equals("STOP")){//停止
                stop();
            }
            if(state.equals("STOPSERVICE")){//停止服务
                stopSelf();//自带，自己停止自己
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
    public void start(){
        mediaPlayer.start();

    }
    public void pause(){
        //先判断播放器是否在播放
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }

    }

    public void stop(){
        mediaPlayer.stop();
        //为了下一次播放做准备：prepare方法
        try {
            mediaPlayer.prepare();
        }catch (Exception e){
            Log.e(MediaService.class.toString(),e.toString());
        }

    }

    @Override
    public void onDestroy() {
        Log.i(MediaService.class.toString(),"onDestory");
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(MediaService.class.toString(),"onBind");
       return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(MediaService.class.toString(),"onUnbind");
        return super.onUnbind(intent);
    }

    public class MyBinder extends Binder{
        public MediaService getMediaService(){
            return MediaService.this;

        }


    }
}
