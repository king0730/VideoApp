package com.example.videoapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.videoapp.R;
import com.example.videoapp.activity.LoginActivity;
import com.example.videoapp.adapter.HomeAdapter;
import com.example.videoapp.api.Api;
import com.example.videoapp.api.ApiConfig;
import com.example.videoapp.api.TestCallback;
import com.example.videoapp.entity.CategoryEntity;
import com.example.videoapp.entity.VideoCategoryResponse;
import com.example.videoapp.entity.VideoEntity;
import com.example.videoapp.entity.VideoListResponse;
import com.example.videoapp.util.StringUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HomeFragment extends BaseFragment {

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles;
    private ViewPager viewPager;
    private SlidingTabLayout slidingTabLayout;


    public HomeFragment() {
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        viewPager = mRootView.findViewById(R.id.fixedViewPager);
        slidingTabLayout = mRootView.findViewById(R.id.slidingTabLayout);
    }

    @Override
    protected void initData() {
        getVideoCategoryList();
    }

    private void getVideoCategoryList() {
        HashMap<String, Object> params = new HashMap<>();
        Api.config(ApiConfig.VIDEO_CATEGORY_LIST, params).getRequest(getActivity(), new TestCallback() {
                    @Override
                    public void onSuccess(String res) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                VideoCategoryResponse response = new Gson().fromJson(res, VideoCategoryResponse.class);
                                if (response != null && response.getCode() == 0) {
                                    List<CategoryEntity> list = response.getPage().getList();
                                    if (list != null && list.size() > 0) {
                                        mTitles = new String[list.size()];
                                        for (int i = 0; i < list.size(); i++) {
                                            mTitles[i] = list.get(i).getCategoryName();
                                            mFragments.add(VideoFragment.newInstance(list.get(i).getCategoryId()));
                                        }
                                        viewPager.setOffscreenPageLimit(mFragments.size());
                                        viewPager.setAdapter(new HomeAdapter(getFragmentManager(), mTitles, mFragments));
                                        slidingTabLayout.setViewPager(viewPager);
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
            });
    }
}