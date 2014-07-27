package com.donnfelker.android.bootstrap.ui.step;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.donnfelker.android.bootstrap.core.Work;
import com.donnfelker.android.bootstrap.core.inspect.result.Result;
import com.donnfelker.android.bootstrap.ui.WorkActivity;
import com.donnfelker.android.bootstrap.util.Ln;
import com.donnfelker.android.bootstrap.util.MyTitlePageIndicator;
import com.donnfelker.android.bootstrap.util.MyViewPager;
import com.donnfelker.android.bootstrap.util.ResultXmlBuilder;
import com.donnfelker.android.bootstrap.util.SafeAsyncTask;
import static com.donnfelker.android.bootstrap.core.Constants.Http.*;
import static com.donnfelker.android.bootstrap.core.Constants.UPreference.*;
import static com.donnfelker.android.bootstrap.core.Constants.FileURI.PICTURE_PATH;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.wishlist.Toaster;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLEncoder;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by Feather on 14-4-2.
 * 巡检流程的走马灯框架
 */
public class ProcessCarouselFragment extends Fragment {

    @InjectView(R.id.tpi_header)protected MyTitlePageIndicator indicator;
    @InjectView(R.id.vp_pages)protected MyViewPager pager;
    @InjectView(R.id.prev)BootstrapButton prev;
    @InjectView(R.id.next)BootstrapButton next;

    protected InspectPagerAdapter pagerAdapter;

    protected ValidationFragment currentFragment;
    private SafeAsyncTask<Boolean> uploadTask;

    private Result result;
    private Work work;
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
        work = ((WorkActivity)getActivity()).getWork();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_carousel_process, container, false);
        Views.inject(this, view);

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

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(R.string.upload_try);
        progressDialog.setMessage(getString(R.string.upload_wait));
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (uploadTask != null)
                    uploadTask.cancel(true);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
                next.setOnClickListener(this);
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
                        if (work.getStatus() == 0) {
                            next.setText(getResources().getString(R.string.button_upload));
                            next.setOnClickListener(new OnSubmitClickListener());
                        } else {
                            next.setText(getResources().getString(R.string.button_close));
                            next.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getActivity().finish();
                                }
                            });
                        }
                    }
                }
                else
                    Toast.makeText(getActivity(), getString(R.string.error_blank), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class OnSubmitClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            try {
                result = ((WorkActivity)getActivity()).getResult();
                if (result.getDeviceResults() == null) {
                    Toaster.showLong(getActivity(), R.string.error_no_device_result);
                    return;
                }
                progressDialog.show();
                uploadTask = new UploadTask();
                uploadTask.execute();
            } catch (Exception e) {
                e.printStackTrace();
                Ln.d("upload: %s", e.toString());
            }
        }
    }

    private class UploadTask extends SafeAsyncTask<Boolean> {

        public Boolean call() throws Exception {


            String resultId = result.getResultid();
            String type = ((WorkActivity)getActivity()).getWork().getType();
            String query = String.format("?%s=%s&%s=%s&%s=%s&%s=%s&%s=%s",
                    "username", getActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).getString(USER_INFO_NAME, ""),
                    "substationid", ((WorkActivity)getActivity()).getWork().getSubstation(),
                    "resultid", resultId,
                    "type", URLEncoder.encode(URLEncoder.encode(type, "UTF-8"), "UTF-8"),
                    "planid", ((WorkActivity)getActivity()).getWork().getPlanid());

            Ln.d("upload result url: %s", URL_UPLOAD + query);

            // build result xml
            File xmlResult = new File(getActivity().getFilesDir(), "result_" + resultId + ".xml");
            FileOutputStream fos = new FileOutputStream(xmlResult);
            ResultXmlBuilder.Build(result, fos);
            fos.flush();
            fos.close();

            // upload images 先上传图片信息
            String prefix = resultId + "_";
            File root = new File(PICTURE_PATH);
            for (File file : root.listFiles()) {
                if (file.getName().contains(prefix)) {
                    HttpRequest request = HttpRequest.post(URL_UPLOAD + query);
                    request.part("upload", file.getName(), "image/jpeg", file);
                    Ln.d("upload result jpg");
                    if (!request.ok())
                        return false;
                }
            }

            HttpRequest request = HttpRequest.post(URL_UPLOAD + query);
            request.part("upload", xmlResult.getName(), "text/plain", xmlResult);
            Ln.d("upload result code: %s", request.code());
            return request.ok();
        }

        @Override
        public void onSuccess(final Boolean uploadSuccess) {
            // delete result files 删除结果文件
            if (uploadSuccess) {
                Toast.makeText(getActivity(), getString(R.string.upload_success), Toast.LENGTH_LONG).show();
                getActivity().finish();
                /*
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
                */
            }
            else
                Toast.makeText(getActivity(), getString(R.string.upload_failed), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onException(final Exception e) throws RuntimeException {
            Toaster.showLong(getActivity(), getString(R.string.upload_error) + "\n" + e.getMessage());
        }

        @Override
        protected void onInterrupted(Exception e) {
            Toast.makeText(getActivity(), getString(R.string.upload_cancel), Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onFinally() throws RuntimeException {
            ProcessCarouselFragment.this.handler.sendEmptyMessage(0);
        }
    }

}
