package com.github.ybq.endless_recyclerview;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.ybq.endless.Endless;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Integer> data;
    private Endless endless;
    private TextAdapter textAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(textAdapter = new TextAdapter());
        View loadingView = View.inflate(this, R.layout.layout_loading, null);
        endless = Endless.applyTo(recyclerView,
                loadingView
        );
        endless.setLoadMoreListener(new Endless.LoadMoreListener() {
            @Override
            public void onLoadMore(int page) {
                loadData(page);
            }
        });
        loadData(0);
    }

    private void loadData(final int page) {
        new AsyncTask<Integer, Integer, List<Integer>>() {
            @Override
            protected List<Integer> doInBackground(Integer[] integers) {
                if (page != 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return DataProvider.request(integers[0], 28);
            }

            @Override
            protected void onPostExecute(List<Integer> integers) {
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
