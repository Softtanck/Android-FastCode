package com.tangce.fastcode;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tangce.fastcode.presenter.BasePresenter;
import com.tangce.fastcode.view.BaseView;

/**
 * Created by Tanck on 11/3/2016.
 * <p>
 * Describe:
 */
public abstract class BaseActivity<P extends BasePresenter, M> extends AppCompatActivity implements BaseView<M> {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    /**
     * create a presenter
     *
     * @return
     */
    abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mPresenter)
            mPresenter.detachView();
    }

    @Override
    public BaseActivity getContextForPresenter() {
        return this;
    }

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
}
