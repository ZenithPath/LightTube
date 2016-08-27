package com.example.scame.lighttube.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

    @BindView(R.id.categories_toolbar) Toolbar toolbar;

    private ArrayAdapter<String> arrayAdapter;

    private SurpriseMeListener listener;

    private String chosenCategory;

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

        categoriesListView.setOnItemClickListener((adapterView, view, position, id) -> {
            chosenCategory = arrayAdapter.getItem(position);
            listener.onCategoryItemClick(chosenCategory);
        });

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }
}
