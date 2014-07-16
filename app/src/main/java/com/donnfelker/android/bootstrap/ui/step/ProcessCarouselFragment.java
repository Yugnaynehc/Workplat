package com.donnfelker.android.bootstrap.ui.step;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.ui.WorkActivity;
import com.donnfelker.android.bootstrap.util.Ln;
import com.donnfelker.android.bootstrap.util.SafeAsyncTask;
import static com.donnfelker.android.bootstrap.core.Constants.Http.*;


import com.github.kevinsawicki.http.HttpRequest;
import com.viewpagerindicator.TitlePageIndicator;

import java.io.File;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by Feather on 14-4-2.
 * 巡检流程的走马灯框架
 */
public class ProcessCarouselFragment extends Fragment {

    @InjectView(R.id.tpi_header)protected TitlePageIndicator indicator;
    @InjectView(R.id.vp_pages)protected ViewPager pager;
    @InjectView(R.id.prev)BootstrapButton prev;
    @InjectView(R.id.next)BootstrapButton next;

    protected InspectPagerAdapter pagerAdapter;

    protected ValidationFragment currentFragment;
    private SafeAsyncTask<Boolean> uoloadTask;

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
        // 以下这一行代码解决了在平板电脑上ActinBar的menu显示不正常的问题。
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
                Ln.d("prev clicked");
            }
            else if (view.getId() == next.getId()) {
                if (validation()) {
                    currentFragment.saveResult();
                    int index = pager.getCurrentItem();
                    if (index < pagerAdapter.getCount() - 1) {
                        pager.setCurrentItem(index + 1);
                        prev.setBootstrapButtonEnabled(true);
                        if (index == pagerAdapter.getCount() - 2)
                            next.setText(getResources().getString(R.string.button_upload));
                    }
                    // if this is the result list fragment, then upload the result
                    else {
                        // upload task
                        next.setOnClickListener(new View.OnClickListener(){
                            @Override
                            public void onClick(View view){
                                final File resultFile = new File(Environment.getExternalStorageDirectory(),"bbb.xml");
                                final File resultImage = new File(Environment.getExternalStorageDirectory(), "myImage/111.jpg");
                                try {
                                uoloadTask = new SafeAsyncTask<Boolean>() {
                                    @Override
                                    public Boolean call() throws Exception {
                                        //String query = String.format("?%s=%s&%s=%s", );
                                        String xmlExtension = "xml";
                                        String jpgExtension = "jpg";
                                        String query = "";

                                        try {
                                            File[] files = getActivity().getFilesDir().listFiles();
                                            for (File file : files) {
                                                HttpRequest request;
                                                if (file.isFile()) {
                                                    if (file.getPath().substring(file.getPath().length() - xmlExtension.length()).
                                                            equals(xmlExtension)) {
                                                        request = HttpRequest.post(URL_UPLOAD + query);
                                                        request.part("upload", file.getName(), "text/plain", file);
                                                        Ln.d("upload xml");
                                                        if (!request.ok())
                                                            return false;
                                                    }
                                                    else if (file.getPath().substring(file.getPath().length() - jpgExtension.length()).
                                                            equals(jpgExtension)) {
                                                        request = HttpRequest.post(URL_UPLOAD + query);
                                                        request.part("upload", file.getName(), "image/jpeg", file);
                                                        Ln.d("upload jpg");
                                                        if (!request.ok())
                                                            return false;
                                                    }

                                                }
                                            }
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        }
                                        Ln.d("upload success");
                                        return true;
                                    }
                                };
                                uoloadTask.execute();
                                } catch (Exception e) {
                                    Ln.d("upload: %s", e.toString());
                                }
                            }
                        });
                    }
                    //pager.arrowScroll(2);
                    Ln.d("next clicked");
                }
                else
                    Toast.makeText(getActivity(), "请输入完整信息", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
