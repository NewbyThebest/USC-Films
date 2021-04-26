package com.geekb.uscfilms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends BaseFagment{
    private SearchView searchView;
    private RecyclerView mRv;
    private SearchRvAdapter mRvAdapter;
    private List<VideoData> mList = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }
    void initData(){
        mList.add(new VideoData());
        mList.add(new VideoData());
        mList.add(new VideoData());
        mList.add(new VideoData());
        mList.add(new VideoData());
        mList.add(new VideoData());
        mList.add(new VideoData());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        initView(root);
        return root;
    }
    void initView(View root){
        searchView = root.findViewById(R.id.search);
        mRv = root.findViewById(R.id.rv);
        mRvAdapter = new SearchRvAdapter(mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mRvAdapter);
        ImageView icon = searchView.findViewById(R.id.search_mag_icon);
        ImageView close = searchView.findViewById(R.id.search_close_btn);
        AutoCompleteTextView textView = searchView.findViewById(R.id.search_src_text);
        textView.setHintTextColor(getResources().getColor(R.color.white_adadad));
        textView.setTextColor(getResources().getColor(R.color.white));
        icon.setImageResource(R.drawable.ic_baseline_search_24);
        close.setImageResource(R.drawable.baseline_close_grey_300_24dp);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}
