package com.tangce.fastcode.presenter;

import com.tangce.fastcode.api.ApiException;
import com.tangce.fastcode.model.BaseResponse;
import com.tangce.fastcode.view.BaseView;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Tanck on 11/2/2016.
 * <p>
 * Describe:
 */
public class BasePresenter<V extends BaseView> {

    protected V mView;

    private CompositeSubscription mCompositeSubscription;

    public void attachView(V view) {
        this.mView = view;
    }

    public void detachView() {
        this.mView = null;
        onUnsubscribe();
    }


    //RXjava un for not oom
    protected void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    /**
     * add event
     *
     * @param observable
     * @param subscriber
     * @param <T>
     */
    protected <T> void addSubscription(Observable<T> observable, Subscriber<T> subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .map(new HttpResultFunc())
                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <M> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    protected class HttpResultFunc<M> implements Func1<BaseResponse<M>, M> {

        @Override
        public M call(BaseResponse<M> baseResponse) {
            if (baseResponse.getCode().equals("0000")) {
                throw new ApiException(100);
            }
            return baseResponse.getData();
        }
    }
}
