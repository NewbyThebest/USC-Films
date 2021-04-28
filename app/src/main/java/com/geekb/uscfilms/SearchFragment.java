package com.geekb.uscfilms;

import android.content.Intent;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchFragment extends BaseFagment {
    private SearchView searchView;
    private RecyclerView mRv;
    private TextView emptyView;
    private SearchRvAdapter mRvAdapter;
    private List<VideoData> mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        initView(root);
        return root;
    }

    void initView(View root) {
        searchView = root.findViewById(R.id.search);
        mRv = root.findViewById(R.id.rv);
        emptyView = root.findViewById(R.id.empty_view);
        mRvAdapter = new SearchRvAdapter(mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRv.setLayoutManager(layoutManager);
        mRv.setAdapter(mRvAdapter);
        mRvAdapter.setOnItemClickListener(new SearchRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VideoData data) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data",data);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
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
                mList.clear();
                MainManager.getInstance().getNetService().getSearch(newText)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<List<VideoData>>() {
                            @Override
                            public void onCompleted() {


                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onNext(List<VideoData> datas) {
                                mList.addAll(datas);
                                mRvAdapter.setList(mList);
                                mRvAdapter.notifyDataSetChanged();
                                if (mList.isEmpty()) {
                                    emptyView.setVisibility(View.VISIBLE);
                                } else {
                                    emptyView.setVisibility(View.GONE);
                                }
                            }
                        });
                return false;
            }
        });
    }
}
