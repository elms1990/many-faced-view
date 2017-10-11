package com.theycallmeerick.manyfacedview.animator;

import android.animation.Animator;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AnimatorComposer {
    private Animator firstAnimator;
    private Animator latestAnimator;

    private List<Animator> registeredAnimators;

    private AnimatorComposer(Animator startingAnimator) {
        firstAnimator = startingAnimator;
        latestAnimator = startingAnimator;

        registeredAnimators = new ArrayList<>();
        registeredAnimators.add(startingAnimator);
    }

    public static AnimatorComposer from(@NonNull Animator startingAnimator) {
        return new AnimatorComposer(startingAnimator);
    }

    public static AnimatorComposer from(@NonNull Animator startingAnimator, @NonNull View target) {
        startingAnimator.setTarget(target);
        return new AnimatorComposer(startingAnimator);
    }

    public AnimatorComposer next(@NonNull Animator nextAnimator) {
        return next(nextAnimator, null);
    }

    public AnimatorComposer next(@NonNull Animator nextAnimator, View target) {
        if (target != null) {
            nextAnimator.setTarget(target);
        }
        latestAnimator.addListener(new AnimatorEndChain(nextAnimator));
        latestAnimator = nextAnimator;
        registeredAnimators.add(nextAnimator);
        return this;
    }

    public AnimatorComposer nextAction(@NonNull ActionCallback action) {
        latestAnimator.addListener(new AnimatorEndActionChain(action));
        return this;
    }

    public AnimatorComposer start() {
        nextAction(new ActionCallback() {
            @Override
            public void execute() {
                clearListeners();
            }
        });

        firstAnimator.start();
        return this;
    }

    private void clearListeners() {
        for (int i = 0; i < registeredAnimators.size(); i++) {
            registeredAnimators.get(i).getListeners().clear();
        }
    }

    public void stop() {
        for (int i = 0; i < registeredAnimators.size(); i++) {
            registeredAnimators.get(i).cancel();
        }
    }
}
