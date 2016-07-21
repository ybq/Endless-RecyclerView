package com.github.ybq.endless_recyclerview;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class DataProvider {

    public static List<Integer> request(int lastId, int count) {
        ArrayList<Integer> data = new ArrayList<>();
        int start = lastId + 1;
        int last = start + count;
        for (; start < last; start++) {
            data.add(start);
        }
        return data;
    }
}
