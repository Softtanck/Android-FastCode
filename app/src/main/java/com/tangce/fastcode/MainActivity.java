package com.tangce.fastcode;

import android.os.Bundle;
import android.view.View;

import com.tangce.fastcode.api.LoginApi;
import com.tangce.fastcode.model.LoginResponse;
import com.tangce.fastcode.presenter.MainPresenter;
import com.tangce.fastcode.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity<MainPresenter, Object>  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    MainPresenter createPresenter() {
        return new MainPresenter(this);
    }


    public void login(View view) {
//        FastHttp.test(App.class);
        Map<String, Object> param = new HashMap<>();
        param.put("userName", "18380473706");
        param.put("password", "f8EFUs+YEQckfzGEtNZ/JMyvTPPJVC5s9NquQ5UAJQWrXiaWN/XAwZ5EorsIqvKq0WYCrS1xkFvy\n" +
                "                                                              rdZ+OZYysX9l/Txm/nq4q4wJ75xlnBBTR3ZtFoeztChJrCprEhFF3GF4jGGv41UkagCxravaiWcg\n" +
                "                                                              q/AJVoTvmNmyHv3PWtg=");
        param.put("imei", "422013");
        mPresenter.start(LoginApi.login(param), "1");
        mPresenter.start(LoginApi.login(param), "2");
        mPresenter.start(LoginApi.login(param), "3");
        mPresenter.start(LoginApi.login(param));
//        Router.create("Tanck://login").open(MainActivity.this);
    }


    @Override
    public void onDataSuccess(String tag, Object data) {
        LogUtils.d("tag:" + tag);
        LoginResponse response = (LoginResponse) data;
        LogUtils.d(response.getDefaultVehicleIcon());
    }

    @Override
    public void onDataSuccess(Object data) {
        LogUtils.d("no tag");
    }

    @Override
    public void onDataFailed(String reason) {

    }

    @Override
    public void onNoNetWork() {


    }

    @Override
    public void onComplete(String tag) {
        LogUtils.d("onComplete:" + tag);
    }

//    @Override
//    public void needProgressShow() {
//        LogUtils.d("needProgressShow");
//    }
//
//    @Override
//    public void needProgressDismiss() {
//        LogUtils.d("needProgressDismiss");
//    }
}
