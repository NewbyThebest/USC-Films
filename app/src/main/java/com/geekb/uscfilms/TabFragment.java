package com.geekb.uscfilms;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.geekb.uscfilms.holder.MZHolderCreator;
import com.geekb.uscfilms.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;

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
    private int mTag = Constant.MOVIE;
    private TextView mTMDB;

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
        mTag = getArguments().getInt("tag");
        if (mTag == Constant.MOVIE) {
            mBanners.addAll(MainManager.getInstance().getMovieBanners());
            mTopList.addAll(MainManager.getInstance().getMovieTops());
            mPopularList.addAll(MainManager.getInstance().getMoviePopulars());
        } else {
            mBanners.addAll(MainManager.getInstance().getTvBanners());
            mTopList.addAll(MainManager.getInstance().getTvTops());
            mPopularList.addAll(MainManager.getInstance().getTvPopulars());
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tab, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMZBanner.start();
    }

    void initView(View root) {
        mTopRv = root.findViewById(R.id.rv_top);
        mPopularRv = root.findViewById(R.id.rv_popular);
        mPopularRv = root.findViewById(R.id.rv_popular);
        mTMDB = root.findViewById(R.id.tv_tmdb);
        mTopRvAdapter = new TopRvAdapter(mTopList);
        mPopularRvAdapter = new TopRvAdapter(mPopularList);
        mMZBanner = root.findViewById(R.id.banner);

        mTMDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org"));
                startActivity(browserIntent);
            }
        });
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
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", data);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }

            @Override
            public void onMoreClick(VideoData data, View view) {
                popupMenu = new PopupMenu(getActivity(), view);


                //将 R.menu.menu_main 菜单资源加载到popup中
                getActivity().getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                boolean isAdd;
                if (MainManager.getInstance().checkIsInWatchlist(data.id)) {
                    popupMenu.getMenu().getItem(3).setTitle("remove");
                    isAdd = false;
                } else {
                    popupMenu.getMenu().getItem(3).setTitle("add");
                    isAdd = true;
                }


                //为popupMenu选项添加监听器
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tmdb:
                                String url = "https://www.themoviedb.org/" + data.Media_type + "/" + data.id;
                                MainManager.getInstance().openBrowser(getActivity(),url);
                                break;
                            case R.id.facebook:
                                String url1 = "https://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2Fwww.themoviedb.org%2F"+data.Media_type+"%2F"+data.id;
                                MainManager.getInstance().openBrowser(getActivity(),url1);
                                break;
                            case R.id.twitter:
                                String url2 = "https://twitter.com/intent/tweet?url=http%3A%2F%2Fwww.themoviedb.org%2F"+data.Media_type+"%2F"+data.id;
                                MainManager.getInstance().openBrowser(getActivity(),url2);
                                break;
                            case R.id.add:
                                String name = TextUtils.isEmpty(data.title)? data.name:data.title;
                                if (isAdd) {
                                    MainManager.getInstance().addWatchlistData(data);
                                    Toast.makeText(getActivity(),name + " was added to Watchlist",Toast.LENGTH_SHORT).show();

                                } else {
                                    MainManager.getInstance().removeWatchlistData(data);
                                    Toast.makeText(getActivity(),name + " was removed from Watchlist",Toast.LENGTH_SHORT).show();
                                }
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
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", data);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }

            @Override
            public void onMoreClick(VideoData data, View view) {
                popupMenu = new PopupMenu(getActivity(), view);
                //将 R.menu.menu_main 菜单资源加载到popup中
                getActivity().getMenuInflater().inflate(R.menu.menu_main, popupMenu.getMenu());
                boolean isAdd;
                if (MainManager.getInstance().checkIsInWatchlist(data.id)) {
                    popupMenu.getMenu().getItem(3).setTitle("remove");
                    isAdd = false;
                } else {
                    popupMenu.getMenu().getItem(3).setTitle("add");
                    isAdd = true;
                }
                //为popupMenu选项添加监听器
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tmdb:
                                String url = "https://www.themoviedb.org/" + data.Media_type + "/" + data.id;
                                MainManager.getInstance().openBrowser(getActivity(),url);
                                break;
                            case R.id.facebook:
                                String url1 = "https://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2Fwww.themoviedb.org%2F"+data.Media_type+"%2F"+data.id;
                                MainManager.getInstance().openBrowser(getActivity(),url1);
                                break;
                            case R.id.twitter:
                                String url2 = "https://twitter.com/intent/tweet?url=http%3A%2F%2Fwww.themoviedb.org%2F"+data.Media_type+"%2F"+data.id;
                                MainManager.getInstance().openBrowser(getActivity(),url2);
                                break;
                            case R.id.add:
                                String name = TextUtils.isEmpty(data.title)? data.name:data.title;
                                if (isAdd) {
                                    MainManager.getInstance().addWatchlistData(data);
                                    Toast.makeText(getActivity(),name + " was added to Watchlist",Toast.LENGTH_SHORT).show();

                                } else {
                                    MainManager.getInstance().removeWatchlistData(data);
                                    Toast.makeText(getActivity(),name + " was removed from Watchlist",Toast.LENGTH_SHORT).show();
                                }
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
        private ImageView mImageView1;
        private ImageView mImageView2;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView1 = (ImageView) view.findViewById(R.id.banner_image);
            mImageView2 = (ImageView) view.findViewById(R.id.banner_image_bg);
            return view;
        }

        @Override
        public void onBind(Context context, int position, VideoData data) {
            mImageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data", data);
                    intent.putExtra("bundle", bundle);
                    startActivity(intent);
                }
            });
            Glide.with(mImageView1.getContext()).load(data.poster_path)
                    .placeholder(R.drawable.img_default)
                    .transform(new BlurTransformation())
                    .into(mImageView2);
            Glide.with(mImageView1.getContext()).load(data.poster_path)
                    .placeholder(R.drawable.img_default)
                    .into(mImageView1);
        }


    }
}
