package com.sunsun.scrollviewdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private SparseArray<ScrollViewListener> mScrollViewListener = new SparseArray<ScrollViewListener>();
    private TabStripView mTabStripView;
    private ScrollableViewPager mScrollableViewPager;
    private CustomScrollView mCustomScrollView;
    private String[] mTabs;
    private MainViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
//       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mTabs = getResources().getStringArray(R.array.down_stream_manager);
        mCustomScrollView = (CustomScrollView) findViewById(R.id.custom_scrollview);
        mScrollableViewPager = (ScrollableViewPager) findViewById(R.id.scrollable_viewpager);
        mTabStripView = (TabStripView) findViewById(R.id.tab_strip_view);
        setTabStripView(mTabs);
        mScrollableViewPager.setOffscreenPageLimit(1);// 设置viewPager进行预加载
        mScrollableViewPager.setOnPageChangeListener(this);
        mScrollableViewPager.setAdapter(viewPagerAdapter);
        mTabStripView.setOnSwitchListener(onSwitchListener);
        setViewPagerData();
        mCustomScrollView.setScrollableListener(listener);
    }

    private TabStripView.OnTabStripSwitchListener onSwitchListener = new TabStripView.OnTabStripSwitchListener() {

        @Override
        public void doSwitch(int selectedIndex) {
            if (mScrollableViewPager != null) {
                mScrollableViewPager.setCurrentItem(selectedIndex);
            }
        }
    };

    /**
     * 设置 tabStripView 个数
     *
     * @param tabs
     */
    private void setTabStripView(String[] tabs) {
        if (tabs != null && tabs.length > 0) {
            mTabStripView.setTabs(tabs);
        }
    }

    private void setViewPagerData() {
        if (mTabs != null && mTabs.length > 0) {
            ArrayList<String> data = new ArrayList<String>();
            for (int i = 0; i < mTabs.length; i++) {
                data.add(mTabs[i]);
            }
            viewPagerAdapter.setData(data);
        }
    }

    /**
     * 切换tab菜单
     *
     * @param index
     */
    private void changeTabStatus(int index) {
        mTabStripView.setSelectedIndex(index);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeTabStatus(position);
        Log.d(TAG, "position = " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private CustomScrollView.CustomScrollViewListener listener = new CustomScrollView.CustomScrollViewListener() {
        @Override
        public boolean isSlidingTop(MotionEvent ev) {
            ScrollViewListener listener = mScrollViewListener.get(mScrollableViewPager.getCurrentItem());
            if (listener != null) {
                return listener.isSlidingTop();
            }
            return false;
        }
    };


    public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

        private ArrayList<String> data;

        public MainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(ArrayList<String> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new AFragment();
            } else if (position == 1) {
                return new BFragment();
            } else if (position == 2) {
                return new CFragment();
            }
            return new AFragment();
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object mObject = super.instantiateItem(container, position);
            if (mObject instanceof ScrollViewListener) {
                mScrollViewListener.put(position, (ScrollViewListener) mObject);
            }
            return mObject;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mScrollViewListener != null) {
            mScrollViewListener.clear();
        }
    }
}
