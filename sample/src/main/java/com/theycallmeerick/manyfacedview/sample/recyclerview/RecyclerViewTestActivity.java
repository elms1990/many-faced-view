package com.theycallmeerick.manyfacedview.sample.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.theycallmeerick.manyfacedview.sample.R;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewTestActivity extends AppCompatActivity {
    private List<TestViewModel> data = TestViewModel.createRandomElements(36);

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_test);
        ButterKnife.bind(this);

        initialize();
    }

    private void initialize() {
        Collections.shuffle(data);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        TestAdapter adapter = new TestAdapter();
        adapter.setData(data);
        recyclerView.setAdapter(adapter);
    }
}
