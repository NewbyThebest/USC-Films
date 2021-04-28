package com.geekb.uscfilms;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.tencent.mmkv.MMKV;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBannerData();
        initTop();
        initPopular();
        initWatchlist();
    }

    void initWatchlist(){
        String jsonStr = MMKV.defaultMMKV().decodeString("watchlist");
        if (!TextUtils.isEmpty(jsonStr)) {
            List<VideoData> watchlist = MainManager.getInstance().getWatchlistData();
            watchlist.clear();
            try {
                Gson gson = new Gson();
                JsonArray array = JsonParser.parseString(jsonStr).getAsJsonArray();
                for (JsonElement jsonElement : array) {
                    watchlist.add(gson.fromJson(jsonElement, VideoData.class));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void initBannerData() {
        MainManager.getInstance().getNetService().getMovieBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoData>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        checkFinish();
                    }

                    @Override
                    public void onNext(List<VideoData> datas) {
                        MainManager.getInstance().setMovieBanners(datas);
                        checkFinish();
                    }
                });

        MainManager.getInstance().getNetService().getTVBanners()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoData>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        checkFinish();
                    }

                    @Override
                    public void onNext(List<VideoData> datas) {
                        MainManager.getInstance().setTvBanners(datas);
                        checkFinish();
                    }
                });
    }

    void initTop() {
        MainManager.getInstance().getNetService().getMovieTop()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoData>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        checkFinish();
                    }

                    @Override
                    public void onNext(List<VideoData> datas) {
                        if (datas.size() > 10) {
                            datas = datas.subList(0, 10);
                        }
                        MainManager.getInstance().setMovieTops(datas);
                        checkFinish();
                    }
                });

        MainManager.getInstance().getNetService().getTVTop()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoData>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        checkFinish();
                    }

                    @Override
                    public void onNext(List<VideoData> datas) {
                        if (datas.size() > 10) {
                            datas = datas.subList(0, 10);
                        }
                        MainManager.getInstance().setTvTops(datas);
                        checkFinish();
                    }
                });
    }

    void initPopular() {
        MainManager.getInstance().getNetService().getMoviePopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoData>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        checkFinish();
                    }

                    @Override
                    public void onNext(List<VideoData> datas) {
                        if (datas.size() > 10) {
                            datas = datas.subList(0, 10);
                        }
                        MainManager.getInstance().setMoviePopulars(datas);
                        checkFinish();
                    }
                });

        MainManager.getInstance().getNetService().getTVPopular()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<VideoData>>() {
                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        checkFinish();
                    }

                    @Override
                    public void onNext(List<VideoData> datas) {
                        if (datas.size() > 10) {
                            datas = datas.subList(0, 10);
                        }
                        MainManager.getInstance().setTvPopulars(datas);
                        checkFinish();
                    }
                });
    }

    private void checkFinish() {
        count++;
        if (count == 6) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
