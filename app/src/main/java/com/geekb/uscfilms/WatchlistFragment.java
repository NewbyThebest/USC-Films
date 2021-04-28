package com.geekb.uscfilms;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.ItemTouchHelper.Callback;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WatchlistFragment extends BaseFagment {
    private RecyclerView mRecyclerView;
    private WatchlistRvAdapter mWatchlistRvAdapter;
    private List<VideoData> mDataList = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;
    private TextView emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_watchlist, container, false);
        initData();
        initView(root);
        return root;
    }

    void initView(View view) {
        mRecyclerView = view.findViewById(R.id.rv);
        emptyView = view.findViewById(R.id.empty_view);
        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), 3);
        mWatchlistRvAdapter = new WatchlistRvAdapter(mDataList);
        mRecyclerView.setLayoutManager(layoutManage);
        mRecyclerView.setAdapter(mWatchlistRvAdapter);
        if (mDataList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }

        mWatchlistRvAdapter.setOnItemClickListener(new WatchlistRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VideoData data) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data",data);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }

            @Override
            public void onItemRemoveClick(VideoData data) {
                mDataList.remove(data);
                mWatchlistRvAdapter.setList(mDataList);
                mWatchlistRvAdapter.notifyDataSetChanged();
                MainManager.getInstance().removeWatchlistData(data);
                if (mDataList.isEmpty()) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
                String name = TextUtils.isEmpty(data.title)? data.name:data.title;
                Toast.makeText(getActivity(),name + " was removed from Watchlist",Toast.LENGTH_SHORT).show();
            }
        });

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

    void initData() {
        mDataList = MainManager.getInstance().getWatchlistData();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWatchlistRvAdapter.notifyDataSetChanged();
    }
}
