package com.geekb.uscfilms;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;

public class ReviewsActivity extends BaseActivity {
    ReviewData data = new ReviewData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).statusBarColor(R.color.black_1e2c34).fitsSystemWindows(true).init();
        setContentView(R.layout.activity_reviews);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        data = (ReviewData) bundle.getSerializable("data");
        TextView userInfo = findViewById(R.id.user_info);
        TextView star = findViewById(R.id.star);
        TextView comment = findViewById(R.id.comment);
        if (!TextUtils.isEmpty(data.created)) {
            String[] y = data.created.split(",");
            String year = y[0] + ", " + y[1];
            userInfo.setText(data.author + " " + year);
        }else {
            userInfo.setText(data.author);
        }
        comment.setText(data.content);
        star.setText(data.rating);
    }
}
