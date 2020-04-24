package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ch13Activity_1 extends AppCompatActivity {
    private EditText ip;
    private EditText port;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch13_1);

        ip=(EditText)findViewById(R.id.ch13_1_ip);
        port=(EditText)findViewById(R.id.ch13_1_port);

        SharedPreferences sharedPreferencesCompat=getSharedPreferences("prefs",MODE_PRIVATE);
        ip.setText(sharedPreferencesCompat.getString("ip",""));
        port.setText(sharedPreferencesCompat.getString("port","" ));



    }
    private void write(View v){
        EditText editText=(EditText)findViewById(R.id.ch13_1_et);
        String content=editText.getText().toString();
        try {
            FileOutputStream fileOutputStream = openFileOutput("android02.txt", MODE_PRIVATE);

            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        }catch (Exception e){
            Log.e(ch13Activity_1.class.toString(),e.toString());
        }
    }
    public void read(View view){
        try{
            FileInputStream fileInputStream=openFileInput("android01.txt");
            int size=fileInputStream.available();
            byte[] bytes=new byte[size];
            fileInputStream.read(bytes);
            String content=new String(bytes);

            fileInputStream.close();
            Toast.makeText(this,content,Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.e(ch13Activity_1.class.toString(),e.toString());
        }
    }
    public void saveSharePref(View view){
        SharedPreferences sharedPreferencesCompat=getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferencesCompat.edit();
        editor.putString("ip",ip.getText().toString());
        editor.putString("port",port.getText().toString());
        editor.commit();
    }
}
