package com.qujing.leeyong.lottery.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewDebug;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/5/11.
 */

    public class MyTextView extends TextView {
        public MyTextView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        public MyTextView(Context context, AttributeSet attrs,
                                     int defStyle) {
            super(context, attrs, defStyle);
            // TODO Auto-generated constructor stub
        }

        public MyTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            // TODO Auto-generated constructor stub
        }
        //转载请注明出处：http://blog.csdn.net/u014071694/article/details/52004542
        @Override
        @ViewDebug.ExportedProperty(category = "focus")
        public boolean isFocused() {
            // TODO Auto-generated method stub
            return true;// 重点
        }

        @Override
        protected void onFocusChanged(boolean focused, int direction,
                                      Rect previouslyFocusedRect) {
            // TODO Auto-generated method stub
            super.onFocusChanged(true, direction, previouslyFocusedRect);// 重点
        }
    }