package com.geekb.uscfilms;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WatchlistFragment extends BaseFagment{
    private RecyclerView mRecyclerView;
    private WatchlistRvAdapter mWatchlistRvAdapter;
    private List<VideoData> mDataList = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_watchlist, container, false);
        initView(root);
        return root;
    }

    void initView(View view){
        mRecyclerView = view.findViewById(R.id.rv);
        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 3);
        mWatchlistRvAdapter = new WatchlistRvAdapter(mDataList);
        mRecyclerView.setLayoutManager(layoutManage);
        mRecyclerView.setAdapter(mWatchlistRvAdapter);
        mItemTouchHelper = new ItemTouchHelper(new Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);

            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mDataList, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mDataList, i, i - 1);
                    }
                }
                mWatchlistRvAdapter.notifyItemMoved(fromPosition, toPosition);
                return true;

            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    void initData(){
        mDataList.add(new VideoData());
        mDataList.add(new VideoData());
        mDataList.add(new VideoData());
        mDataList.add(new VideoData());
        mDataList.add(new VideoData());
        mDataList.add(new VideoData());
        mDataList.add(new VideoData());
        mDataList.add(new VideoData());
    }
}
