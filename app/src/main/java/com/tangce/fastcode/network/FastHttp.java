package com.tangce.fastcode.network;

import android.text.TextUtils;

import com.tangce.fastcode.App;

import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Tanck on 10/27/2016.
 * <p/>
 * Describe: the fastHttp utils can get response from internet
 */
public class FastHttp {

    private static final long DEFAULT_TIMEOUT = 5;// default time out

    private static final String DEFAULT_URL = "https://github.com/q422013";

    private static FastHttp instance;

    private String mBaseUrl;

    private OkHttpClient.Builder mDefaultOkHttpClient;

    private CallAdapter.Factory mCallAdapter;

    private Converter.Factory mConverter;

    private Retrofit retrofit;

    private FastHttp() {
        init();
    }


    public static FastHttp getInstance() {
        if (null == instance) {
            synchronized (FastHttp.class) {
                if (null == instance) {
                    instance = new FastHttp();
                }
            }
        }
        return instance;
    }


    /**
     * init the retrofit and okhttp
     */
    private void init() {
        OkHttpClient.Builder okClient = getOkHttpClient();
        okClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        Retrofit.Builder builder = new Retrofit.Builder().client(okClient.build()).baseUrl(HttpUrl.parse(mBaseUrl));
        if (null != mCallAdapter)
            builder.addCallAdapterFactory(mCallAdapter);
        if (null != mConverter)
            builder.addConverterFactory(mConverter);
        retrofit = builder.build();
    }

    public static <T> T create(Class<T> clz) {
        return FastHttp.getInstance().retrofit.create(clz);
    }

    public static <T> T create(Class<T> clz, Subscriber<T> subscriber, Observable observable){

    }


    /**
     * set user custom http client
     *
     * @param client user http client
     * @param <T>
     */
    public <T extends OkHttpClient> void setCustomOkHttpClient(T client) {
        this.mDefaultOkHttpClient = client.newBuilder();
    }

    private OkHttpClient.Builder getOkHttpClient() {
        if (null == mDefaultOkHttpClient)
            mDefaultOkHttpClient = new OkHttpClient.Builder();
        return mDefaultOkHttpClient;
    }

    /**
     * set user custom call adapter factory
     *
     * @param callAdapterFactory user call adapter factory
     * @param <T>
     */
    public <T extends CallAdapter.Factory> void setCustomCallAdapterFactory(T callAdapterFactory) {
        this.mCallAdapter = callAdapterFactory;
    }

    private <T extends CallAdapter.Factory> T getCallAdapter() {
        if (null == mCallAdapter)
            mCallAdapter = RxJavaCallAdapterFactory.create();
        return (T) mCallAdapter;
    }


    /**
     * set user custom Converter factory
     *
     * @param converterFactory user Converter factory
     * @param <T>
     */
    public <T extends Converter.Factory> void setCustomConverterFactory(T converterFactory) {
        this.mConverter = converterFactory;
    }

    private <T extends Converter.Factory> T getConverter() {
        if (null == mConverter)
            mConverter = GsonConverterFactory.create();
        return (T) mConverter;
    }

    /**
     * set http base url
     *
     * @param url
     */
    public void setBaseUrl(String url) {
        mBaseUrl = TextUtils.isEmpty(url) ? DEFAULT_URL : url;
    }

    private String getBaseUrl() {
        return mBaseUrl;
    }

}
