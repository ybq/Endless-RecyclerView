package com.github.ybq.endless_recyclerview;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 */
public class DataProvider {

    static Random random = new Random(25);

    public static class Item {
        public int data;

        public Item(int data) {
            this.data = data;
        }

        public int color = Color.argb(255,
                random.nextInt(155) + 100,
                random.nextInt(155) + 100,
                random.nextInt(155) + 100
        );
    }

    public static List<Item> request(int lastId, int count) {
        ArrayList<Item> data = new ArrayList<>();
        int position = lastId + 1;
        int last = position + count;
        for (; position < last; position++) {
            data.add(new Item(position));
        }
        return data;
    }
}
