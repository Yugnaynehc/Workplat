package com.donnfelker.android.bootstrap.ui.step.device;


import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.util.Ln;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.InjectView;
import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_TYPE_ID;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_ITEM_NO;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.RESULT_ID;

public class ExceptionActivity extends Activity {
    @InjectView(R.id.button1)protected BootstrapButton button1;
    @InjectView(R.id.button2)protected BootstrapButton button2;
    @InjectView(R.id.button3)protected BootstrapButton button3;
    @InjectView(R.id.editText1)protected EditText ed;
    @InjectView(R.id.imageView1)protected ImageView im;
    private String position;
    private String res;
    private String resultID;
    private String deviceTypeID;
    private int itemNo;
    int picIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exception_activity);
        Views.inject(this);
        Intent exIntent = this.getIntent();

        position =  exIntent.getStringExtra("pos");
        res= exIntent.getStringExtra("res");
        resultID = exIntent.getStringExtra(RESULT_ID);
        deviceTypeID = exIntent.getStringExtra(DEVICE_TYPE_ID);
        itemNo = exIntent.getIntExtra(DEVICE_ITEM_NO, -1);

        if (!res.equals("正常"))
            ed.setText(res);
        button1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (ed.getText().toString().equals(""))
                    Toast.makeText(ExceptionActivity.this, "请输入异常信息", Toast.LENGTH_LONG).show();
                else {
                    Intent intent = getIntent();
                    intent.putExtra("pos", position);
                    intent.putExtra("result", ed.getText().toString());
                    ExceptionActivity.this.setResult(RESULT_OK,intent);
                    ExceptionActivity.this.finish();
                }
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
                Intent intent = getIntent();
                ExceptionActivity.this.setResult(RESULT_CANCELED, intent);
                ExceptionActivity.this.finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            im = (ImageView) findViewById(R.id.imageView1);
            im.setImageBitmap(bitmap);
            FileOutputStream b = null;
            File file = new File(getFilesDir(), resultID + "_" + deviceTypeID + "_" + itemNo + "_" + picIndex + ".jpg");
            try {
                b = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
                picIndex++;
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


}
