package com.tangce.fastcode.view;

import com.tangce.fastcode.BaseActivity;

/**
 * Created by Tanck on 11/2/2016.
 * <p>
 * Describe:
 */
public interface BaseView<M> {
    /**
     * request network data success
     *
     * @param data
     */
    void onDataSuccess(String tag, M data);

    /**
     * request network data success
     *
     * @param data
     */
    void onDataSuccess(M data);


    /**
     * request network data fail [note:401 or 404 and so on]
     *
     * @param reason
     */
    void onDataFailed(String reason);

    /**
     * request network data fail [note:401 or 404 and so on]
     *
     * @param tag
     * @param reason
     */
    void onDataFailed(String tag, String reason);

    /**
     * no network
     */
    void onNoNetWork();

    /**
     * no network
     *
     * @param tag
     */
    void onNoNetWork(String tag);

    /**
     * finish
     */
    void onComplete();

    /**
     * finish
     *
     * @param tag
     */
    void onComplete(String tag);

    /**
     * get context
     *
     * @return
     */
    BaseActivity getContextForPresenter();

}
