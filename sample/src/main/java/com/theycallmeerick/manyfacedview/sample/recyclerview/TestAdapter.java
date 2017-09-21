package com.theycallmeerick.manyfacedview.sample.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theycallmeerick.manyfacedview.sample.R;
import com.theycallmeerick.manyfacedview.view.ManyFacedView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.Nullable;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private List<TestViewModel> data;

    public void setData(List<TestViewModel> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.test_item_state_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TestViewModel viewModel = data.get(position);
        loadData(holder, viewModel);
    }

    private void loadData(ViewHolder holder, TestViewModel viewModel) {
        holder.text.setText(viewModel.text);
        Picasso.with(holder.image.getContext())
                .load(viewModel.imageUrl)
                .centerCrop()
                .fit()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.state_view)
        ManyFacedView stateView;

        @Nullable
        @BindView(R.id.image)
        ImageView image;

        @Nullable
        @BindView(R.id.text)
        TextView text;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
