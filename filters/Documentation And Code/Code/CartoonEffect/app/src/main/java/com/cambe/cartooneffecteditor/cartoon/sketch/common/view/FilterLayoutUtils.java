package com.cambe.cartooneffecteditor.cartoon.sketch.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


import com.cambe.cartooneffecteditor.R;
import com.cambe.cartooneffecteditor.bean.FilterInfo;
import com.cambe.cartooneffecteditor.cartoon.sketch.common.adapter.FilterAdapter;
import com.cambe.cartooneffecteditor.filters.FilterChangedInterface;

import java.util.ArrayList;
import java.util.List;

public class FilterLayoutUtils {
//    private ImageView btn_Favourite;
//    private OnClickListener btn_Favourite_listener = new OnClickListener() {
//        public void onClick(View v) {
//            if (FilterLayoutUtils.this.position != 0 && ((FilterInfo) FilterLayoutUtils.this.filterInfos.get(FilterLayoutUtils.this.position)).getFilterType() != -1) {
//                int Type = ((FilterInfo) FilterLayoutUtils.this.filterInfos.get(FilterLayoutUtils.this.position)).getFilterType();
//                FilterLayoutUtils filterLayoutUtils;
//                if (((FilterInfo) FilterLayoutUtils.this.filterInfos.get(FilterLayoutUtils.this.position)).isFavourite()) {
//                    FilterLayoutUtils.this.btn_Favourite.setSelected(false);
//                    ((FilterInfo) FilterLayoutUtils.this.filterInfos.get(FilterLayoutUtils.this.position)).setFavourite(false);
//                    FilterLayoutUtils.this.mAdapter.notifyItemChanged(FilterLayoutUtils.this.position);
//                    int i = 0;
//                    while (i < FilterLayoutUtils.this.favouriteFilterInfos.size()) {
//                        if (Type == ((FilterInfo) FilterLayoutUtils.this.favouriteFilterInfos.get(i)).getFilterType()) {
//                            FilterLayoutUtils.this.favouriteFilterInfos.remove(i);
//                            FilterLayoutUtils.this.filterInfos.remove(i + 1);
//                            FilterLayoutUtils.this.mAdapter.notifyItemRemoved(i + 1);
//                            FilterLayoutUtils.this.mAdapter.setLastSelected(FilterLayoutUtils.this.mAdapter.getLastSelected() - 1);
//                            break;
//                        }
//                        i++;
//                    }
//                    filterLayoutUtils = FilterLayoutUtils.this;
//                    filterLayoutUtils.position = filterLayoutUtils.position - 1;
//                    FilterLayoutUtils.this.mAdapter.notifyItemRangeChanged(i + 1, (FilterLayoutUtils.this.filterInfos.size() - i) - 1);
//                } else {
//                    FilterLayoutUtils.this.btn_Favourite.setSelected(true);
//                    ((FilterInfo) FilterLayoutUtils.this.filterInfos.get(FilterLayoutUtils.this.position)).setFavourite(true);
//                    FilterLayoutUtils.this.mAdapter.notifyItemChanged(FilterLayoutUtils.this.position);
//                    FilterInfo filterInfo = new FilterInfo();
//                    filterInfo.setFilterType(Type);
//                    filterInfo.setSelected(true);
//                    filterInfo.setFavourite(true);
//                    FilterLayoutUtils.this.filterInfos.add(FilterLayoutUtils.this.favouriteFilterInfos.size() + 1, filterInfo);
//                    filterLayoutUtils = FilterLayoutUtils.this;
//                    filterLayoutUtils.position = filterLayoutUtils.position + 1;
//                    FilterLayoutUtils.this.mAdapter.notifyItemInserted(FilterLayoutUtils.this.favouriteFilterInfos.size() + 1);
//                    FilterLayoutUtils.this.mAdapter.notifyItemRangeChanged(FilterLayoutUtils.this.favouriteFilterInfos.size() + 1, (FilterLayoutUtils.this.filterInfos.size() - FilterLayoutUtils.this.favouriteFilterInfos.size()) - 1);
//                    FilterLayoutUtils.this.favouriteFilterInfos.add(filterInfo);
//                    FilterLayoutUtils.this.mAdapter.setLastSelected(FilterLayoutUtils.this.mAdapter.getLastSelected() + 1);
//                }
//                FilterLayoutUtils.this.saveFavourite();
//            }
//        }
//    };
    FilterChangedInterface callback;
    private List<FilterInfo> favouriteFilterInfos;
    private List<FilterInfo> filterInfos;
    private FilterAdapter mAdapter;
    private Context mContext;
    private int mFilterType = 0;
    Bitmap mainBitmap;
    private ImageView mainImage;
    private FilterAdapter.onFilterChangeListener onFilterChangeListener = new FilterAdapter.onFilterChangeListener() {
        public void onFilterChanged(int filterType, int position) {
            Log.e("postion", "" + position);
            int i;
            int Type = ((FilterInfo) FilterLayoutUtils.this.filterInfos.get(position)).getFilterType();
            FilterLayoutUtils.this.position = position;
            FilterLayoutUtils.this.callback.filterChanged(filterType);
            FilterLayoutUtils.this.mFilterType = filterType;
//            if (position != 0) {
//                FilterLayoutUtils.this.btn_Favourite.setVisibility(View.VISIBLE);
//            } else {
//                FilterLayoutUtils.this.btn_Favourite.setVisibility(View.GONE);
//            }
//            FilterLayoutUtils.this.btn_Favourite.setSelected(((FilterInfo) FilterLayoutUtils.this.filterInfos.get(position)).isFavourite());
            if (position <= FilterLayoutUtils.this.favouriteFilterInfos.size()) {
                for (i = FilterLayoutUtils.this.favouriteFilterInfos.size() + 2; i < FilterLayoutUtils.this.filterInfos.size(); i++) {
                    if (((FilterInfo) FilterLayoutUtils.this.filterInfos.get(i)).getFilterType() == Type) {
                        ((FilterInfo) FilterLayoutUtils.this.filterInfos.get(i)).setSelected(true);
                        FilterLayoutUtils.this.mAdapter.setLastSelected(i);
                        FilterLayoutUtils.this.position = i;
                        FilterLayoutUtils.this.mAdapter.notifyItemChanged(i);
                    } else if (((FilterInfo) FilterLayoutUtils.this.filterInfos.get(i)).isSelected()) {
                        ((FilterInfo) FilterLayoutUtils.this.filterInfos.get(i)).setSelected(false);
                        FilterLayoutUtils.this.mAdapter.notifyItemChanged(i);
                    }
                }
            }
            for (i = 1; i < FilterLayoutUtils.this.favouriteFilterInfos.size() + 1; i++) {
                if (((FilterInfo) FilterLayoutUtils.this.filterInfos.get(i)).getFilterType() == Type) {
                    ((FilterInfo) FilterLayoutUtils.this.filterInfos.get(i)).setSelected(true);
                    FilterLayoutUtils.this.mAdapter.notifyItemChanged(i);
                } else if (((FilterInfo) FilterLayoutUtils.this.filterInfos.get(i)).isSelected()) {
                    ((FilterInfo) FilterLayoutUtils.this.filterInfos.get(i)).setSelected(false);
                    FilterLayoutUtils.this.mAdapter.notifyItemChanged(i);
                }
            }
        }
    };
    private int position;

    public FilterLayoutUtils(Context context, ImageView mImageView, Bitmap bitmap) {
        this.mContext = context;
        this.mainImage = mImageView;
        this.mainBitmap = bitmap;
        this.callback = (FilterChangedInterface) context;
    }

    public void init() {
//        this.btn_Favourite = (ImageView) ((Activity) this.mContext).findViewById(R.id.btn_camera_favourite);
//        this.btn_Favourite.setOnClickListener(this.btn_Favourite_listener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setOrientation(0);
        RecyclerView mFilterListView = (RecyclerView) ((Activity) this.mContext).findViewById(R.id.filter_listView);
        mFilterListView.setLayoutManager(linearLayoutManager);
        this.mAdapter = new FilterAdapter(this.mContext);
        mFilterListView.setAdapter(this.mAdapter);
        initFilterInfos();
        this.mAdapter.setFilterInfos(this.filterInfos);
        this.mAdapter.setOnFilterChangeListener(this.onFilterChangeListener);
    }

    public void init(View view) {
//        this.btn_Favourite = (ImageView) view.findViewById(R.id.btn_camera_favourite);
//        this.btn_Favourite.setOnClickListener(this.btn_Favourite_listener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setOrientation(0);
        RecyclerView mFilterListView = (RecyclerView) view.findViewById(R.id.filter_listView);
        mFilterListView.setLayoutManager(linearLayoutManager);
        this.mAdapter = new FilterAdapter(this.mContext);
        mFilterListView.setAdapter(this.mAdapter);
        initFilterInfos();
        this.mAdapter.setFilterInfos(this.filterInfos);
        this.mAdapter.setOnFilterChangeListener(this.onFilterChangeListener);
//        view.findViewById(R.id.btn_camera_closefilter).setVisibility(View.GONE);
    }

    private void initFilterInfos() {
        int i;
        this.filterInfos = new ArrayList();
        FilterInfo filterInfo = new FilterInfo();
        filterInfo.setFilterType(0);
        filterInfo.setSelected(true);
        this.filterInfos.add(filterInfo);
        loadFavourite();
        for (i = 0; i < this.favouriteFilterInfos.size(); i++) {
            filterInfo = new FilterInfo();
            filterInfo.setFilterType(((FilterInfo) this.favouriteFilterInfos.get(i)).getFilterType());
            filterInfo.setFavourite(true);
            this.filterInfos.add(filterInfo);
        }
        filterInfo = new FilterInfo();
        filterInfo.setFilterType(-1);
        this.filterInfos.add(filterInfo);
        for (i = 1; i < 27; i++) {
            filterInfo = new FilterInfo();
            filterInfo.setFilterType(i + 0);
            for (int j = 0; j < this.favouriteFilterInfos.size(); j++) {
                if (i + 0 == ((FilterInfo) this.favouriteFilterInfos.get(j)).getFilterType()) {
                    filterInfo.setFavourite(true);
                    break;
                }
            }
            this.filterInfos.add(filterInfo);
        }
    }

    private void loadFavourite() {
        this.favouriteFilterInfos = new ArrayList();
        String[] typeList = ((Activity) this.mContext).getSharedPreferences("favourite_filter", 0).getString("favourite_filter_list", "").split(",");
        int i = 0;
        while (i < typeList.length && typeList[i] != "") {
            FilterInfo filterInfo = new FilterInfo();
            filterInfo.setFilterType(Integer.parseInt(typeList[i]));
            filterInfo.setFavourite(true);
            this.favouriteFilterInfos.add(filterInfo);
            i++;
        }
    }

    private void saveFavourite() {
        Editor editor = ((Activity) this.mContext).getSharedPreferences("favourite_filter", 0).edit();
        editor.remove("favourite_filter_list");
        editor.commit();
        String str = "";
        for (int i = 0; i < this.favouriteFilterInfos.size(); i++) {
            str = new StringBuilder(String.valueOf(str)).append(((FilterInfo) this.favouriteFilterInfos.get(i)).getFilterType()).append(",").toString();
        }
        editor.putString("favourite_filter_list", str);
        editor.commit();
    }

    public int getFilterType() {
        return this.mFilterType;
    }
}
