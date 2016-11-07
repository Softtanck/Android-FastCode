# Android-FastCode(Route+Retrofit2.0+RxJava+MVP)
a fast project for android. the project support intercept activity(Route).

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
public class LoginApi {
    private interface LoginService {
        @POST("csh-interface/endUser/login.jhtml")
        Observable<BaseResponse<LoginResponse>> login(@Body Map<String, Object> body);
    }

    public static Observable<BaseResponse<LoginResponse>> login(Map<String, Object> body) {
        return FastHttp.create(LoginService.class).login(body);
    }

}
```

in your activity you can call :
```java
        Map<String, Object> param = new HashMap<>();
        param.put("userName", "183****3706");
        param.put("password", "password");
        param.put("imei", "422013");
        mPresenter.start(LoginApi.login(param), "1");
        mPresenter.start(LoginApi.login(param), "2");
        mPresenter.start(LoginApi.login(param), "3");
        mPresenter.start(LoginApi.login(param));
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

# License Apache
2016-2017 copy right @Tanck 
