package com.tangce.fastcode.presenter;

import com.tangce.fastcode.ApiCallback;
import com.tangce.fastcode.view.BaseView;

import rx.Observable;

/**
 * Created by Tanck on 11/3/2016.
 * <p>
 * Describe:
 */
public class MainPresenter<T> extends BasePresenter {

    public MainPresenter(BaseView view) {
        attachView(view);
    }

    /**
     * no Tag
     *
     * @param o
     */
    public void start(Observable<T> o) {
        addSubscription(o, new ApiCallback<T>() {
            @Override
            public void onCompleted() {
                mView.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.onDataFailed(msg);
            }

            @Override
            public void onNext(T model) {
                mView.onDataSuccess(model);
            }
        });
    }

    /**
     * @param o
     * @param tag
     */
    public void start(Observable<T> o, final String tag) {
        addSubscription(o, new ApiCallback<T>() {
            @Override
            public void onCompleted() {
                mView.onComplete(tag);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.onDataFailed(tag, msg);
            }

            @Override
            public void onNext(T model) {
                mView.onDataSuccess(tag, model);
            }
        });
    }
}
