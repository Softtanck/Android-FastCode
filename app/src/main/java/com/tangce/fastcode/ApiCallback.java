package com.tangce.fastcode;

/**
 * Created by Tanck on 11/3/2016.
 * <p>
 * Describe:
 */

import com.tangce.fastcode.utils.LogUtils;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public abstract class ApiCallback<T> extends Subscriber<T> {

    protected int httpCode;

    protected String msg;

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            /**
             * Debug
             */
            try {
                LogUtils.d(httpException.response().errorBody().string());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            httpCode = httpException.code();
            msg = httpException.getMessage();
            if (httpCode == 504) {
                msg = "网络不给力";
            } else if (httpCode == 502 || httpCode == 404) {
                msg = "服务器异常，请稍后再试";
            }
        } else {
            msg = e.getMessage();
        }
        LogUtils.d(msg);
    }
}
