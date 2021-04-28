package com.geekb.uscfilms;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DetailActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mCastRv;
    private RecyclerView mReviewsRv;
    private RecyclerView mRecommendedRv;
    private CastRvAdapter mCastRvAdapter;
    private ReviewsRvAdapter mReviewsRvAdapter;
    private RecommendedRvAdapter mRecommendedRvAdapter;
    private List<VideoData> mCastDatas = new ArrayList<>();
    private List<ReviewData> mReviewsDatas = new ArrayList<>();
    private List<VideoData> mRecommendedDatas = new ArrayList<>();
    private VideoData data;
    private VideoDetailData detailData = new VideoDetailData();

    private TextView title;
    private ReadMoreTextView overview;
    private TextView genres;
    private TextView year;
    private ImageView add;
    private ImageView facebook;
    private ImageView twitter;
    private boolean isAdd;
    private ViewGroup cast_layout;
    private ViewGroup reviews_layout;

    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initData();
        initView();
    }

    void initData() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        data = bundle.getParcelable("data");

        MainManager.getInstance().getNetService().getVideo(data.Media_type, data.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoDetailData>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<VideoDetailData> datas) {
                        if (datas != null) {
                            if (datas.size() == 1) {
                                detailData = datas.get(0);
                            } else if (datas.size() == 2) {
                                detailData = datas.get(1);
                                detailData.key = datas.get(0).key;
                            }
                            updateText();
                        }

                    }
                });

        MainManager.getInstance().getNetService().getCast(data.Media_type, data.id)
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
                        if (datas != null && datas.size()>0) {
                            if (datas.size() > 6) {
                                datas = datas.subList(0, 6);
                            }
                            mCastDatas.addAll(datas);
                            mCastRvAdapter.setList(mCastDatas);
                            mCastRvAdapter.notifyDataSetChanged();
                            cast_layout.setVisibility(View.VISIBLE);
                        }else {
                            cast_layout.setVisibility(View.GONE);
                        }

                    }
                });

        MainManager.getInstance().getNetService().getReviews(data.Media_type, data.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ReviewData>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<ReviewData> datas) {
                        if (datas != null && datas.size()>0) {
                            if (datas.size() > 3) {
                                datas = datas.subList(0, 3);
                            }
                            mReviewsDatas.addAll(datas);
                            mReviewsRvAdapter.setList(mReviewsDatas);
                            mReviewsRvAdapter.notifyDataSetChanged();
                            reviews_layout.setVisibility(View.VISIBLE);
                        }else {
                            reviews_layout.setVisibility(View.GONE);
                        }

                    }
                });

        MainManager.getInstance().getNetService().getSimilar(data.Media_type, data.id)
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
                        if (datas != null) {
                            if (datas.size() > 10) {
                                datas = datas.subList(0, 10);
                            }
                            mRecommendedDatas.addAll(datas);
                            mRecommendedRvAdapter.setList(mRecommendedDatas);
                            mRecommendedRvAdapter.notifyDataSetChanged();
                        }

                    }
                });
    }

    void updateText() {
        if (title == null) return;
        title.setText(detailData.title);
        overview.setText(detailData.overview);
        String gen = "";
        if (detailData.genres != null) {
            for (VideoDetailData.GenresData genresData : detailData.genres) {
                gen += genresData.name;
                gen += ",";
            }
        }
        if (!TextUtils.isEmpty(gen)) {
            gen = gen.substring(0, gen.length() - 1);
        }
        genres.setText(gen);
        String y = "";
        if (!TextUtils.isEmpty(data.release_date)) {
            String[] release_date = data.release_date.split("-");
            y = release_date[0];
        }
        year.setText(y);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(detailData.key, 0);
            }
        });
    }


    void initView() {

        mCastRv = findViewById(R.id.rv_cast);
        cast_layout = findViewById(R.id.cast_layout);
        reviews_layout = findViewById(R.id.reviews_layout);
        title = findViewById(R.id.title);
        overview = findViewById(R.id.tv_overview);
        genres = findViewById(R.id.tv_genres);
        year = findViewById(R.id.tv_year);
        add = findViewById(R.id.add);
        facebook = findViewById(R.id.share_facebook);
        twitter = findViewById(R.id.share_twitter);
        facebook.setOnClickListener(this);
        add.setOnClickListener(this);
        twitter.setOnClickListener(this);
        youTubePlayerView = findViewById(R.id.youtube_player_view);

        title.setText(detailData.title);
        overview.setText(detailData.overview);


        if (MainManager.getInstance().checkIsInWatchlist(data.id)) {
            add.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
            isAdd = false;
        } else {
            add.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
            isAdd = true;
        }

        String gen = "";
        if (detailData.genres != null) {
            for (VideoDetailData.GenresData genresData : detailData.genres) {
                gen += genresData.name;
                gen += ",";
            }
        }
        if (!TextUtils.isEmpty(gen)) {
            gen = gen.substring(0, gen.length() - 1);
        }
        genres.setText(gen);
        String y = "";
        if (!TextUtils.isEmpty(data.release_date)) {
            String[] release_date = data.release_date.split("-");
            y = release_date[0];
        }
        year.setText(y);
        GridLayoutManager layoutManage = new GridLayoutManager(this, 3);
        mCastRvAdapter = new CastRvAdapter(mCastDatas);
        mCastRv.setLayoutManager(layoutManage);
        mCastRv.setAdapter(mCastRvAdapter);

        mReviewsRv = findViewById(R.id.rv_reviews);
        mReviewsRvAdapter = new ReviewsRvAdapter(mReviewsDatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewsRv.setLayoutManager(layoutManager);
        mReviewsRv.setAdapter(mReviewsRvAdapter);
        mReviewsRvAdapter.setOnItemClickListener(new ReviewsRvAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ReviewData data) {
                Intent intent = new Intent(DetailActivity.this, ReviewsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

        mRecommendedRv = findViewById(R.id.rv_recommend);
        mRecommendedRvAdapter = new RecommendedRvAdapter(mCastDatas);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecommendedRv.setLayoutManager(layoutManager1);
        mRecommendedRv.setAdapter(mRecommendedRvAdapter);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.share_facebook:
                String url1 = "https://www.facebook.com/sharer/sharer.php?u=http%3A%2F%2Fwww.themoviedb.org%2F" + data.Media_type + "%2F" + data.id;
                MainManager.getInstance().openBrowser(this, url1);
                break;
            case R.id.share_twitter:
                String url2 = "https://twitter.com/intent/tweet?url=http%3A%2F%2Fwww.themoviedb.org%2F" + data.Media_type + "%2F" + data.id;
                MainManager.getInstance().openBrowser(this, url2);
                break;
            case R.id.add:
                isAdd = !isAdd;
                String name = TextUtils.isEmpty(data.title)? data.name:data.title;
                if (isAdd) {
                    MainManager.getInstance().removeWatchlistData(data);
                    Toast.makeText(this, name + " was removed from Watchlist", Toast.LENGTH_SHORT).show();
                    add.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);

                } else {
                    MainManager.getInstance().addWatchlistData(data);
                    Toast.makeText(this, name + " was added to Watchlist", Toast.LENGTH_SHORT).show();
                    add.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
                }
                break;
        }
    }
}
