package com.github.ybq.endless;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public abstract class EndlessAdapter<LVH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    private final int TYPE_LOAD_MORE = 101;
    private boolean loading;
    private View loadMoreView;

    public EndlessAdapter(View loadMoreView) {
        this.loadMoreView = loadMoreView;
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (loading && viewType == TYPE_LOAD_MORE) {
            return new LoadMoreHolder(loadMoreView);
        }
        return onCreateHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (loading && getItemViewType(position) == TYPE_LOAD_MORE) {
            //// TODO:
        } else {
            //noinspection unchecked
            onBindHolder((LVH) holder, position);
        }
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
        notifyDataSetChanged();
    }

    public boolean isLoading() {
        return loading;
    }

    @Override
    public final int getItemViewType(int position) {
        if (loading && position == getItemCount() - 1) {
            return TYPE_LOAD_MORE;
        }
        return getViewType(position);
    }

    @Override
    public final int getItemCount() {

        if (loading) {
            return getCount() + 1;
        } else {
            return getCount();
        }
    }

    public abstract int getViewType(int position);

    public abstract int getCount();

    public abstract LVH onCreateHolder(ViewGroup parent, int viewType);

    public abstract void onBindHolder(LVH holder, int position);

    public static EndlessAdapter wrap(final RecyclerView.Adapter adapter, View loadMoreView) {
        if (adapter instanceof EndlessAdapter) {
            return (EndlessAdapter) adapter;
        }
        return new EndlessAdapter(loadMoreView) {
            @Override
            public int getViewType(int position) {
                return adapter.getItemViewType(position);
            }

            @Override
            public int getCount() {
                return adapter.getItemCount();
            }

            @Override
            public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
                return adapter.onCreateViewHolder(parent, viewType);
            }

            @Override
            public void onBindHolder(RecyclerView.ViewHolder holder, int position) {
                //noinspection unchecked
                adapter.onBindViewHolder(holder, position);
            }
        };
    }


    class LoadMoreHolder extends RecyclerView.ViewHolder {

        public LoadMoreHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(
                    new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

}