package com.geekb.uscfilms;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionFragment;

public class BaseFagment extends SimpleImmersionFragment {
    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarColor(R.color.black).fitsSystemWindows(true).init();
    }
}
