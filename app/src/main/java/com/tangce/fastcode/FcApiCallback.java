package com.tangce.fastcode;

/**
 * Created by Tanck on 11/3/2016.
 * <p>
 * Describe:
 */

import com.tangce.fastcode.utils.LogUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

public abstract class FcApiCallback<T> extends Subscriber<T> {

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
            } else if (httpCode == 502 || httpCode == 404 || httpCode == 500) {
                msg = "服务器异常，请稍后再试";
            }
        } else if (e instanceof SocketTimeoutException) {
            msg = "网络中断，请检查您的网络状态";
        } else if (e instanceof ConnectException) {
            msg = "网络中断，请检查您的网络状态";
        } else {
            msg = e.getMessage();
        }
        LogUtils.d(msg);
    }
}
