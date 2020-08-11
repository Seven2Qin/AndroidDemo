package com.seven.viewdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 超链接TextView
 * Created by seven.qin on 2019/1/8.
 */
@SuppressLint("AppCompatCustomView")
public class HyperLinkTextView extends TextView {

    private Context mContext;
    private List<LinkText> mList;
    private HyperLinkCallback mCallback;
    private static int DEFAULT_TEXT_SIZE = 16;
    private static int DEFAULT_TEXT_COLOR = Color.BLUE;

    public HyperLinkTextView(Context context) {
        super(context);
        mContext = context;
    }

    public HyperLinkTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        //设置相关属性
        TypedArray t = mContext.obtainStyledAttributes(attrs, R.styleable.HyperLinkTextView);
        DEFAULT_TEXT_SIZE = t.getInteger(R.styleable.HyperLinkTextView_itemSize, DEFAULT_TEXT_SIZE);
        DEFAULT_TEXT_COLOR = t.getColor(R.styleable.HyperLinkTextView_defaultColor, DEFAULT_TEXT_COLOR);
    }


    /**
     * 设置超链接文本-->多样式多点击事件
     *
     * @param list
     */
    private void bindData(List<LinkText> list) {
        if (list == null || list.size() == 0) {
            mList = new ArrayList<>();
            mList.add(new LinkText("《百度链接》", "http://www.baidu.com", Color.BLUE));
            mList.add(new LinkText("《谷歌服务协议》", "http://www.google.com", Color.RED));
            mList.add(new LinkText("《腾讯》", "http://www.tencent.com"));
        } else {
            mList.clear();
            mList.addAll(list);
        }


        SpannableStringBuilder style = new SpannableStringBuilder();
        int startId = 0;
        for (int i = 0; i < mList.size(); i++) {
            LinkText item = mList.get(i);
            style.append(item.content);
            //ClickableMonitor需要在ForegroundColorSpan之前，否则textColor会被覆盖
            style.setSpan(new ClickableMonitor(mCallback, i, item), startId, startId + item.content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            style.setSpan(new ForegroundColorSpan(item.color == 0 ? DEFAULT_TEXT_COLOR : item.color), startId, startId + item.content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            startId += item.content.length();
        }


        this.setText(style);
        this.setTextSize(DEFAULT_TEXT_SIZE);
        //设置点击后背景为透明
        this.setHighlightColor(getResources().getColor(android.R.color.transparent));
        this.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 添加回调
     *
     * @param callback
     */
    public void setHyperLinkCallback(HyperLinkCallback callback, List<LinkText> list) {
        this.mCallback = callback;
        //绑定数据
        bindData(list);
    }

    /**
     * 外部回调接口
     */
    public interface HyperLinkCallback {
        void onItemClick(View v, int position, LinkText linkText);
    }

    /**
     * 超链接内容bean对象
     */
    public class LinkText {
        private String content;
        private String url;
        private int color;

        public LinkText(String content, String url) {
            this.content = content;
            this.url = url;
        }

        public LinkText(String content, String url, int color) {
            this.content = content;
            this.url = url;
            this.color = color;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }
    }

    /**
     * 每段item点击事件 监听
     */
    private class ClickableMonitor extends ClickableSpan {
        private HyperLinkCallback mCallback;
        private int mPosition;
        private LinkText mLinkText;

        public ClickableMonitor(HyperLinkCallback callback, int position, LinkText linkText) {
            mCallback = callback;
            mPosition = position;
            mLinkText = linkText;
        }

        @Override
        public void onClick(View v) {
            if (mCallback != null) {
                mCallback.onItemClick(v, mPosition, mLinkText);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);// 设置文字下划线不显示
//            ds.setColor(Color.BLUE);// 设置字体颜色
            ds.clearShadowLayer();
        }
    }

}
