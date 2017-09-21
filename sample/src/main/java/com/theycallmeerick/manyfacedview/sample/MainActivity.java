package com.theycallmeerick.manyfacedview.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.theycallmeerick.manyfacedview.sample.butterknife.ButterKnifeStateTestActivity;
import com.theycallmeerick.manyfacedview.sample.customanimation.CustomAnimationTestActivity;
import com.theycallmeerick.manyfacedview.sample.recyclerview.RecyclerViewTestActivity;
import com.theycallmeerick.manyfacedview.sample.state.StateTestActivity;
import com.theycallmeerick.manyfacedview.sample.transition.StateTransitionActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.state_test_button)
    void onStateTestButtonClick() {
        startActivity(new Intent(MainActivity.this, StateTestActivity.class));
    }

    @OnClick(R.id.butterknife_test_button)
    void onButterKnifeTestButtonClick() {
        startActivity(new Intent(MainActivity.this, ButterKnifeStateTestActivity.class));
    }

    @OnClick(R.id.recyclerview_test_button)
    void onRecyclerViewTestButtonClick() {
        startActivity(new Intent(MainActivity.this, RecyclerViewTestActivity.class));
    }

    @OnClick(R.id.transition_test_button)
    void onStateTransitionViewTestButtonClick() {
        startActivity(new Intent(MainActivity.this, StateTransitionActivity.class));
    }

    @OnClick(R.id.custom_animation_test_button)
    void onCustomAnimationViewTestButtonClick() {
        startActivity(new Intent(MainActivity.this, CustomAnimationTestActivity.class));
    }
}
