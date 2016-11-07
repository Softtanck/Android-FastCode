package com.tangce.fastcode;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import com.tangce.fastcode.api.EditUserInfoApi;
import com.tangce.fastcode.api.LoginApi;
import com.tangce.fastcode.model.LoginResponse;
import com.tangce.fastcode.model.ModifyUserPhotoResponse;
import com.tangce.fastcode.network.FastHttp;
import com.tangce.fastcode.presenter.MainPresenter;
import com.tangce.fastcode.utils.LogUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

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
        Map<String, Object> param = new HashMap<>();
        param.put("userName", "183****3706");
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


    public void upload(View view) {
        Map<String, RequestBody> up = new HashMap<>();
        up.put("userId", FastHttp.stringToRequestBody("1"));
        up.put("token", FastHttp.stringToRequestBody(mPresenter.getToken()));
        String path = Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "print.png";
        File file = new File(path);
        up.put("photo\"; filename=\"" + file.getName(), FastHttp.imgToRequestBody(file));
        mPresenter.start(EditUserInfoApi.modifyUserPhoto(up), "4");
    }

    @Override
    public void onDataSuccess(String tag, Object data) {
        LogUtils.d("tag:" + tag);

        if ("1".equals(tag)) {
            LoginResponse response = (LoginResponse) data;
            LogUtils.d(response.getDefaultVehicleIcon());
        } else if ("4".equals(tag)) {
            ModifyUserPhotoResponse modifyUserPhotoResponse = (ModifyUserPhotoResponse) data;
            LogUtils.d(modifyUserPhotoResponse.getNickName());
        }
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
