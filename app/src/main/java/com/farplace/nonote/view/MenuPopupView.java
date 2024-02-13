package com.farplace.nonote.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.farplace.nonote.R;

public class MenuPopupView extends PopupWindow {
    View view;
    Context context;
    LinearLayout main_linear;
    View anchorView;
    int minWidth;

    public MenuPopupView(Context context) {
        super(((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.popup_menu_layout, null)
                , LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        view = getContentView();
        view.setTranslationZ(10);
        this.context = context;
        initView();
    }

    public MenuPopupView(Context context, View anchorView) {
        this(context);
        this.anchorView = anchorView;

    }

    private void initView() {
        main_linear = view.findViewById(R.id.main_linear);
        if (minWidth != 0) {
            setLinearWidth(minWidth);
        }
    }

    public void setMinWidth(int width) {
        this.minWidth = width;
    }

    private void setLinearWidth(int width) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) main_linear.getLayoutParams();
        layoutParams.width = width;
        main_linear.setLayoutParams(layoutParams);
        main_linear.requestLayout();
    }

    public void showMenuWindow(int offsetX, int offsetY) {
//        int ox = 0;
//        int oy = 0;
//        Log.e("TEST===>>",anchorView.getY()+"");
//        if (anchorView.getY() > 1000) {
//            oy = anchorView.getHeight() + 300;
//        }

        showAsDropDown(anchorView,offsetX,offsetY);
        if (anchorView != null) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(anchorView, "translationZ", 0, -15);
            objectAnimator.start();
            ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, "elevation", 0, 5);
            objectAnimator1.start();
        }
        setOnDismissListener(() -> {
            if (anchorView != null) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(anchorView, "translationZ", anchorView.getTranslationZ(), 0);
                objectAnimator.start();
                ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(view, "elevation", 5, 0);
                objectAnimator1.start();
            }
        });

    }

    public void addItemView(View view) {
        main_linear.addView(view);
    }
}