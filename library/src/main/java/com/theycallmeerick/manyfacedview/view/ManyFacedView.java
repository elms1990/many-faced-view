package com.theycallmeerick.manyfacedview.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.theycallmeerick.manyfacedview.R;
import com.theycallmeerick.manyfacedview.animator.ActionCallback;
import com.theycallmeerick.manyfacedview.animator.AnimatorComposer;

public class ManyFacedView extends FrameLayout {
    private LayoutInflater layoutInflater;

    private SparseArray<View> stateViews = new SparseArray<>();

    private @ViewState int previousState = ViewState.UNKNOWN;
    private @ViewState int initialState = ViewState.UNKNOWN;
    private @ViewState int currentState = ViewState.UNKNOWN;

    private boolean animateTransition = false;
    private Animator inAnimator;
    private Animator outAnimator;
    private AnimatorComposer animatorComposer;

    private OnStateChangedListener listener;

    public ManyFacedView(@NonNull Context context) {
        super(context);
        initialize();
    }

    public ManyFacedView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
        parseAttributes(attrs);
        setUpView();
    }

    public ManyFacedView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
        parseAttributes(attrs);
        setUpView();
    }

    private void initialize() {
        layoutInflater = LayoutInflater.from(getContext());
    }

    private void parseAttributes(@NonNull AttributeSet attributeSet) {
        TypedArray ta = getResources().obtainAttributes(attributeSet, R.styleable.ManyFacedView);

        boolean hasContentView = inflateViewForStateIfExists(ta, ViewState.CONTENT,
                R.styleable.ManyFacedView_mfv_content);
        if (!hasContentView) {
            throw new IllegalStateException("Missing view for content");
        }
        inflateViewForStateIfExists(ta, ViewState.EMPTY, R.styleable.ManyFacedView_mfv_empty);
        inflateViewForStateIfExists(ta, ViewState.ERROR, R.styleable.ManyFacedView_mfv_error);
        inflateViewForStateIfExists(ta, ViewState.LOADING, R.styleable.ManyFacedView_mfv_loading);
        inflateViewForStateIfExists(ta, ViewState.EXTRA1, R.styleable.ManyFacedView_mfv_extra1);
        inflateViewForStateIfExists(ta, ViewState.EXTRA2, R.styleable.ManyFacedView_mfv_extra2);
        inflateViewForStateIfExists(ta, ViewState.EXTRA3, R.styleable.ManyFacedView_mfv_extra3);
        inflateViewForStateIfExists(ta, ViewState.EXTRA4, R.styleable.ManyFacedView_mfv_extra4);
        if (ta.hasValue(R.styleable.ManyFacedView_mfv_state)) {
            initialState = ta.getInt(R.styleable.ManyFacedView_mfv_state, ViewState.UNKNOWN);
        }

        animateTransition = ta.getBoolean(R.styleable.ManyFacedView_mfv_animateChanges, true);
        int inAnimationId = ta.getResourceId(R.styleable.ManyFacedView_mfv_inAnimation,
                R.anim.fade_in);
        inAnimator = AnimatorInflater.loadAnimator(getContext(), inAnimationId);
        int outAnimationId = ta.getResourceId(R.styleable.ManyFacedView_mfv_outAnimation,
                R.anim.fade_out);
        outAnimator = AnimatorInflater.loadAnimator(getContext(), outAnimationId);

        ta.recycle();
    }

    private void setUpView() {
        int newState = (initialState == ViewState.UNKNOWN) ? ViewState.CONTENT : initialState;
        setState(newState);
    }

    private boolean inflateViewForStateIfExists(TypedArray ta,
                                                @ViewState int state,
                                                int styleable) {
        if (ta.hasValue(styleable)) {
            int layoutId = ta.getResourceId(styleable, 0);
            addStateView(state, layoutId);
            return true;
        }

        return false;
    }

    public void addStateView(@ViewState int state, View view) {
        stateViews.put(state, view);
        if (currentState != state) {
            view.setVisibility(View.INVISIBLE);
        }
        addView(view);
    }

    public void addStateView(@ViewState int state, @LayoutRes int layoutId) {
        View inflatedView = layoutInflater.inflate(layoutId, this, false);
        addStateView(state, inflatedView);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(@ViewState int state) {
        return (T) stateViews.get(state);
    }

    private boolean hasStateViewAttached() {
        return getChildCount() > 0;
    }

    public @ViewState int getState() {
        return this.currentState;
    }

    public void setState(@ViewState int state) {
        if (currentState == state) {
            return;
        }
        if (stateViews.indexOfKey(state) < 0) {
            throw new IllegalArgumentException(
                    "Attempting to set a state without setting its view (" + state + ")");
        }

        View inView = stateViews.get(state);
        View outView = stateViews.get(currentState);

        previousState = currentState;
        currentState = state;
        if (animateTransition) {
            animateViewSwap(outView, inView);
        } else {
            immediateSwap(outView, inView);
        }
    }

    private void animateViewSwap(final View outView, final View inView) {
        cancelPreviousAnimation();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            animatePreLollipop(outView, inView);
        } else {
            animateLollipopAndNewer(outView, inView);
        }
    }

    private void animatePreLollipop(final View outView, final View inView) {
        if (outView != null) {
            animateInAndOutView(outView, inView);
        } else {
            animateInView(inView);
        }
    }

    private void animateLollipopAndNewer(final View outView, final View inView) {
        animateInAndOutView(outView, inView);
    }

    private void animateInAndOutView(final View outView, final View inView) {
        inView.setVisibility(View.INVISIBLE);

        animatorComposer = AnimatorComposer
                .from(outAnimator, outView)
                .nextAction(new ActionCallback() {
                    @Override
                    public void execute() {
                        hideOutView(outView);
                        inView.setVisibility(View.VISIBLE);
                    }
                })
                .next(inAnimator, inView)
                .nextAction(new ActionCallback() {
                    @Override
                    public void execute() {
                        notifyStateChanged();
                    }
                })
                .start();
    }

    private void animateInView(final View inView) {
        inView.setVisibility(View.VISIBLE);

        animatorComposer = AnimatorComposer
                .from(inAnimator, inView)
                .nextAction(new ActionCallback() {
                    @Override
                    public void execute() {
                        notifyStateChanged();
                    }
                })
                .start();
    }

    private void cancelPreviousAnimation() {
        if (animatorComposer != null) {
            animatorComposer.stop();
        }
    }

    private void immediateSwap(View outView, View inView) {
        hideOutView(outView);
        inView.setVisibility(View.VISIBLE);
        notifyStateChanged();
    }

    private void notifyStateChanged() {
        if (listener != null && previousState != ViewState.UNKNOWN) {
            listener.onChanged(currentState);
        }
    }

    private void hideOutView(View outView) {
        if (outView != null) {
            outView.setVisibility(View.INVISIBLE);
        }
    }

    public void enableTransitionAnimation(boolean enable) {
        animateTransition = enable;
    }

    public void setInAnimator(@NonNull Animator inAnimator) {
        this.inAnimator = inAnimator;
    }

    public void setOutAnimator(@NonNull Animator outAnimator) {
        this.outAnimator = outAnimator;
    }

    public void setOnStateChangedListener(@Nullable OnStateChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parentState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(parentState);
        savedState.currentState = currentState;
        savedState.previousState = previousState;

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        currentState = previousState;
        setState(savedState.currentState);
    }

    private static class SavedState extends BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel source) {
                        return new SavedState(source);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };

        @ViewState
        int currentState;

        @ViewState
        int previousState;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.currentState = in.readInt();
            this.previousState = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentState);
            dest.writeInt(previousState);
        }
    }
}