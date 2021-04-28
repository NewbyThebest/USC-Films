package com.geekb.uscfilms;


import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HomeFragment extends BaseFagment implements View.OnClickListener {

    private TextView mTvMovie;
    private TextView mTvTV;
    private TabFragment mMovieFragment;
    private TabFragment mTVFragment;
    private int currentTag = 0;
    private ViewGroup mWaiting;
    private ViewGroup layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initView(root);
        return root;
    }

    void initView(View view) {
        mTvMovie = view.findViewById(R.id.tv_movie);
        mTvTV = view.findViewById(R.id.tv_TV);
        mWaiting = view.findViewById(R.id.waiting);
        layout = view.findViewById(R.id.layout);
        mTvMovie.setOnClickListener(this);
        mTvTV.setOnClickListener(this);
        if (MainManager.getInstance().isShowWaiting){
            MainManager.getInstance().isShowWaiting = false;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    layout.setVisibility(View.VISIBLE);
                    mWaiting.setVisibility(View.GONE);

                }
            },2000);
        }else {
            layout.setVisibility(View.VISIBLE);
            mWaiting.setVisibility(View.GONE);
        }

        jumpToMoviePage();
    }

    void jumpToMoviePage() {
        mMovieFragment = (TabFragment) getChildFragmentManager().findFragmentByTag("movie");
        if (mMovieFragment == null) {
            mMovieFragment = TabFragment.newInstance(Constant.MOVIE);
        }
        mTVFragment = (TabFragment) getChildFragmentManager().findFragmentByTag("tv");
        if (mTVFragment != null) {
            getChildFragmentManager().beginTransaction().hide(mTVFragment)
                    .add(R.id.fragment_container, mMovieFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        } else {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mMovieFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    void jumpToTVPage() {
        mTVFragment = (TabFragment) getChildFragmentManager().findFragmentByTag("tv");
        if (mTVFragment == null) {
            mTVFragment = TabFragment.newInstance(Constant.TV);
        }
        mMovieFragment = (TabFragment) getChildFragmentManager().findFragmentByTag("movie");
        if (mMovieFragment != null) {
            getChildFragmentManager().beginTransaction().hide(mMovieFragment)
                    .add(R.id.fragment_container, mTVFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        } else {
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, mTVFragment)
                    .addToBackStack(null)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_movie:
                if (currentTag != 0) {
                    currentTag = 0;
                    jumpToMoviePage();
                    mTvMovie.setTextColor(getResources().getColor(R.color.white));
                    mTvTV.setTextColor(getResources().getColor(R.color.blue_a700));
                }
                break;
            case R.id.tv_TV:
                if (currentTag != 1) {
                    currentTag = 1;
                    jumpToTVPage();
                    mTvTV.setTextColor(getResources().getColor(R.color.white));
                    mTvMovie.setTextColor(getResources().getColor(R.color.blue_a700));
                }
                break;
            default:
        }
    }
}
