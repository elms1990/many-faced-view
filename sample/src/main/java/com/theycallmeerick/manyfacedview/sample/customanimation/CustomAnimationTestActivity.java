package com.theycallmeerick.manyfacedview.sample.customanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theycallmeerick.manyfacedview.sample.R;
import com.theycallmeerick.manyfacedview.view.ManyFacedView;
import com.theycallmeerick.manyfacedview.view.ViewState;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CustomAnimationTestActivity extends AppCompatActivity {
    private static final String IMAGE_URL = "https://vignette.wikia.nocookie.net/sonic/images/f/fb/Amyposse%27.png/revision/latest?cb=20130620175559";

    @BindView(R.id.state_view)
    ManyFacedView stateView;

    ContentView contentView = new ContentView();

    Disposable disposable;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_animation_test);

        ButterKnife.bind(this);
        delayAndSwitchToState(ViewState.CONTENT);
    }

    private void delayAndSwitchToState(@ViewState int state) {
        disposable = Flowable.just(state)
                .delay(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer currentState) throws Exception {
                        stateView.setState(currentState);
                        if (currentState == ViewState.CONTENT) {
                            displayContents();
                            delayAndSwitchToState(ViewState.LOADING);
                        } else {
                            delayAndSwitchToState(ViewState.CONTENT);
                        }
                    }
                });
    }

    private void displayContents() {
        ButterKnife.bind(contentView, this);
        contentView.text.setText("Finished Loading Amy image :D");
        Picasso.with(this)
                .load(IMAGE_URL)
                .centerInside()
                .fit()
                .into(contentView.image);
    }

    static class ContentView {
        @BindView(R.id.image)
        ImageView image;

        @BindView(R.id.text)
        TextView text;
    }
}
