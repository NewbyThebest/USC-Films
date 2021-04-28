package com.geekb.uscfilms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout mTabHome;
    private LinearLayout mTabSearch;
    private LinearLayout mTabWatchlist;
    private ImageButton mIBHome;
    private ImageButton mIBSearch;
    private ImageButton mIBWatchlist;
    private TextView mTvHome;
    private TextView mTvSearch;
    private TextView mTvWatchlist;

    private ViewPager mViewPager;
    private TabFragmentViewPagerAdapter mAdapter;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initClickListener();
    }

    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.tab_main_viewpager);
        mTabHome = (LinearLayout) findViewById(R.id.tab_home);
        mTabSearch = (LinearLayout) findViewById(R.id.tab_search);
        mTabWatchlist = (LinearLayout) findViewById(R.id.tab_watchlist);
        mIBHome = (ImageButton) findViewById(R.id.ib_home);
        mIBSearch = (ImageButton) findViewById(R.id.ib_search);
        mIBWatchlist = (ImageButton) findViewById(R.id.ib_watchlist);
        mTvHome = (TextView) findViewById(R.id.tv_home);
        mTvSearch = (TextView) findViewById(R.id.tv_search);
        mTvWatchlist = (TextView) findViewById(R.id.tv_watchlist);

        mFragments = new ArrayList<Fragment>();
        Fragment homeFragment = new HomeFragment();
        Fragment searchFragment = new SearchFragment();
        Fragment watchlistFragment = new WatchlistFragment();
        mFragments.add(homeFragment);
        mFragments.add(searchFragment);
        mFragments.add(watchlistFragment);

        mAdapter = new TabFragmentViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //滑动时 改变图标状态
            @Override
            public void onPageSelected(int position) {
                int currentItem = mViewPager.getCurrentItem();
                initTabImage();
                switch (currentItem) {
                    case 0:
                        mIBHome.setBackgroundResource(R.drawable.home_white_24dp);
                        mTvHome.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case 1:
                        mIBSearch.setBackgroundResource(R.drawable.search_white_24dp);
                        mTvSearch.setTextColor(getResources().getColor(R.color.white));
                        break;
                    case 2:
                        mIBWatchlist.setBackgroundResource(R.drawable.history_white_24dp);
                        mTvWatchlist.setTextColor(getResources().getColor(R.color.white));
                        break;
                    default:
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initClickListener() {
        mTabHome.setOnClickListener(this);
        mTabSearch.setOnClickListener(this);
        mTabWatchlist.setOnClickListener(this);
    }

    private void initTabImage() {
        mIBHome.setBackgroundResource(R.drawable.home_blue_a700_24dp);
        mIBSearch.setBackgroundResource(R.drawable.search_blue_a700_24dp);
        mIBWatchlist.setBackgroundResource(R.drawable.history_blue_900_24dp);
        mTvHome.setTextColor(getResources().getColor(R.color.blue_a700));
        mTvSearch.setTextColor(getResources().getColor(R.color.blue_a700));
        mTvWatchlist.setTextColor(getResources().getColor(R.color.blue_a700));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_home:
                setSelect(0);
                break;
            case R.id.tab_search:
                setSelect(1);
                break;
            case R.id.tab_watchlist:
                setSelect(2);
                break;
            default:
        }
    }

    private void setSelect(int i) {

        initTabImage();
        switch (i) {
            case 0:
                mIBHome.setBackgroundResource(R.drawable.home_white_24dp);
                mTvHome.setTextColor(getResources().getColor(R.color.white));
                break;
            case 1:
                mIBSearch.setBackgroundResource(R.drawable.search_white_24dp);
                mTvSearch.setTextColor(getResources().getColor(R.color.white));
                break;
            case 2:
                mIBWatchlist.setBackgroundResource(R.drawable.history_white_24dp);
                mTvWatchlist.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                break;
        }
        mViewPager.setCurrentItem(i);
    }
}