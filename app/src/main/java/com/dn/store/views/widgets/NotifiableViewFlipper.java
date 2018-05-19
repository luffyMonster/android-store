package com.dn.store.views.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ViewFlipper;

public class NotifiableViewFlipper extends ViewFlipper {

    private OnFlipListener onFlipListener;

    public NotifiableViewFlipper(Context context) {
        super(context);
    }

    public NotifiableViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void showPrevious() {
        super.showPrevious();
        if (hasFlipListener()){
            onFlipListener.onShowPrevious(this);
        }
    }

    @Override
    public void showNext() {
        super.showNext();
        if (hasFlipListener()){
            onFlipListener.onShowNext(this);
        }
    }

    public void setOnFlipListener(OnFlipListener onFlipListener) {
        this.onFlipListener = onFlipListener;
    }

    public boolean hasFlipListener() {
        return onFlipListener != null;
    }

    public interface OnFlipListener {
        void onShowPrevious(NotifiableViewFlipper flipper);

        void onShowNext(NotifiableViewFlipper flipper);
    }
}
