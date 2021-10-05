package com.example.videoapp.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.videoapp.R;
import com.example.videoapp.activity.LoginActivity;
import com.example.videoapp.activity.WebActivity;
import com.example.videoapp.adapter.NewsAdapter;
import com.example.videoapp.api.Api;
import com.example.videoapp.api.ApiConfig;
import com.example.videoapp.api.TestCallback;
import com.example.videoapp.entity.NewsEntity;
import com.example.videoapp.entity.NewsListResponse;
import com.example.videoapp.entity.VideoEntity;
import com.example.videoapp.entity.VideoListResponse;
import com.example.videoapp.util.StringUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NewsFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private RefreshLayout refreshLayout;
    private NewsAdapter newsAdapter;
    private List<NewsEntity> datas = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private int pageNum = 1;



    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    newsAdapter.setDatas(datas);
                    newsAdapter.notifyDataSetChanged();
                    break;
            }

        }
    };


    public NewsFragment() {
    }


    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }


    @Override
    protected int initLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        refreshLayout = mRootView.findViewById(R.id.refreshLayout);
    }

    @Override
    protected void initData() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        newsAdapter = new NewsAdapter(getActivity());
        newsAdapter.setDatas(datas);
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Serializable obj) {
//                showToast("点击");
                NewsEntity newsEntity = (NewsEntity) obj;
                String url = "http://192.168.31.32:8089/newsDetail?title=" + newsEntity.getAuthorName();
                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                navigateToWithBundle(WebActivity.class, bundle);
            }

        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                getNewsList(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000);
                pageNum++;
                getNewsList(false);
            }
        });
        getNewsList(true);
    }

    private void getNewsList(boolean isRefresh) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("page", pageNum);
            params.put("limit", ApiConfig.PAGE_SIZE);
            Api.config(ApiConfig.NEWS_LIST, params).getRequest(getActivity(), new TestCallback() {
                @Override
                public void onSuccess(String res) {
                    if (isRefresh) {
                        refreshLayout.finishRefresh();
                    }else {
                        refreshLayout.finishRefresh();
                    }

                    NewsListResponse response = new Gson().fromJson(res, NewsListResponse.class);
                    if (response != null && response.getCode() == 0) {
                        List<NewsEntity> list = response.getPage().getList();
                        if (list != null && list.size() > 0) {
                            if (isRefresh) {
                                datas = list;
                            } else {
                                datas.addAll(list);
                            }
                            mHandler.sendEmptyMessage(0);

                        } else {
                            if (isRefresh) {
                                showToastSync("暂时无数据");
                            } else {
                                showToastSync("没有更多数据");
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    if (isRefresh) {
                        refreshLayout.finishRefresh();
                    }else {
                        refreshLayout.finishRefresh();
                    }
                }
            });
    }
}