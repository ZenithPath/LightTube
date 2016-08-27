package com.example.scame.lighttube.data.entities.search;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CategoryPairs implements Iterable<Map.Entry<String, String>> {

    private Map<String, String> categoryMap = new HashMap<>();

    public void put(String key, String value) {
        categoryMap.put(key, value);
    }

    public String get(String key) {
        return categoryMap.get(key);
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {
        return categoryMap.entrySet().iterator();
    }
}
