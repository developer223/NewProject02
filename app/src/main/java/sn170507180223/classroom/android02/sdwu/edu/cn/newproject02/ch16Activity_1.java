package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.media.Image;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class ch16Activity_1 extends AppCompatActivity {
    private TextureView textureView;
    private SurfaceTexture surfaceTexture;
    private CameraDevice.StateCallback stateCallback;
    private CameraDevice cameraDevice;
    private  CaptureRequest.Builder captureRequestBuilder;//请求的构造器
    private CaptureRequest previewRequest;
    private CameraCaptureSession cameraCaptureSession;
    private ImageReader imageReader;//接收相机生成的静态图像

    public void takephoto (View view){
        //点击快门，生成静态图像
        if(cameraDevice!=null){
            //使用构造器Builder创建请求
            try {
                CaptureRequest.Builder builder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
                builder.addTarget(imageReader.getSurface());
                //停止连续取景
                cameraCaptureSession.stopRepeating();
                //捕获静态图像
                cameraCaptureSession.capture(builder.build(),new CameraCaptureSession.CaptureCallback(){
                    public void onCaptureCompleted(@NonNull CameraCaptureSession session, @NonNull CaptureRequest request, @NonNull TotalCaptureResult result) {
                        //捕获完成后，回复连续取景
                        try{
                            session.setRepeatingRequest(previewRequest,null,null);
                        }catch(Exception e){
                            Log.e(ch16Activity_1.class.toString(),e.toString());
                        }
                    }
                },null);


            }catch (Exception e){
                Log.e(ch16Activity_1.class.toString(),e.toString());
            }
            }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查相机的使用权限
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            int result = checkSelfPermission(Manifest.permission.CALL_PHONE);
            if(result== PackageManager.PERMISSION_GRANTED){
                setCameraLayout();
            }else {
                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},104);
            }
        }
        //实例化stateCallback，打开相机时执行的方法，便于会话的创建
        stateCallback= new CameraDevice.StateCallback() {
            @Override
            public void onOpened(@NonNull CameraDevice cameraDevice) {
                //摄像头打开后执行本方法，可以获得对象
                ch16Activity_1.this.cameraDevice=cameraDevice;
                //准备预览时使用的组件
                Surface surface=new Surface(surfaceTexture);

                try {
                    //创建一个捕捉请求CaptureRequest
                    captureRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
                    captureRequestBuilder.addTarget(surface);//指定视频输出的位置

                    imageReader=ImageReader.newInstance(1024,768, ImageFormat.JPEG,2);//最大支持两张图片
                    imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
                        @Override
                        public void onImageAvailable(ImageReader imageReader) {
                            //当照片数据可用时，激发该方法
                            //获取捕获的照片数据
                            Image image=imageReader.acquireNextImage();
                            ByteBuffer buffer=image.getPlanes()[0].getBuffer();
                            byte[] bytes=new byte[buffer.remaining()];
                            buffer.get(bytes);


                            //写文件
                            File file=new File(Environment.getExternalStorageDirectory(),"abcd.jpg");
                            FileOutputStream outputStream=null;
                            try{
                                outputStream=new FileOutputStream(file);
                                outputStream.write(bytes);
                                Toast.makeText(ch16Activity_1.this,"save:"+file,Toast.LENGTH_LONG).show();
                            }catch (Exception e){
                                Log.e(ch16Activity_1.class.toString(),e.toString());
                            }finally {
                                try{
                                outputStream.flush();
                                outputStream.close();
                                }catch (Exception e) {
                                    Log.e(ch16Activity_1.class.toString(),e.toString());
                                }
                            }

                        }
                    },null);

                    // /创建一个相机捕捉会话，
                    //参数1代表后续预览或拍照使用的组件
                    //参数2代表的是监听器，创建会话完成后执行的方法
                    cameraDevice.createCaptureSession(Arrays.asList(surface,imageReader.getSurface()), new CameraCaptureSession.StateCallback() {
                        @Override
                        public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                            //会话创建完成后，我们可以再参数中得到会话的对象
                            //开始显示相机的预览
                            ch16Activity_1.this.cameraCaptureSession=cameraCaptureSession;
                            try {
                                previewRequest = captureRequestBuilder.build();
                                cameraCaptureSession.setRepeatingRequest(previewRequest, null, null);//在会话中发出重复请求进行预览
                            }catch (Exception e){
                                Log.i(ch16Activity_1.class.toString(),e.toString());
                            }
                        }

                        @Override
                        public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {

                        }
                    },null);
                }catch (Exception e){
                    Log.i(ch16Activity_1.class.toString(),e.toString());
                }
            }

            @Override
            public void onDisconnected(@NonNull CameraDevice cameraDevice) {
                //摄像头关闭时执行
                ch16Activity_1.this.cameraDevice=null;
            }

            @Override
            public void onError(@NonNull CameraDevice cameraDevice,int i) {

            }
        };

    }
    private void openCamera(int width,int height){
        //使用getSystemService得到相机管理器
        CameraManager cameraManager=(CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            cameraManager.openCamera("0",stateCallback,null);//"0"代表后置摄像头，“1”代表前置的

        }catch (Exception e){
            Log.i(ch16Activity_1.class.toString(),e.toString());
        }
    }
    private void setCameraLayout(){
        //用户授权后，加载界面
        setContentView(R.layout.layout_ch16_1);
        textureView=(TextureView)findViewById(R.id.ch16_tv);
        //当textureView准备好之后，自动调用setSurfaceTextureListener监听器
        textureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                //当textureView可用时，打开摄像头
                ch16Activity_1.this.surfaceTexture=surfaceTexture;//加载到成员里
                openCamera(width,height);

            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

            }
        });
    }
    public void call(View view){
        //判断当前用户的手机版本，是否是6.0之后的
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            // 1.判断当前用户是否已经授权过；
            int result = checkSelfPermission(Manifest.permission.CALL_PHONE);
            if(result== PackageManager.PERMISSION_GRANTED){
                callphone();
            }else {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},101);
            }
        }
    }
    public void chgOri(View view){
        //改变屏幕方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
    }
    public void sms(View view){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            int result = checkSelfPermission(Manifest.permission.SEND_SMS);
            if (result == PackageManager.PERMISSION_GRANTED) {
                sendSms();
            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 102);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==101){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                callphone();
            }
        }
        if(requestCode==102){
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                sendSms();
            }
        }
        if(requestCode==104){//相机授权
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                setCameraLayout();
            }
        }
    }
    private void sendSms(){
        //借助于短信管理器：smsManager进行发送
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage("139669666","11111233652","massage",null,null);//发给谁，谁发出去的，短信内容，两个空
    }

    private void callphone(){
        Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel://139669666"));
        startActivity(intent);
    }
}
