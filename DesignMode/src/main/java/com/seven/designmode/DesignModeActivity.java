package com.seven.designmode;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.seven.designmode.mvp.BaseMvpActivity;
import com.seven.designmode.mvp.main.MainModel;
import com.seven.designmode.mvp.main.MainModelImpl;
import com.seven.designmode.mvp.main.MainPresenter;
import com.seven.designmode.mvp.main.MainView;

public class DesignModeActivity extends BaseMvpActivity<MainModel, MainView, MainPresenter> implements MainView {

    private Button btnMVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design_mode);
        btnMVP = findViewById(R.id.btn_mvp);
        btnMVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
            }
        });
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv, "rotation", 0, 360).setDuration(2000);
//        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        objectAnimator.start();
//        SystemClock.sleep(30 * 1000);
    }

    private void init() {
        if (presenter != null) {
            presenter.getData();
        }
    }

    @Override
    public MainModel createModel() {
        return new MainModelImpl();
    }

    @Override
    public MainView createView() {
        return this;
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void setData(String str) {
        btnMVP.setText(str);
    }

}
