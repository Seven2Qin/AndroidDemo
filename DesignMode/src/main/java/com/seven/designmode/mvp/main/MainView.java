package com.seven.designmode.mvp.main;

import com.seven.designmode.mvp.IView;

/**
 * Created by seven.qin on 2018/12/19.
 */
public interface MainView extends IView {
    /**
     * 设置数据
     *
     * @param str
     */
    void setData(String str);
}
