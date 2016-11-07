package com.tangce.fastcode.network;

import android.text.TextUtils;

import com.tangce.fastcode.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Tanck on 10/27/2016.
 * <p>
 * Describe: the fastHttp utils can get response from internet
 */
public class FastHttp {

    public static final boolean DEBUG = true;

    private static final long DEFAULT_TIMEOUT = 5;// default time out

    //    private static final String DEFAULT_URL = "https://github.com/q422013";
    private static final String DEFAULT_URL = "http://120.27.92.247:10001/";

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
        if (DEBUG) // is debug mode show request info 
            okClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    LogUtils.d("request url:" + request.url() + "\n" + bodyToString(request.body()));
                    Response response = chain.proceed(request);
                    LogUtils.d("response:" + response.body().string());
                    return chain.proceed(request);
                }
            });
        okClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        Retrofit.Builder builder = new Retrofit.Builder().client(okClient.build()).baseUrl(HttpUrl.parse(getBaseUrl()));
        mCallAdapter = getCallAdapter();
        mConverter = getConverter();
        if (null != mCallAdapter)
            builder.addCallAdapterFactory(mCallAdapter);
        if (null != mConverter)
            builder.addConverterFactory(mConverter);
        retrofit = builder.build();
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
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
        if (TextUtils.isEmpty(mBaseUrl))
            return DEFAULT_URL;
        return mBaseUrl;
    }


    public static <T extends Object> T create(Class<T> clz) {
        return FastHttp.getInstance().retrofit.create(clz);
    }


    public static RequestBody stringToRequestBody(String data) {
        return RequestBody.create(MediaType.parse("text/plain"), data);
    }

    public static RequestBody imgToRequestBody(String path) {
        File file = new File(path);
        if (!file.exists())
            throw new RuntimeException("the image not found");
        return RequestBody.create(MediaType.parse("image/png"), new File(path));
    }


    public static RequestBody imgToRequestBody(File file) {
        if (!file.exists())
            throw new RuntimeException("the image not found");
        return RequestBody.create(MediaType.parse("image/png"), file);
    }

//    public static <T> T create(Class<T> clz, Subscriber<T> subscriber, Observable observable){
//
//    }
}
