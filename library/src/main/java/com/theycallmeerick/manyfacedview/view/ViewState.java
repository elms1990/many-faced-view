package com.theycallmeerick.manyfacedview.view;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.theycallmeerick.manyfacedview.view.ViewState.CONTENT;
import static com.theycallmeerick.manyfacedview.view.ViewState.EMPTY;
import static com.theycallmeerick.manyfacedview.view.ViewState.ERROR;
import static com.theycallmeerick.manyfacedview.view.ViewState.EXTRA1;
import static com.theycallmeerick.manyfacedview.view.ViewState.EXTRA2;
import static com.theycallmeerick.manyfacedview.view.ViewState.EXTRA3;
import static com.theycallmeerick.manyfacedview.view.ViewState.EXTRA4;
import static com.theycallmeerick.manyfacedview.view.ViewState.LOADING;
import static com.theycallmeerick.manyfacedview.view.ViewState.UNKNOWN;

@Retention(RetentionPolicy.SOURCE)
@IntDef({ UNKNOWN, CONTENT, LOADING, ERROR, EMPTY, EXTRA1, EXTRA2, EXTRA3, EXTRA4 })
public @interface ViewState {
    int UNKNOWN = 0; // internal use only
    int CONTENT = 1;
    int LOADING = 2;
    int ERROR = 3;
    int EMPTY = 4;
    int EXTRA1 = 5;
    int EXTRA2 = 6;
    int EXTRA3 = 7;
    int EXTRA4 = 8;
}
