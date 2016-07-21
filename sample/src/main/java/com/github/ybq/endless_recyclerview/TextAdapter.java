package com.github.ybq.endless_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 */
public class TextAdapter extends RecyclerView.Adapter<TextHolder> {

    private List<Integer> data;

    public void setData(List<Integer> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<Integer> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public TextHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        TextView textView = (TextView) View.inflate(viewGroup.getContext(), android.R.layout.simple_list_item_1, null);
        return new TextHolder(textView);
    }

    @Override
    public void onBindViewHolder(TextHolder textHolder, int position) {
        textHolder.bind("data : " + data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public Integer getLastId() {
        return data == null ? 0 : data.get(data.size() - 1);
    }
}

class TextHolder extends RecyclerView.ViewHolder {

    public TextHolder(TextView itemView) {
        super(itemView);
    }

    public void bind(String text) {
        ((TextView) itemView).setText(text);
    }
}