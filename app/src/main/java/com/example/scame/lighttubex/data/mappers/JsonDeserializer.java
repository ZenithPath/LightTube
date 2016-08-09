package com.example.scame.lighttubex.data.mappers;


import com.example.scame.lighttubex.data.entities.search.AutocompleteEntity;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

// TODO: replace with okhttp deserializer

public class JsonDeserializer {

    public AutocompleteEntity convert(ResponseBody responseBody) {

        AutocompleteEntity entity = new AutocompleteEntity();

        try {
            JSONArray jsonArray = new JSONArray(responseBody.string());
            entity.setSearchQuery(jsonArray.getString(0));
            JSONArray jsonInnerArray = jsonArray.getJSONArray(1);
            List<String> strings = new ArrayList<>();

            for (int i = 0; i < jsonInnerArray.length(); i++) {
                strings.add(jsonInnerArray.getString(i));
            }

            entity.setItems(strings);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return entity;
    }
}
