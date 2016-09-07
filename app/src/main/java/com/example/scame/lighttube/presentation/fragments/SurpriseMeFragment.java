package com.example.scame.lighttube.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.scame.lighttube.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurpriseMeFragment extends BaseFragment {

    @BindView(R.id.categories_lv) ListView categoriesListView;

    @BindView(R.id.categories_toolbar) Toolbar toolbar;

    @BindView(R.id.durations_spinner) Spinner durationsSpinner;

    private ArrayAdapter<String> categoriesArrayAdapter;

    private ArrayAdapter<CharSequence> durationsArrayAdapter;

    private SurpriseMeListener listener;

    public interface SurpriseMeListener {

        void onCategoryItemClick(String category, String duration);
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

        categoriesArrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.category_item);
        categoriesArrayAdapter.addAll(getResources().getStringArray(R.array.category_items));
        categoriesListView.setAdapter(categoriesArrayAdapter);

        categoriesListView.setOnItemClickListener((adapterView, view, position, id) ->
            listener.onCategoryItemClick(categoriesArrayAdapter.getItem(position),
                    durationsSpinner.getSelectedItem().toString())
        );

        return fragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        configureDurationSpinner();
    }

    private void configureDurationSpinner() {
        durationsArrayAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.video_durations_array, android.R.layout.simple_spinner_item);
        durationsArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        durationsSpinner.setAdapter(durationsArrayAdapter);
    }
}
