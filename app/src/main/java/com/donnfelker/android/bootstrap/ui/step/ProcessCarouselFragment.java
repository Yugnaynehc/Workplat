package com.donnfelker.android.bootstrap.ui.step;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.ui.WorkActivity;
import com.donnfelker.android.bootstrap.util.Ln;
import com.donnfelker.android.bootstrap.util.MyViewPager;
import com.donnfelker.android.bootstrap.util.SafeAsyncTask;
import static com.donnfelker.android.bootstrap.core.Constants.Http.*;
import static com.donnfelker.android.bootstrap.core.Constants.UPreference.*;
import static com.donnfelker.android.bootstrap.core.Constants.Intent.*;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.*;

import com.github.kevinsawicki.http.HttpRequest;
import com.viewpagerindicator.TitlePageIndicator;

import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.io.FileUtils;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by Feather on 14-4-2.
 * 巡检流程的走马灯框架
 */
public class ProcessCarouselFragment extends Fragment {

    @InjectView(R.id.tpi_header)protected TitlePageIndicator indicator;
    @InjectView(R.id.vp_pages)protected MyViewPager pager;
    @InjectView(R.id.prev)BootstrapButton prev;
    @InjectView(R.id.next)BootstrapButton next;

    protected InspectPagerAdapter pagerAdapter;

    protected ValidationFragment currentFragment;
    private SafeAsyncTask<Boolean> uploadTask;

    private ProgressDialog progressDialog;
    private Handler handler;        // 用handler来更新主线程UI

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //更新UI,关闭ProgressDialog
                progressDialog.dismiss();
            }};
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel_process, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Views.inject(this, getView());
        pagerAdapter = new InspectPagerAdapter(getResources(), getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        indicator.setViewPager(pager);
        pager.setCurrentItem(0);
        // 以下这一行代码解决了在平板电脑上ActionBar的menu显示不正常的问题。
        // 可以在https://code.google.com/p/android/issues/detail?id=29472中找到详细讨论
        // Thanks!
        pager.setOffscreenPageLimit(3);
        prev.setOnClickListener(new MyOnClickListener());
        next.setOnClickListener(new MyOnClickListener());
        prev.setBootstrapButtonEnabled(false);
    }

    private boolean validation() {
        currentFragment = (ValidationFragment)pagerAdapter.instantiateItem(pager, pager.getCurrentItem());
        return currentFragment.validation();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == prev.getId()) {
                int index = pager.getCurrentItem();
                if (index > 0) {
                    pager.setCurrentItem(index - 1);
                    next.setBootstrapButtonEnabled(true);
                    next.setText(getResources().getString(R.string.button_next));
                }
                if (pager.getCurrentItem() == 0)
                    prev.setBootstrapButtonEnabled(false);
                //pager.arrowScroll(1);
                next.setOnClickListener(this);
               // Ln.d("prev clicked");
            }
            else if (view.getId() == next.getId()) {
                if (validation()) {
                    currentFragment.saveResult();
                    int index = pager.getCurrentItem();
                    if (index < pagerAdapter.getCount() - 1) {
                        pager.setCurrentItem(index + 1);
                        prev.setBootstrapButtonEnabled(true);
                    }
                    if (pager.getCurrentItem()  == pagerAdapter.getCount() - 1) {
                        next.setText(getResources().getString(R.string.button_upload));
                        next.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                try {
                                    Resources resources = getActivity().getResources();
                                    progressDialog = ProgressDialog.show(getActivity(),
                                            resources.getString(R.string.upload_try),
                                            resources.getString(R.string.upload_wait),
                                            true, false);
                                    uploadTask = new UploadTask();
                                    uploadTask.execute();
                                } catch (Exception e) {
                                    Ln.d("upload: %s", e.toString());
                                }
                            }
                        });
                    }
                    //pager.arrowScroll(2);
                    // Ln.d("next clicked");
                }
                else
                    Toast.makeText(getActivity(), "请输入完整信息", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UploadTask extends SafeAsyncTask<Boolean> {

        @Override
        public Boolean call() throws Exception {

            String resultId = ((WorkActivity)getActivity()).getResult().getResultid();
            String query = String.format("?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
                    "username", getActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).getString(USER_INFO_NAME, ""),
                    "substationid", ((WorkActivity)getActivity()).getWork().getSubstation(),
                    "resultid", resultId,
                    "type", ((WorkActivity)getActivity()).getWork().getType(),
                    "planid", ((WorkActivity)getActivity()).getWork().getPlanid());
            String xmlExtension = "xml";
            String jpgExtension = "jpg";
            Ln.d("upload result url: %s", URL_UPLOAD + query);
            File xmlResult = new File(getActivity().getFilesDir(), "result_" + resultId + ".xml");
            FileOutputStream fos = new FileOutputStream(xmlResult);
            try {
                File[] files = getActivity().getFilesDir().listFiles();
                for (File file : files) {
                    if (file.isFile() && !file.getName().contains("result")) {
                        if (file.getPath().substring(file.getPath().length() - xmlExtension.length()).
                                equals(xmlExtension)) {
                            // TODO construct xml result file
                            byte[] bytes = FileUtils.readFileToByteArray(file);
                            fos.write(bytes);
                            fos.flush();
                        }
                        else if (file.getPath().substring(file.getPath().length() - jpgExtension.length()).
                                equals(jpgExtension)) {
                            HttpRequest jpgrequest = HttpRequest.post(URL_UPLOAD + query);
                            jpgrequest.part("upload", file.getName(), "image/jpeg", file);
                            Ln.d("upload result jpg");
                            if (!jpgrequest.ok())
                                return false;
                        }

                    }
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            fos.flush();
            fos.close();
            HttpRequest xmlrequest = HttpRequest.post(URL_UPLOAD + query);
            xmlrequest.acceptCharset("UTF-8");
            xmlrequest.part("upload", xmlResult.getName(), "text/plain", xmlResult);
            Ln.d("upload result location: %s", xmlrequest.location());
            Ln.d("upload result body: %s", xmlrequest.body());
            Ln.d("upload result contentEncoding: %s", xmlrequest.contentEncoding());
            Ln.d("upload result message: %s", xmlrequest.message());
            //Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_LONG).show();
            ((WorkActivity)getActivity()).getWork().setStage("已完成");
            Intent intent = new Intent();
            intent.putExtra(WORK_ID, ((WorkActivity)getActivity()).getWork().getPlanid());
            getActivity().setResult(FINISH_WORK, intent);
            getActivity().finish();
            return xmlrequest.ok();
        }

        @Override
        public void onSuccess(final Boolean authSuccess) {
            // delete result files 删除结果文件
            try {
                File[] files = getActivity().getFilesDir().listFiles();
                for (File file : files) {
                    if (!file.getName().contains("result"))
                        FileUtils.forceDelete(file);
                }
                Ln.d("upload result clean");
                Ln.d("upload result success");
            } catch (Exception e) {
                e.printStackTrace();
            }
            ProcessCarouselFragment.this.handler.sendEmptyMessage(0);
        }

        @Override
        protected void onFinally() throws RuntimeException {

        }
    }
}
