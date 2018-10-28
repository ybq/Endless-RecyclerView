package com.github.ybq.endless_recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 */
public class TextAdapter extends RecyclerView.Adapter<TextHolder> {

    private List<DataProvider.Item> data;

    public void setData(List<DataProvider.Item> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<DataProvider.Item> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public TextHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        TextView textView = (TextView) View.inflate(viewGroup.getContext(), R.layout.layout_item, null);
        textView.setGravity(Gravity.CENTER);
        return new TextHolder(textView);
    }

    @Override
    public void onBindViewHolder(TextHolder textHolder, int position) {
        DataProvider.Item item = data.get(position);
        ((TextView) textHolder.itemView).setText(String.valueOf(item.data));
        textHolder.itemView.setBackgroundColor(item.color);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    public Integer getLastId() {
        return data == null ? 0 : data.get(data.size() - 1).data;
    }
}

class TextHolder extends RecyclerView.ViewHolder {

    public TextHolder(TextView itemView) {
        super(itemView);
    }

}