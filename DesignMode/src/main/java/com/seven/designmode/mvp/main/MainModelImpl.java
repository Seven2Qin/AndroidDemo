package com.seven.designmode.mvp.main;

/**
 * Created by seven.qin on 2018/12/19.
 */
public class MainModelImpl implements MainModel{
    @Override
    public String getDataFromNet() {
        return "Seven";
    }

    @Override
    public void stopRequest() {

    }
}
