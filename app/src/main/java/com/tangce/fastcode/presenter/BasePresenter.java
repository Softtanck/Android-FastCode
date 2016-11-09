package com.tangce.fastcode.presenter;

import android.text.TextUtils;
import android.util.SparseArray;

import com.tangce.fastcode.FcApiCallback;
import com.tangce.fastcode.model.BaseResponse;
import com.tangce.fastcode.presenter.progress.ProgressCancelListener;
import com.tangce.fastcode.presenter.progress.ProgressDialogCustomListener;
import com.tangce.fastcode.presenter.progress.ProgressDialogHandler;
import com.tangce.fastcode.view.BaseView;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Tanck on 11/2/2016.
 * <p>
 * Describe:
 */
public class BasePresenter<V extends BaseView> implements ProgressCancelListener {

    protected V mView;

    //default
    protected ProgressDialogHandler mDialogHandler;

    // user custom implement dialog
    protected ProgressDialogCustomListener mCustomListener;

    private CompositeSubscription mCompositeSubscription;

    private SparseArray<Subscription> mSubs = new SparseArray<>();

    // save mToken
    private String mToken;

    public String getToken() {
        return mToken;
    }

    public void attachView(V view) {
        this.mView = view;
        if (view instanceof ProgressDialogCustomListener)
            mCustomListener = (ProgressDialogCustomListener) view;
        else
            mDialogHandler = new ProgressDialogHandler(mView.getContextForPresenter(), this, true);
    }

    @Deprecated
    public void attachView(V view, ProgressDialogCustomListener listener) {
        this.mView = view;
        this.mCustomListener = listener;
    }

    public void detachView() {
        this.mView = null;
        onUnsubscribe(null);
    }


    //RXjava un for not oom
    protected void onUnsubscribe(String tag) {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            if (TextUtils.isEmpty(tag))
                mCompositeSubscription.unsubscribe();
            else {
                int key = tag.hashCode();
                Subscription temp = mSubs.get(key);
                mCompositeSubscription.remove(temp);
                mSubs.remove(key);
            }
        }
    }


    /**
     * add event
     *
     * @param tag
     * @param observable
     * @param subscriber
     * @param <T>
     */
    protected <T> void addSubscription(String tag, Observable<T> observable, Subscriber<T> subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        mCompositeSubscription.add(subscription);
        if (!TextUtils.isEmpty(tag))
            mSubs.put(tag.hashCode(), subscription);
    }


    /**
     * just for normal http request
     *
     * @param tag
     * @param observable
     * @param subscriber
     * @param <T>
     */
    protected <T> void addSubscriptionNormal(String tag, Observable<T> observable, Subscriber<T> subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

        mCompositeSubscription.add(subscription);

        if (!TextUtils.isEmpty(tag))
            mSubs.put(tag.hashCode(), subscription);
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <M> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    protected class HttpResultFunc<M> implements Func1<BaseResponse<M>, M> {

        @Override
        public M call(BaseResponse<M> baseResponse) {
//            if (baseResponse.getCode().equals("0000")) {
//                throw new ApiException(100);
//            }
            mToken = baseResponse.getToken();
            return baseResponse.getData();
        }
    }


    @Override
    public void onCancelProgress() {
        onUnsubscribe(null);
    }

    private void showProgressDialog() {
        if (null != mCustomListener) {
            mCustomListener.needProgressShow();
            return;
        }
        if (mDialogHandler != null) {
            mDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (null != mCustomListener) {
            mCustomListener.needProgressDismiss();
            return;
        }
        if (mDialogHandler != null) {
            mDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mDialogHandler = null;
        }
    }

    /**
     * no Tag
     *
     * @param o
     */
    public <T> void start(Observable<T> o) {
        addSubscription(null, o, new FcApiCallback<T>() {

            @Override
            public void onStart() {
                showProgressDialog();
            }

            @Override
            public void onCompleted() {
                mView.onComplete();
                dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.onDataFailed(msg);
                dismissProgressDialog();
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
    public <T> void start(Observable<T> o, final String tag) {
        addSubscription(tag, o, new FcApiCallback<T>() {
            @Override
            public void onStart() {
                showProgressDialog();
            }

            @Override
            public void onCompleted() {
                mView.onComplete(tag);
                dismissProgressDialog();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                mView.onDataFailed(tag, msg);
                dismissProgressDialog();
            }

            @Override
            public void onNext(T model) {
                mView.onDataSuccess(tag, model);
            }
        });
    }


    /**
     * cancel http request and unsubcribe
     */
    public void cancel() {
        onUnsubscribe(null);
    }

    /**
     * cancel http request and unsubcribe
     *
     * @param tag
     */
    public void cancel(String tag) {
        onUnsubscribe(tag);
    }
}
