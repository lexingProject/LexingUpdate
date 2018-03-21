package com.lexing360.app.lexingupdate.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lexing360.app.lexingupdate.Api;
import com.lexing360.app.lexingupdate.utils.BooleanUtils;
import com.lexing360.app.lexingupdate.R;
import com.lexing360.app.lexingupdate.UpDataSubscriber;
import com.lexing360.app.lexingupdate.base.BaseBindingActivity;
import com.lexing360.app.lexingupdate.databinding.ActivityLayoutBinding;
import com.lexing360.app.lexingupdate.model.LayoutModel;
import com.lexing360.app.lexingupdate.model.ResponseModel;
import com.lexing360.app.lexingupdate.base.RouterConstants;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.Callback;
import retrofit2.Response;

@Route(path = RouterConstants.LAYOUT)
public class LayoutActivity extends BaseBindingActivity<ActivityLayoutBinding> implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    String name;
    String checkAvatarurl;
    String checkIntegration;
    String redDot;
    boolean hide1;
    boolean hide2;
    boolean hide3;
    boolean hide4;
    String fonttypeFitstOne;
    String fonttypeSecondOne;
    String fonttypeFirstTwo;
    String fontTypeSeconeTwo;
    String textColor1Str;
    String textColor2Str;
    String textColor3Str;
    String textColor4Str;
    int messageSettingId;

    private boolean isFirst = true;
    LayoutModel.DataBean dataBean;
    List<LayoutModel.DataBean> data;


    @Override
    protected void bindData(ActivityLayoutBinding binding, Bundle savedInstanceState) {
        initListener();
        getXmlInfo();
    }

    private void initListener() {
        binding.btPut.setOnClickListener(this);
        binding.rgAvatarurl.setOnCheckedChangeListener(this);
        binding.rgReddot.setOnCheckedChangeListener(this);
        binding.fonttypeFitstOne.rgFontType.setOnCheckedChangeListener(this);
        binding.fonttypeSecondOne.rgFontType.setOnCheckedChangeListener(this);
        binding.fonttypeFirstTwo.rgFontType.setOnCheckedChangeListener(this);
        binding.fonttypeSecondTwo.rgFontType.setOnCheckedChangeListener(this);
        binding.fonttypeFitstOne.rgHide.setOnCheckedChangeListener(this);
        binding.fonttypeSecondOne.rgHide.setOnCheckedChangeListener(this);
        binding.fonttypeFirstTwo.rgHide.setOnCheckedChangeListener(this);
        binding.fonttypeSecondTwo.rgHide.setOnCheckedChangeListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_layout;
    }


    //获取目前服务器的布局参数
    private void getXmlInfo() {
        apiServices.getXmlInfo().enqueue(new Callback<LayoutModel>() {
            @Override
            public void onResponse(retrofit2.Call<LayoutModel> call, Response<LayoutModel> response) {
                if (response.body() == null) {
                    Toast.makeText(LayoutActivity.this,"获取当前服务器参数为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                data = response.body().getData();
                initView();
            }

            @Override
            public void onFailure(retrofit2.Call<LayoutModel> call, Throwable t) {
                Toast.makeText(LayoutActivity.this, "获取后台参数错误：" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    //初始化数据，显示服务器返回的最新状态
    private void initView() {
        if (isFirst) {
            dataBean = data.get(0);
            binding.setModel(dataBean);
            getChecked();
            isFirst = false;
        }
        textColorChangeListener(binding.etMessageSettingId);
        textColorChangeListener(binding.textColor1);
        textColorChangeListener(binding.textColor2);
        textColorChangeListener(binding.textColor3);
        textColorChangeListener(binding.textColor4);

        binding.preview.setModel2(dataBean);
    }

    //获取布局中RadioGroup的选中状态并设置
    public void getChecked() {
        String fontType = dataBean.getFirstLabelOfLineOne().getFontType();
        if (fontType.equals("LARGE")) {
            binding.fonttypeFitstOne.rbFontTypeLarge.setChecked(true);
        } else if (fontType.equals("LARGE")) {
            binding.fonttypeFitstOne.rbFontTypeMiddle.setChecked(true);
        } else {
            binding.fonttypeFitstOne.rbFontTypeSmall.setChecked(true);
        }
        String fontType2 = dataBean.getSecondLabelOfLineOne().getFontType();
        if (fontType2.equals("LARGE")) {
            binding.fonttypeSecondOne.rbFontTypeLarge.setChecked(true);
        } else if (fontType.equals("LARGE")) {
            binding.fonttypeSecondOne.rbFontTypeMiddle.setChecked(true);
        } else {
            binding.fonttypeSecondOne.rbFontTypeSmall.setChecked(true);
        }

        String fontType3 = dataBean.getFirstLabelOfLineTwo().getFontType();
        if (fontType3.equals("LARGE")) {
            binding.fonttypeFirstTwo.rbFontTypeLarge.setChecked(true);
        } else if (fontType.equals("LARGE")) {
            binding.fonttypeFirstTwo.rbFontTypeMiddle.setChecked(true);
        } else {
            binding.fonttypeFirstTwo.rbFontTypeSmall.setChecked(true);
        }

        String fontType4 = dataBean.getSecondLabelOfLineTwo().getFontType();
        if (fontType4.equals("LARGE")) {
            binding.fonttypeSecondTwo.rbFontTypeLarge.setChecked(true);
        } else if (fontType.equals("LARGE")) {
            binding.fonttypeSecondTwo.rbFontTypeMiddle.setChecked(true);
        } else {
            binding.fonttypeSecondTwo.rbFontTypeSmall.setChecked(true);
        }

        if (dataBean.getFirstLabelOfLineOne().isHide()) {
            binding.fonttypeFitstOne.rbHideTrue.setChecked(true);
        }
        if (dataBean.getSecondLabelOfLineOne().isHide()) {
            binding.fonttypeSecondOne.rbHideTrue.setChecked(true);
        }
        if (dataBean.getFirstLabelOfLineTwo().isHide()) {
            binding.fonttypeFirstTwo.rbHideTrue.setChecked(true);
        }
        if (dataBean.getSecondLabelOfLineTwo().isHide()) {
            binding.fonttypeSecondTwo.rbHideTrue.setChecked(true);
        }

    }

    //获取修改后的EditText的值
    private void getViewValue() {
        name = binding.etName.getText().toString();
        textColor1Str = binding.textColor1.getText().toString();
        textColor2Str = binding.textColor2.getText().toString();
        textColor3Str = binding.textColor3.getText().toString();
        textColor4Str = binding.textColor4.getText().toString();
        try {
            messageSettingId = Integer.parseInt(binding.etMessageSettingId.getText().toString());
        } catch (NumberFormatException e) {
            messageSettingId = 1;
        }

    }

    //设置修改后的布局信息值
    private void setPutInfo() {
        getViewValue();
        dataBean.setName(name);
        dataBean.setMessageSettingId(messageSettingId);
        dataBean.setRedDot(redDot);
        dataBean.setAvatarUrl(BooleanUtils.convertToBoolean(checkAvatarurl));
        dataBean.setIntegration(BooleanUtils.convertToBoolean(checkIntegration));
        dataBean.getFirstLabelOfLineOne().setTextColor(textColor1Str);
        dataBean.getFirstLabelOfLineOne().setFontType(fonttypeFitstOne);
        dataBean.getFirstLabelOfLineOne().setHide(hide1);

        dataBean.getSecondLabelOfLineOne().setTextColor(textColor2Str);
        dataBean.getSecondLabelOfLineOne().setFontType(fonttypeSecondOne);
        dataBean.getSecondLabelOfLineOne().setHide(hide2);

        dataBean.getFirstLabelOfLineTwo().setTextColor(textColor3Str);
        dataBean.getFirstLabelOfLineTwo().setFontType(fonttypeFirstTwo);
        dataBean.getFirstLabelOfLineTwo().setHide(hide3);

        dataBean.getSecondLabelOfLineTwo().setTextColor(textColor4Str);
        dataBean.getSecondLabelOfLineTwo().setFontType(fontTypeSeconeTwo);
        dataBean.getSecondLabelOfLineTwo().setHide(hide4);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (group != null && checkedId > 0) {
            if (group == binding.rgAvatarurl) {
                if (checkedId == R.id.rb_avatarurl_true) {
                    checkAvatarurl = "true";
                } else {
                    checkAvatarurl = "false";
                }

            } else if (group == binding.rgIntegration) {
                if (checkedId == R.id.rb_integration_true) {
                    checkIntegration = "true";
                } else {
                    checkIntegration = "false";
                }
            }  else if (group == binding.rgReddot) {
                if (checkedId == R.id.rb_reddot_true) {
                    redDot = binding.rbReddotTrue.getText().toString();
                } else {
                    redDot = binding.rbReddotFalse.getText().toString();
                }
            } else if (group == binding.fonttypeFitstOne.rgFontType) {
                if (group.getCheckedRadioButtonId() == R.id.rb_fontType_large) {
                    fonttypeFitstOne = "LARGE";
                } else if (group.getCheckedRadioButtonId() == R.id.rb_fontType_middle) {
                    fonttypeFitstOne = "MIDDLE";
                } else {
                    fonttypeFitstOne = "SMALL";
                }
            } else if (group == binding.fonttypeSecondOne.rgFontType) {
                if (group.getCheckedRadioButtonId() == R.id.rb_fontType_large) {
                    fonttypeSecondOne = "LARGE";
                } else if (group.getCheckedRadioButtonId() == R.id.rb_fontType_middle) {
                    fonttypeSecondOne = "MIDDLE";
                } else {
                    fonttypeSecondOne = "SMALL";
                }
            } else if (group == binding.fonttypeFirstTwo.rgFontType) {
                if (group.getCheckedRadioButtonId() == R.id.rb_fontType_large) {
                    fonttypeFirstTwo = "LARGE";
                } else if (group.getCheckedRadioButtonId() == R.id.rb_fontType_middle) {
                    fonttypeFirstTwo = "MIDDLE";
                } else {
                    fonttypeFirstTwo = "SMALL";
                }
            } else if (group == binding.fonttypeSecondTwo.rgFontType) {
                if (group.getCheckedRadioButtonId() == R.id.rb_fontType_large) {
                    fontTypeSeconeTwo = "LARGE";
                } else if (group.getCheckedRadioButtonId() == R.id.rb_fontType_middle) {
                    fontTypeSeconeTwo = "MIDDLE";
                } else {
                    fontTypeSeconeTwo = "SMALL";
                }
            } else if (group == binding.fonttypeFitstOne.rgHide) {
                if (checkedId == R.id.rb_hide_true) {
                    hide1 = true;
                } else {
                    hide1 = false;
                }
            } else if (group == binding.fonttypeSecondOne.rgHide) {
                if (checkedId == R.id.rb_hide_true) {
                    hide2 = true;
                } else {
                    hide2 = false;
                }
            } else if (group == binding.fonttypeFirstTwo.rgHide) {
                if (checkedId == R.id.rb_hide_true) {
                    hide3 = true;
                } else {
                    hide3 = false;
                }
            } else if (group == binding.fonttypeSecondTwo.rgHide) {
                if (checkedId == R.id.rb_hide_true) {
                    hide4 = true;
                } else {
                    hide4 = false;
                }
            }
            group.check(checkedId);
            if (!isFirst) {
                setPutInfo();
            }

        }
    }

    public void textColorChangeListener(final EditText et) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (R.id.textColor1 == et.getId()) {
                    dataBean.getFirstLabelOfLineOne().setTextColor(s.toString());
                    return;
                }
                if (R.id.textColor2 == et.getId()) {
                    dataBean.getSecondLabelOfLineOne().setTextColor(s.toString());
                    return;
                }
                if (R.id.textColor3 == et.getId()) {
                    dataBean.getFirstLabelOfLineTwo().setTextColor(s.toString());
                    return;
                }
                if (R.id.textColor4 == et.getId()) {
                    dataBean.getSecondLabelOfLineTwo().setTextColor(s.toString());
                    return;
                }
                if(R.id.et_messageSettingId == et.getId()){
                    if (s == null || s.length() == 0) {
                        return;
                    }
                    messageSettingId = Integer.parseInt(s.toString());
                    if (messageSettingId == 1) {
                        getXmlInfo();
                        dataBean = data.get(0);
                        binding.setModel(dataBean);
                        return;
                    }

                    if (messageSettingId == 2) {
                        getXmlInfo();
                        dataBean = data.get(1);
                        binding.setModel(dataBean);
                        return;
                    }

                    if (messageSettingId == 3) {
                        getXmlInfo();
                        dataBean = data.get(2);
                        binding.setModel(dataBean);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        putXmlInfo();
    }

    //推送修改后的配置
    private void putXmlInfo() {
        setPutInfo();
        if (TextUtils.isEmpty(textColor1Str) || TextUtils.isEmpty(textColor1Str) || TextUtils.isEmpty(textColor1Str) || TextUtils.isEmpty(textColor1Str)) {
            Toast.makeText(this, "请先填写颜色值", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(binding.etMessageSettingId.getText().toString())) {
            Toast.makeText(this, "请先填写id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (apiServices == null) {
            return;
        }
        Flowable<ResponseModel> observable = apiServices.putXmlInfo(dataBean);
        Api.subscribe(observable, new UpDataSubscriber<ResponseModel>() {
            @Override
            public void onSucess(ResponseModel responseModel) {
                Toast.makeText(LayoutActivity.this, responseModel.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("666Next", responseModel.getMessage());
            }

        });
    }

}
