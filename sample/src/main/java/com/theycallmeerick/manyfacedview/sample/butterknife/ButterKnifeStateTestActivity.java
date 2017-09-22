package com.theycallmeerick.manyfacedview.sample.butterknife;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theycallmeerick.manyfacedview.sample.R;
import com.theycallmeerick.manyfacedview.view.ManyFacedView;
import com.theycallmeerick.manyfacedview.view.ViewState;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

public class ButterKnifeStateTestActivity extends AppCompatActivity {
    private static final String IMAGE_URL = "http://jakewharton.github.io/butterknife/static/logo.png";

    @BindView(R.id.state_view)
    ManyFacedView stateView;

    @BindView(R.id.content_view_button)
    Button button;

    @BindView(R.id.image)
    ImageView image;

    Disposable disposable;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterknife_state_test);
        ButterKnife.bind(this);

        disposable = Completable
                .timer(3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        setListener();
                        displayContents();
                    }
                });
    }

    private void setListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(
                        ButterKnifeStateTestActivity.this,
                        "I'm a sexy button!",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

    private void displayContents() {
        stateView.setState(ViewState.CONTENT);
        Picasso.with(this)
                .load(IMAGE_URL)
                .centerCrop()
                .fit()
                .into(image);
    }
}
