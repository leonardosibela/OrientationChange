package com.sibela.examples.orientationchange.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sibela.examples.orientationchange.R;
import com.sibela.examples.orientationchange.model.Book;
import com.sibela.examples.orientationchange.model.TitleAndDescription;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TitleDescriptionAdapter<T extends TitleAndDescription> extends RecyclerView.Adapter<TitleDescriptionAdapter.TitleDescriptionViewHolder> {

    private List<T> mList;

    public TitleDescriptionAdapter(List<T> list) {
        mList = list;
    }

    @Override
    public TitleDescriptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_description_item, parent, false);
        return new TitleDescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TitleDescriptionViewHolder holder, int position) {
        if (mList.isEmpty())
            return;

        T item = mList.get(position);

        if (item != null) {
            holder.mTitle.setText(item.getTitle());
            holder.mnDescription.setText(item.getDescription());
        }
    }

    @Override
    public int getItemCount() {
        if (mList == null)
            return 0;

        return mList.size();
    }

    public void setContent(T item) {
        mList.add(item);
        notifyDataSetChanged();
    }

    public void setContent(List<T> itens) {
        mList = itens;
        notifyDataSetChanged();
    }

    public List<T> getItens() {
        return mList;
    }

    public static class TitleDescriptionViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.title_text)
        TextView mTitle;

        @Bind(R.id.description_text)
        TextView mnDescription;

        public TitleDescriptionViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
