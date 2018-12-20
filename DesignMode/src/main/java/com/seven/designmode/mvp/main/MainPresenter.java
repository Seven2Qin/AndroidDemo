package com.seven.designmode.mvp.main;

import com.seven.designmode.mvp.BasePresenter;
import com.seven.designmode.mvp.IModel;

/**
 * Created by seven.qin on 2018/12/19.
 */
public class MainPresenter extends BasePresenter<MainModel, MainView> implements IModel {
    public void getData() {//这里要注意判空（view和model可能为空）
        String dataFromNet = null;
        if (model != null) {
            dataFromNet = model.getDataFromNet();
        }
        if (getView() != null) {
            getView().setData(dataFromNet);
        }
    }

    @Override
    protected void onViewDestroy() {//销毁Activity时的操作，可以停止当前的model
        if (model != null) {
            model.stopRequest();
        }
    }
}