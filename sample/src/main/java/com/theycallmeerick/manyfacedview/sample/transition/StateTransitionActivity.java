package com.theycallmeerick.manyfacedview.sample.transition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theycallmeerick.manyfacedview.sample.R;
import com.theycallmeerick.manyfacedview.view.ManyFacedView;
import com.theycallmeerick.manyfacedview.view.OnStateChangedListener;
import com.theycallmeerick.manyfacedview.view.ViewState;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class StateTransitionActivity extends AppCompatActivity {
    private static final String IMAGE_URL = "http://weknowmemes.com/wp-content/uploads/2012/10/i-say-the-whole-world-must-learn-of-our-peaceful-ways-by-force-bender.jpg";

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
        setContentView(R.layout.activity_state_transition);

        ButterKnife.bind(this);

        stateView.setOnStateChangedListener(new OnStateChangedListener() {
            @Override
            public void onChanged(int newState) {
                displayContents();
            }
        });
        initialize();
    }

    private void initialize() {
        disposable = Completable
                .timer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        stateView.setState(ViewState.CONTENT);
                    }
                });
    }

    private void displayContents() {
        ButterKnife.bind(contentView, this);

        contentView.text.setText("Bender B. Rodriguez");
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
