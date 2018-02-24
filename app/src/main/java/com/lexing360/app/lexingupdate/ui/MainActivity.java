package com.lexing360.app.lexingupdate.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lexing360.app.lexingupdate.Api;
import com.lexing360.app.lexingupdate.R;
import com.lexing360.app.lexingupdate.adapter.MainListAdapter;
import com.lexing360.app.lexingupdate.base.BaseBindingActivity;
import com.lexing360.app.lexingupdate.base.BaseBindingAdapter;
import com.lexing360.app.lexingupdate.base.RouterConstants;
import com.lexing360.app.lexingupdate.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Route(path = RouterConstants.MAIN)
public class MainActivity extends BaseBindingActivity<ActivityMainBinding> {

    ArrayList<String> list;
    @Override
    protected void bindData(ActivityMainBinding binding, Bundle savedInstanceState) {
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));
        MainListAdapter adapter = new MainListAdapter(this);
        binding.rvList.setAdapter(adapter);
        initListData(adapter);
        initListener(adapter);
    }

    private void initListData(MainListAdapter adapter) {
        list = new ArrayList<>();
        list.add("版本升级");
        list.add("更新布局");
        adapter.addAll(list);
    }

    private void initListener(MainListAdapter adapter) {
        adapter.setOnItemClickListener(new BaseBindingAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(String data) {
                if (data.equals("版本升级")) {
                    ARouter.getInstance()
                            .build(RouterConstants.UPDATA)
                            .navigation();
                    return;
                }
                if (data.equals("更新布局")) {
                    ARouter.getInstance()
                            .build(RouterConstants.LAYOUT)
                            .navigation();
                    return;
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

}