package com.github.ybq.endless_recyclerview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.github.ybq.endless.Endless;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataProvider.Item> data;
    private Endless endless;
    private TextAdapter textAdapter;
    private AsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == textAdapter.getItemCount()) {
                    return layoutManager.getSpanCount();
                }
                return 1;
            }
        });
        View loadingView = View.inflate(this, R.layout.layout_loading, null);
        loadingView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        endless = Endless.applyTo(recyclerView,
                loadingView
        );
        endless.setAdapter(textAdapter = new TextAdapter());
        endless.setLoadMoreListener(new Endless.LoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                loadData(page);
            }
        });
        loadData(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (asyncTask != null) {
            asyncTask.cancel(true);
        }
    }

    private void loadData(final int page) {
        asyncTask = new AsyncTask<Integer, Integer, List<DataProvider.Item>>() {
            @Override
            protected List<DataProvider.Item> doInBackground(Integer[] integers) {
                if (page != 0) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return DataProvider.request(integers[0], 71);
            }

            @Override
            protected void onPostExecute(List<DataProvider.Item> integers) {
                data = integers;
                if (page == 0) {
                    textAdapter.setData(data);
                } else {
                    textAdapter.addData(data);
                    endless.loadMoreComplete();

                }
                super.onPostExecute(integers);
            }
        }.execute(textAdapter.getLastId());
    }
}
