package sn170507180223.classroom.android02.sdwu.edu.cn.newproject02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.FileOutputStream;

public class ch13Activity_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ch13_1);
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
}
