package com.donnfelker.android.bootstrap.ui.step;



import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.util.Ln;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.InjectView;
import butterknife.Views;

public class ExceptionActivity extends Activity {
    @InjectView(R.id.button1)protected Button button1;
    @InjectView(R.id.button2)protected Button button2;
    @InjectView(R.id.button3)protected Button button3;
    @InjectView(R.id.editText1)protected EditText ed;
    @InjectView(R.id.imageView1)protected ImageView im;
    private String position;
    private String res;
    private String deviceID;
    private static int num;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);
        Views.inject(this);
        Intent exIntent = this.getIntent();



        position =  exIntent.getStringExtra("pos");
        res= exIntent.getStringExtra("res");
        ed.setText(res);
        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                intent.putExtra("pos",position);
                intent.putExtra("result",ed.getText().toString());

                ExceptionActivity.this.setResult(1,intent);
                ExceptionActivity.this.finish();
            }
        });
        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }
        });
        button3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                ExceptionActivity.this.finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        int index;
        index = 0;
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            im = (ImageView) findViewById(R.id.imageView1);
            im.setImageBitmap(bitmap);
            FileOutputStream b = null;
            File file = new File(getFilesDir(), deviceID + "_" + index + ".jpg");
            try {
                b = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                index++;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    b.flush();
                    b.close();
                    Ln.d("save picture: %s", file.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
