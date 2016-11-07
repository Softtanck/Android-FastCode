package com.tangce.fastcode.api;

import com.tangce.fastcode.model.BaseResponse;
import com.tangce.fastcode.model.LoginResponse;
import com.tangce.fastcode.network.FastHttp;

import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Tanck on 11/3/2016.
 * <p>
 * Describe:
 */
public class LoginApi {
    private interface LoginService {
        @POST("csh-interface/endUser/login.jhtml")
        Observable<BaseResponse<LoginResponse>> login(@Body Map<String, Object> body);
    }

//    private static LoginApi loginApi;
//
//    private LoginApi() {
//    }
//
//    public static LoginApi getInstance() {
//        if (null == loginApi) {
//            synchronized (LoginApi.class) {
//                if (null == loginApi)
//                    loginApi = new LoginApi();
//            }
//        }
//        return loginApi;
//    }

    public static Observable<BaseResponse<LoginResponse>> login(Map<String, Object> body) {
        return FastHttp.create(LoginService.class).login(body);
    }

}
