package com.geekb.uscfilms;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HomeFragment extends BaseFagment implements View.OnClickListener {

    private TextView mTvMovie;
    private TextView mTvTV;
    private TabFragment mMovieFragment;
    private TabFragment mTVFragment;
    private int currentTag = 0;

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
        mTvMovie.setOnClickListener(this);
        mTvTV.setOnClickListener(this);
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
