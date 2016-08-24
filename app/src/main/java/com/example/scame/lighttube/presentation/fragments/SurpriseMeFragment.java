package com.example.scame.lighttube.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.scame.lighttube.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurpriseMeFragment extends BaseFragment {

    @BindView(R.id.categories_lv) ListView categoriesListView;

    private ArrayAdapter<String> arrayAdapter;

    private SurpriseMeListener listener;

    public interface SurpriseMeListener {

        void onCategoryItemClick(String category);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SurpriseMeListener) {
            listener = (SurpriseMeListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.categories_layout, container, false);

        ButterKnife.bind(this, fragmentView);

        arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.category_item);
        arrayAdapter.addAll(getResources().getStringArray(R.array.category_items));
        categoriesListView.setAdapter(arrayAdapter);

        categoriesListView.setOnItemClickListener((adapterView, view, position, id) ->
                listener.onCategoryItemClick(arrayAdapter.getItem(position)));

        return fragmentView;
    }
}
