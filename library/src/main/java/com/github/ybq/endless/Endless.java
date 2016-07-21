package com.github.ybq.endless;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 */
public class Endless {

    private final EndlessScrollListener listener;
    private boolean loadMoreAvailable = true;
    private LoadMoreListener loadMoreListener;
    private RecyclerView recyclerView;
    private EndlessAdapter mAdapter;
    private static ArrayList<WeakReference<Endless>> mLoadMoreEntries;
    private View loadMoreView;

    public static void remove(RecyclerView recyclerView) {
        if (mLoadMoreEntries != null) {
            for (int i = 0; i < mLoadMoreEntries.size(); i++) {
                WeakReference<Endless> weakReference = mLoadMoreEntries.get(i);
                Endless endless = weakReference.get();
                if (endless == null || endless.getRecyclerView() == null || endless.getRecyclerView().equals(recyclerView)) {
                    mLoadMoreEntries.remove(i);
                    i--;
                }
            }
        }
    }

    public static Endless applyTo(RecyclerView recyclerView, View loadMoreView) {
        Endless endless;
        if (mLoadMoreEntries == null) {
            mLoadMoreEntries = new ArrayList<>();
        } else {
            for (int i = 0; i < mLoadMoreEntries.size(); i++) {
                WeakReference<Endless> weakReference = mLoadMoreEntries.get(i);
                endless = weakReference.get();
                if (endless == null || endless.getRecyclerView() == null) {
                    mLoadMoreEntries.remove(i);
                    i--;
                } else if (endless.getRecyclerView().equals(recyclerView)) {
                    return endless;
                }
            }
        }
        endless = new Endless(recyclerView, loadMoreView);
        mLoadMoreEntries.add(new WeakReference<>(endless));
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter != null) {
            endless.setAdapter(adapter);
        }
        return endless;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private Endless(final RecyclerView recyclerView, View loadMoreView) {
        this.recyclerView = recyclerView;
        this.loadMoreView = loadMoreView;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (!(adapter instanceof EndlessAdapter)) {
            setAdapter(adapter);
        }
        recyclerView.addOnScrollListener(listener = new EndlessScrollListener() {
            @Override
            public void onLoadMore(final int currentPage) {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (loadMoreAvailable && loadMoreListener != null && !mAdapter.isLoading()) {
                            loadMoreListener.onLoadMore(currentPage);
                            mAdapter.setLoading(true);
                        }
                    }
                });

            }
        });

    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }


    public void loadMoreComplete() {
        mAdapter.setLoading(false);
        listener.setLoading(false);
    }

    public boolean isLoadMoreAvailable() {
        return loadMoreAvailable;
    }

    public void setLoadMoreAvailable(boolean loadMoreAvailable) {
        this.loadMoreAvailable = loadMoreAvailable;
    }


    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter instanceof EndlessAdapter) {
            recyclerView.setAdapter(adapter);
        }
        recyclerView.setAdapter(EndlessAdapter.wrap(adapter, loadMoreView));
        mAdapter = (EndlessAdapter) recyclerView.getAdapter();
    }

    public interface LoadMoreListener {
        void onLoadMore(int page);
    }
}
