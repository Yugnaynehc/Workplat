package com.donnfelker.android.bootstrap.ui.step.device;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.util.Ln;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_TYPE_ID;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_ITEM_NO;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.RESULT_ID;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.WORK_STATUS;
import static com.donnfelker.android.bootstrap.core.Constants.FileURI.PICTURE_PATH;

public class ExceptionActivity extends Activity implements AdapterView.OnItemSelectedListener, ViewSwitcher.ViewFactory {

    @InjectView(R.id.submit)protected BootstrapButton submit;
    @InjectView(R.id.camera)protected BootstrapButton camera;
    @InjectView(R.id.cancel)protected BootstrapButton cancel;
    @InjectView(R.id.editText1)protected EditText ed;
    @InjectView(R.id.switcher)protected ImageSwitcher switcher;
    @InjectView(R.id.gallery)protected Gallery gallery;

    private String position;
    private String res;
    private int workStatus;
    private String resultID;
    private String deviceTypeID;
    private int itemNo;
    int picIndex = 0;
    private ArrayList<Drawable> imageList;
    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exception_activity);
        Views.inject(this);
        Intent exIntent = this.getIntent();

        position =  exIntent.getStringExtra("pos");
        res = exIntent.getStringExtra("res");
        workStatus = exIntent.getIntExtra(WORK_STATUS, 0);
        resultID = exIntent.getStringExtra(RESULT_ID);
        deviceTypeID = exIntent.getStringExtra(DEVICE_TYPE_ID);
        itemNo = exIntent.getIntExtra(DEVICE_ITEM_NO, -1);
        initImages();
        switcher.setFactory(this);
        imageAdapter = new ImageAdapter(this);
        gallery.setAdapter(imageAdapter);
        gallery.setOnItemSelectedListener(this);

        if (!res.equals("正常"))
            ed.setText(res);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ed.getText().toString().equals(""))
                    Toast.makeText(ExceptionActivity.this, "请输入异常信息", Toast.LENGTH_LONG).show();
                else {
                    Intent intent = getIntent();
                    intent.putExtra("pos", position);
                    intent.putExtra("result", ed.getText().toString());
                    ExceptionActivity.this.setResult(RESULT_OK, intent);
                    ExceptionActivity.this.finish();
                }
            }
        });
        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String basePath = PICTURE_PATH + resultID + "/";
                String pictureName = deviceTypeID + "_" + itemNo + "_" + picIndex + ".jpg";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(basePath, pictureName)));
                startActivityForResult(intent, 1);
            }
        });
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                ExceptionActivity.this.setResult(RESULT_CANCELED, intent);
                ExceptionActivity.this.finish();
            }
        });

        if (workStatus == 1) {
            ed.setFocusable(false);
            submit.setVisibility(View.INVISIBLE);
            camera.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            String basePath = PICTURE_PATH + resultID + "/";
            String pictureName = deviceTypeID + "_" + itemNo + "_" + picIndex + ".jpg";
            Bitmap bitmap = BitmapFactory.decodeFile(basePath + pictureName);
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
            imageList.add(drawable);
            imageAdapter.notifyDataSetChanged();
            picIndex++;
        }
    }

    private void initImages() {
        imageList = new ArrayList<Drawable>();
        File root = new File(PICTURE_PATH + resultID + "/");
        String prefix = deviceTypeID;
        if (!root.exists())
            root.mkdir();
        for (File file : root.listFiles()) {
            if (file.getName().contains(prefix)) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                imageList.add(drawable);
                picIndex++;
            }
        }
    }

    //创建内部类ImageAdapter
    public class ImageAdapter extends BaseAdapter {
        public ImageAdapter(Context c) {
            mContext = c;
        }
        public int getCount() {
            return imageList.size();
        }
        public Object getItem(int position) {
            return position;
        }
        public long getItemId(int position) {
            return position;
        }
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(mContext);

            i.setImageDrawable(imageList.get(position));
            //设置边界对齐
            i.setAdjustViewBounds(true);
            //设置布局参数
            i.setLayoutParams(new Gallery.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return i;
        }
        private Context mContext;
    }

    //实现onItemSelected()方法，更换图片
    @Override
    public void onItemSelected(AdapterView<?> adapter, View v, int position,
                               long id) {
        //设置图片资源
        switcher.setImageDrawable(imageList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    //实现makeView()方法，为ImageView设置布局格式
    @Override
    public View makeView() {
        ImageView i = new ImageView(this);
        // 设置比例类型
        i.setScaleType(ImageView.ScaleType.CENTER_CROP);
        // 设置布局参数
        i.setLayoutParams(new ImageSwitcher.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return i;
    }
}
