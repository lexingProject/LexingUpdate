package com.lexing360.app.lexingupdate.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lexing360.app.lexingupdate.Api;
import com.lexing360.app.lexingupdate.R;
import com.lexing360.app.lexingupdate.UpDataSubscriber;
import com.lexing360.app.lexingupdate.base.BaseBindingActivity;
import com.lexing360.app.lexingupdate.base.RouterConstants;
import com.lexing360.app.lexingupdate.databinding.ActivityUpdataBinding;
import com.lexing360.app.lexingupdate.model.JwtModel;
import com.lexing360.app.lexingupdate.model.UpDatePutModel;
import com.lexing360.app.lexingupdate.model.UpDateModel;
import com.lexing360.app.lexingupdate.model.UpDatePutResponseModel;
import com.lexing360.app.lexingupdate.utils.SPUtil;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Route(path = RouterConstants.UPDATA)
public class UpdataActivity extends BaseBindingActivity<ActivityUpdataBinding> implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    String[] channelArr = {"app", "product", "productTest", "dev", "staging", "stagingTest"};

    String mAccount;
    String mPassword;
    String mCurrrentVersion;
    String mUpdateVersion;
    String mChannel = "app";
    String jwt;
    String mUpdateApkUrl;
    public Api.ApiServices apiServices;

    @Override
    protected void bindData(ActivityUpdataBinding binding, Bundle savedInstanceState) {
        initView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_updata;
    }

    private void initView() {
        binding.etAccount.setText(SPUtil.getString(this, SPUtil.ACCOUNT));
        binding.etPassword.setText(SPUtil.getString(this, SPUtil.PASSWORD));   //默认初始密码
        binding.etCurrrentVersion.setText(SPUtil.getString(this, SPUtil.CURRENT_VERSION));
        binding.etUpdateApkUrl.setText(SPUtil.getString(this, SPUtil.UPDATE_APK_URL));
        binding.etUpdateVersion.setText(SPUtil.getString(this, SPUtil.UPDATE_VERSION));
        binding.btUpDate.setOnClickListener(this);
        binding.rgChannelInner.setOnCheckedChangeListener(this);
        binding.rgChannelOut.setOnCheckedChangeListener(this);

    }

    private void setViewValue() {
        mAccount = binding.etAccount.getText().toString();
        mPassword = binding.etPassword.getText().toString();
        mCurrrentVersion = binding.etCurrrentVersion.getText().toString();
        mUpdateVersion = binding.etUpdateVersion.getText().toString();
        mUpdateApkUrl = binding.etUpdateApkUrl.getText().toString();
        //存值
        SPUtil.putString(this, SPUtil.ACCOUNT, mAccount);
        SPUtil.putString(this, SPUtil.PASSWORD, mPassword);
        SPUtil.putString(this, SPUtil.CURRENT_VERSION, mCurrrentVersion);
        SPUtil.putString(this, SPUtil.UPDATE_VERSION, mUpdateVersion);
        SPUtil.putString(this, SPUtil.CHANNEL, mChannel);
        SPUtil.putString(this, SPUtil.UPDATE_APK_URL, mUpdateApkUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_upDate:
                upDate();
                break;
            default:
                break;
        }
    }

    //点击进行升级
    public void upDate() {
        if (jwt == null) {
            //Toast.makeText(this,"请先获取jwt",Toast.LENGTH_SHORT).show();
            getJwt();
        }
        setViewValue();
        getUpdateUrl();
    }

    public void getJwt() {
        setViewValue();
        HashMap<String, String> map = new HashMap<>();
        map.put("phone",mAccount);
        map.put("password",mPassword);
        map.put("device_id","ac1566d9edf44b0299ec0c33d6621153");
        map.put("device_type","Android");
        Flowable<JwtModel> observable = apiServices.getJwt(mAccount,mPassword,"ac1566d9edf44b0299ec0c33d6621153","Android");
        Api.subscribe(observable, new UpDataSubscriber<JwtModel>() {
            @Override
            public void onSucess(JwtModel responseModel) {
                jwt = responseModel.getData().getJwt();
                Log.e("666666",jwt);
                Toast.makeText(UpdataActivity.this, "成功：" + jwt, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUpdateUrl() {
        Log.e("666", Api.URL_BASE_PRE+mChannel+Api.URL_BASE_AFTER + mCurrrentVersion + "/" + mChannel);
        apiServices.getUpdateUrl(mCurrrentVersion,mChannel).enqueue(new Callback<UpDateModel>() {
            @Override
            public void onResponse(retrofit2.Call<UpDateModel> call, Response<UpDateModel> response) {
                putUpDate();
            }

            @Override
            public void onFailure(retrofit2.Call<UpDateModel> call, Throwable t) {
                Toast.makeText(UpdataActivity.this, "获取升级链接失败:" + t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group != null && checkedId > 0) {
            if (group == binding.rgChannelOut) {
                binding.rgChannelInner.clearCheck();
                switch (checkedId) {
                    case R.id.rb_1:
                        mChannel = channelArr[0];
                        break;
                    case R.id.rb_2:
                        mChannel = channelArr[1];
                        break;
                    case R.id.rb_3:
                        mChannel = channelArr[2];
                        break;
                }
            } else {
                binding.rgChannelOut.clearCheck();
                switch (checkedId) {
                    case R.id.rb_4:
                        mChannel = channelArr[3];
                        break;
                    case R.id.rb_5:
                        mChannel = channelArr[4];
                        break;
                    case R.id.rb_6:
                        mChannel = channelArr[5];
                        break;
                }
            }
            group.check(checkedId);
            initRetrofit();
        }
    }

    private void initRetrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Api.URL_BASE_PRE+mChannel+Api.URL_BASE_AFTER)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiServices = retrofit.create(Api.ApiServices.class);
    }

    private void putUpDate() {
        UpDatePutModel models = new UpDatePutModel();
        models.setDownloadUrl(mUpdateApkUrl);
        Flowable<UpDatePutResponseModel> observable = apiServices.putUpDate(jwt,mUpdateVersion, mChannel,models);
        Log.e("6661", Api.URL_BASE_PRE+mChannel+Api.URL_BASE_AFTER + mUpdateVersion + "/" + mChannel);
        Api.subscribe(observable, new UpDataSubscriber<UpDatePutResponseModel>() {
            @Override
            public void onSucess(UpDatePutResponseModel response) {
                Toast.makeText(UpdataActivity.this, "put升级:" + response.getMessage()+">>>"+response.getData().getVersion()+">>>"+response.getData().getDownloadUrl(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
