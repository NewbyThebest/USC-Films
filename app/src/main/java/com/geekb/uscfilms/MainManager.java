package com.geekb.uscfilms;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainManager {
    private static final String BASE_IP = "192.168.10.114";
    private static MainManager manager;
    private ApiInterface mNetService;
    private List<VideoData> movieBanners = new ArrayList<>();
    private List<VideoData> tvBanners = new ArrayList<>();
    public boolean isShowWaiting = true;

    public List<VideoData> getWatchlistData() {
        return watchlistData;
    }

    public void setWatchlistData(List<VideoData> watchlistData) {
        this.watchlistData.addAll(watchlistData);
    }

    public void addWatchlistData(VideoData data) {
        this.watchlistData.add(data);
        Gson gson = new Gson();
        String strJson = gson.toJson(watchlistData);
        MMKV.defaultMMKV().encode("watchlist", strJson);
    }

    public void removeWatchlistData(VideoData data) {
        for (VideoData videoData : watchlistData) {
            if (videoData.id.equals(data.id)) {
                this.watchlistData.remove(videoData);
                Gson gson = new Gson();
                String strJson = gson.toJson(watchlistData);
                MMKV.defaultMMKV().encode("watchlist", strJson);
                break;
            }
        }
    }

    public boolean checkIsInWatchlist(String id) {
        for (VideoData data : watchlistData) {
            if (id.equals(data.id)) {
                return true;
            }
        }
        return false;
    }

    public void openBrowser(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }


    private List<VideoData> watchlistData = new ArrayList<>();

    public List<VideoData> getMovieTops() {
        return movieTops;
    }

    public void setMovieTops(List<VideoData> movieTops) {
        for (VideoData videoData : movieTops) {
            videoData.Media_type = "movie";
        }
        this.movieTops.addAll(movieTops);
    }

    public List<VideoData> getTvTops() {
        return tvTops;
    }

    public void setTvTops(List<VideoData> tvTops) {
        for (VideoData videoData : tvTops) {
            videoData.Media_type = "tv";
        }
        this.tvTops.addAll(tvTops);
    }

    public List<VideoData> getMoviePopulars() {
        return moviePopulars;
    }

    public void setMoviePopulars(List<VideoData> moviePopulars) {
        for (VideoData videoData : moviePopulars) {
            videoData.Media_type = "movie";
        }
        this.moviePopulars.addAll(moviePopulars);
    }

    public List<VideoData> getTvPopulars() {
        return tvPopulars;
    }

    public void setTvPopulars(List<VideoData> tvPopulars) {
        for (VideoData videoData : tvPopulars) {
            videoData.Media_type = "tv";
        }
        this.tvPopulars.addAll(tvPopulars);
    }

    private List<VideoData> movieTops = new ArrayList<>();
    private List<VideoData> tvTops = new ArrayList<>();
    private List<VideoData> moviePopulars = new ArrayList<>();
    private List<VideoData> tvPopulars = new ArrayList<>();

    private MainManager() {

    }

    public static MainManager getInstance() {
        if (manager == null) {
            synchronized (MainManager.class) {
                if (manager == null) {
                    manager = new MainManager();
                }
            }
        }
        return manager;
    }

    public void initRetrofit() {
        String BASE_URL = "http://" + BASE_IP;
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Response response = chain.proceed(chain.request());
                        return response;
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        mNetService = retrofit.create(ApiInterface.class);
    }

    public ApiInterface getNetService() {
        return mNetService;
    }

    public List<VideoData> getMovieBanners() {
        return movieBanners;
    }

    public void setMovieBanners(List<VideoData> movieBanners) {
        this.movieBanners.addAll(movieBanners);
    }

    public List<VideoData> getTvBanners() {
        return tvBanners;
    }

    public void setTvBanners(List<VideoData> tvBanners) {
        this.tvBanners.addAll(tvBanners);
    }
}
