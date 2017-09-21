package com.theycallmeerick.manyfacedview.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.support.annotation.NonNull;

public class AnimatorEndChain implements AnimatorSet.AnimatorListener {
    private Animator animator;

    public AnimatorEndChain(@NonNull Animator animator) {
        this.animator = animator;
    }

    @Override
    public void onAnimationStart(Animator animator) {
    }

    @Override
    public void onAnimationEnd(Animator animator) {
        this.animator.start();
    }

    @Override
    public void onAnimationCancel(Animator animator) {
    }

    @Override
    public void onAnimationRepeat(Animator animator) {
    }
}
