package com.geekb.uscfilms;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geekb.uscfilms.holder.MZHolderCreator;
import com.geekb.uscfilms.holder.MZViewHolder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TabFragment extends BaseFagment {
    private MZBannerView mMZBanner;
    private List<VideoData> mBanners = new ArrayList<>();
    private RecyclerView mTopRv;
    private RecyclerView mPopularRv;
    private TopRvAdapter mTopRvAdapter;
    private TopRvAdapter mPopularRvAdapter;
    private List<VideoData> mTopList = new ArrayList<>();
    private List<VideoData> mPopularList = new ArrayList<>();
    private PopupMenu popupMenu;

    public static TabFragment newInstance(int tag) {
        TabFragment tabFragment = new TabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tag", tag);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    void initData() {
        mBanners.add(new VideoData());
        mBanners.add(new VideoData());
        mBanners.add(new VideoData());
        mBanners.add(new VideoData());
        mBanners.add(new VideoData());
        mBanners.add(new VideoData());

        mTopList.add(new VideoData());
        mTopList.add(new VideoData());
        mTopList.add(new VideoData());
        mTopList.add(new VideoData());
        mTopList.add(new VideoData());
        mTopList.add(new VideoData());
        mTopList.add(new VideoData());
        mTopList.add(new VideoData());
        mTopList.add(new VideoData());
        mTopList.add(new VideoData());


        mPopularList.add(new VideoData());
        mPopularList.add(new VideoData());
        mPopularList.add(new VideoData());
        mPopularList.add(new VideoData());
        mPopularList.add(new VideoData());
        mPopularList.add(new VideoData());
        mPopularList.add(new VideoData());
        mPopularList.add(new VideoData());
        mPopularList.add(new VideoData());
        mPopularList.add(new VideoData());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tab, container, false);
        initView(root);
        return root;
    }

    void initView(View root) {
        mTopRv = root.findViewById(R.id.rv_top);
        mPopularRv = root.findViewById(R.id.rv_popular);
        mTopRvAdapter = new TopRvAdapter(mTopList);
        mPopularRvAdapter = new TopRvAdapter(mPopularList);
        mMZBanner = root.findViewById(R.id.banner);
        // 设置数据
        mMZBanner.setPages(mBanners, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mTopRvAdapter.setOnItemClickListener(new TopRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VideoData data) {

            }

            @Override
            public void onMoreClick(VideoData data,View view) {
                popupMenu = new PopupMenu(getActivity(), view);


                //将 R.menu.menu_main 菜单资源加载到popup中
                getActivity().getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                //为popupMenu选项添加监听器
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tmdb:

                                break;
                            case R.id.facebook:

                                break;

                            case R.id.twitter:

                                break;
                            case R.id.add:

                                break;
                            default:
                        }
                        popupMenu.dismiss();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        mPopularRvAdapter.setOnItemClickListener(new TopRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(VideoData data) {

            }

            @Override
            public void onMoreClick(VideoData data, View view) {
                popupMenu = new PopupMenu(getActivity(), view);
                //将 R.menu.menu_main 菜单资源加载到popup中
                getActivity().getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                //为popupMenu选项添加监听器
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tmdb:

                                break;
                            case R.id.facebook:

                                break;

                            case R.id.twitter:

                                break;
                            case R.id.add:

                                break;
                            default:
                        }
                        popupMenu.dismiss();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        mTopRv.setLayoutManager(layoutManager1);
        mPopularRv.setLayoutManager(layoutManager2);
        mTopRv.setAdapter(mTopRvAdapter);
        mPopularRv.setAdapter(mPopularRvAdapter);


    }

    public class BannerViewHolder implements MZViewHolder<VideoData> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, VideoData data) {

            Glide.with(mImageView.getContext()).load(data.imgUrl).placeholder(R.drawable.img_default).into(mImageView);
        }


    }
}
