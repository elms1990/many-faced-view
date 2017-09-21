package com.theycallmeerick.manyfacedview.sample.state;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.theycallmeerick.manyfacedview.sample.R;
import com.theycallmeerick.manyfacedview.view.ManyFacedView;
import com.theycallmeerick.manyfacedview.view.ViewState;

public class StateTestActivity extends AppCompatActivity {
    private static final String IMAGE_URL = "https://memegenerator.net/img/instances/250x250/68986417/a-girl-has-no-name.jpg";

    ManyFacedView mStateView;
    ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_test);

        bindViews();
        setUpListeners();

        if (savedInstanceState == null) {
            displayContent();
        }
    }

    private void bindViews() {
        mStateView = findViewById(R.id.state_view);
        mImage = mStateView.getView(ViewState.CONTENT).findViewById(R.id.image);
    }

    private void setUpListeners() {
        findViewById(R.id.state_content_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayContent();
            }
        });
        findViewById(R.id.state_loading_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayLoading();
            }
        });
        findViewById(R.id.state_error_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayError();
            }
        });
        findViewById(R.id.state_empty_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayEmpty();
            }
        });
    }

    private void displayContent() {
        mStateView.setState(ViewState.CONTENT);
        Picasso.with(this)
                .load(IMAGE_URL)
                .centerCrop()
                .fit()
                .into(mImage);
    }

    private void displayLoading() {
        mStateView.setState(ViewState.LOADING);
    }

    private void displayError() {
        mStateView.setState(ViewState.ERROR);
    }

    private void displayEmpty() {
        mStateView.setState(ViewState.EMPTY);
    }
}
