package com.seven.designmode.mvp;

/**
 * Created by seven.qin on 2018/12/19.
 */
public interface IBaseMvp<M extends IModel, V extends IView, P extends BasePresenter> {
    M createModel();

    V createView();

    P createPresenter();
}