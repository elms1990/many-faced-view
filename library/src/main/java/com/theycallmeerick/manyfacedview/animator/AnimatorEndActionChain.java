package com.theycallmeerick.manyfacedview.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.support.annotation.NonNull;

public class AnimatorEndActionChain implements AnimatorSet.AnimatorListener {
    private ActionCallback action;

    public AnimatorEndActionChain(@NonNull ActionCallback action) {
        this.action = action;
    }

    @Override
    public void onAnimationStart(Animator animator) {
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        action.execute();
    }

    @Override
    public void onAnimationCancel(Animator animator) {
    }

    @Override
    public void onAnimationRepeat(Animator animator) {
    }
}
