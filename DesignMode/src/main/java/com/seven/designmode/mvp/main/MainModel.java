package com.seven.designmode.mvp.main;

import android.util.Log;

import com.seven.designmode.mvp.IModel;
import com.seven.designmode.mvp.IView;

/**
 * Created by seven.qin on 2018/12/19.
 */
public interface MainModel extends IModel, IView {
    /**
     * 从网络获取数据
     *
     * @return
     */
    String getDataFromNet();

    /**
     * 停止请求
     */
    void stopRequest();
}
