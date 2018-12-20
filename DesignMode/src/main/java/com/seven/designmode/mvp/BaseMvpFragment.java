package com.seven.designmode.mvp;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by seven.qin on 2018/12/19.
 */
public abstract class BaseMvpFragment<M extends IModel, V extends IView, P extends BasePresenter> extends Fragment implements IBaseMvp<M, V, P> {
    protected P presenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        presenter = createPresenter();
        if (presenter != null) {
            presenter.registerModel(createModel());
            presenter.registerView(createView());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) {
            presenter.destroy();
        }
    }
}