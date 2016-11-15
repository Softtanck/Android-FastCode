博客链接:http://blog.csdn.net/u010316858/article/details/52606465 (对该框架进行详细说明)

# Android-FastCode(Router+Retrofit2.0+RxJava+OkHttp+MVP)
a fast project for android. the project support intercept activity(Router) and you can custom config your intent rule and support access remote server data convert to gson. you can implement yourself custom progressdialog on requesting data.

[![Build Status](https://travis-ci.org/q422013/Android-FastCode.svg?branch=master)](https://github.com/q422013/Android-FastCode)

# How to use:
## App Global
at app oncreate method add:
```java
Router.addRouteCreator(new RouterRule());
```
and you shoud config your route rule:
```java
  private class RouterRule implements RouteCreator {

        @Override
        public Map<String, RouteMap> createRouteRules() {
            Map<String, RouteMap> rule = new HashMap<>();
            rule.put("Tanck://login", new RouteMap("com.tangce.fastcode.LoginActivity"));
            return rule;
        }
    }
```

## Activity
you should extends BaseActivity and implement createPresenter() method. for example:
```java
public class MainActivity extends BaseActivity<MainPresenter, Object> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    MainPresenter createPresenter() {
        return new MainPresenter(this);
    }
}
```

## Network Usage:
Create network API class,for example:
```java

// normal post api
public class LoginApi {
    private interface LoginService {
        @POST("csh-interface/endUser/login.jhtml")
        Observable<BaseResponse<LoginResponse>> login(@Body Map<String, Object> body);
    }

    public static Observable<BaseResponse<LoginResponse>> login(Map<String, Object> body) {
        return FastHttp.create(LoginService.class).login(body);
    }

}

// upload file/image and other parameters post api
public class EditUserInfoApi {
    private interface EditUserInfoService {
        @Multipart
        @POST("csh-interface/endUser/editUserPhoto.jhtml")
        Observable<BaseResponse<ModifyUserPhotoResponse>> modifyUserPhoto(@PartMap Map<String, RequestBody> params);
    }

    public static Observable<BaseResponse<ModifyUserPhotoResponse>> modifyUserPhoto(Map<String, RequestBody> params) {
        return FastHttp.create(EditUserInfoService.class).modifyUserPhoto(params);
    }
}
```

in your activity you can call :
```java
        //normal post param
        Map<String, Object> param = new HashMap<>();
        param.put("userName", "183****3706");
        param.put("password", "password");
        param.put("imei", "422013");
        mPresenter.start(LoginApi.login(param), "1");// tag :1
        mPresenter.start(LoginApi.login(param), "2");// tag :2
        mPresenter.start(LoginApi.login(param), "3");// tag:3
        mPresenter.start(LoginApi.login(param));// no tag
        
        
        // upload file/image
        String path = Environment.getExternalStorageDirectory() + File.separator + "Android" + File.separator + "print.png";
        File file = new File(path);
        Map<String, RequestBody> up = new HashMap<>();
        up.put("userId", FastHttp.stringToRequestBody("1"));
        up.put("token", FastHttp.stringToRequestBody(mPresenter.getToken()));
        up.put("photo\"; filename=\"" + file.getName(), FastHttp.imgToRequestBody(file)); // support String path or File object.
        mPresenter.start(EditUserInfoApi.modifyUserPhoto(up), "4");// tag :4
        
```
the network call you can overwrite:
```java 
   @Override
    public void onDataSuccess(M data) {

    }

    @Override
    public void onDataSuccess(String tag, M data) {

    }

    @Override
    public void onDataFailed(String reason) {

    }

    @Override
    public void onDataFailed(String tag, String reason) {

    }

    @Override
    public void onNoNetWork() {

    }

    @Override
    public void onNoNetWork(String tag) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onComplete(String tag) {

    }
```

## Custom ProgressDialog
you just implement ProgressDialogCustomListener.

# License
2016-2017 copy right @Tanck<br/>
Licensed under the Apache License, Version 2.0 (the "License");<br/>
you may not use this file except in compliance with the License.<br/>
You may obtain a copy of the License at<br/>

http://www.apache.org/licenses/LICENSE-2.0<br/>

Unless required by applicable law or agreed to in writing, software<br/>
distributed under the License is distributed on an "AS IS" BASIS,<br/>
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.<br/>
See the License for the specific language governing permissions and<br/>
limitations under the License.

