package com.cambe.cartooneffecteditor.cartoon.sketch.common.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cambe.cartooneffecteditor.R;
import com.cambe.cartooneffecteditor.bean.FilterInfo;
import com.cambe.cartooneffecteditor.cartoon.sketch.common.utils.FilterTypeHelper;
import com.cambe.cartooneffecteditor.chooser.BaseViewTransformer;

import java.util.List;

public class FilterAdapter extends Adapter<FilterAdapter.FilterHolder> {
    private Context context;
    private List<FilterInfo> filterInfos;
    private int lastSelected = 0;
    private LayoutInflater mInflater;
    private onFilterChangeListener onFilterChangeListener;

    class FilterHolder extends ViewHolder {
        FrameLayout filterFavourite;
        TextView filterName;
        FrameLayout filterRoot;
        ImageView thumbImage;
        FrameLayout thumbSelected;
        View thumbSelected_bg;

        public FilterHolder(View itemView) {
            super(itemView);
        }
    }

    public interface onFilterChangeListener {
        void onFilterChanged(int i, int i2);
    }

    public FilterAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public int getItemCount() {
        return this.filterInfos.size();
    }

    public int getItemViewType(int position) {
        return ((FilterInfo) this.filterInfos.get(position)).getFilterType();
    }

    public void onBindViewHolder(FilterHolder arg0, final int arg1) {
        if (((FilterInfo) this.filterInfos.get(arg1)).getFilterType() != -1) {
            arg0.thumbImage.setImageResource(FilterTypeHelper.FilterType2Thumb(((FilterInfo) this.filterInfos.get(arg1)).getFilterType()));
            arg0.filterName.setText(FilterTypeHelper.FilterType2Name(((FilterInfo) this.filterInfos.get(arg1)).getFilterType()));
            arg0.filterName.setBackgroundColor(this.context.getResources().getColor(FilterTypeHelper.FilterType2Color(((FilterInfo) this.filterInfos.get(arg1)).getFilterType())));
            if (((FilterInfo) this.filterInfos.get(arg1)).isSelected()) {
                arg0.thumbSelected.setVisibility(View.VISIBLE);
                arg0.thumbSelected_bg.setBackgroundColor(this.context.getResources().getColor(FilterTypeHelper.FilterType2Color(((FilterInfo) this.filterInfos.get(arg1)).getFilterType())));
                arg0.thumbSelected_bg.setAlpha(BaseViewTransformer.MAX_DIM_ALPHA);
            } else {
                arg0.thumbSelected.setVisibility(View.GONE);
            }
            if (!((FilterInfo) this.filterInfos.get(arg1)).isFavourite() || arg1 == 0) {
                arg0.filterFavourite.setVisibility(View.GONE);
            } else {
                arg0.filterFavourite.setVisibility(View.VISIBLE);
            }
            arg0.filterRoot.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (FilterAdapter.this.onFilterChangeListener != null && ((FilterInfo) FilterAdapter.this.filterInfos.get(arg1)).getFilterType() != -1 && arg1 != FilterAdapter.this.lastSelected && !((FilterInfo) FilterAdapter.this.filterInfos.get(arg1)).isSelected()) {
                        ((FilterInfo) FilterAdapter.this.filterInfos.get(FilterAdapter.this.lastSelected)).setSelected(false);
                        ((FilterInfo) FilterAdapter.this.filterInfos.get(arg1)).setSelected(true);
                        FilterAdapter.this.notifyItemChanged(FilterAdapter.this.lastSelected);
                        FilterAdapter.this.notifyItemChanged(arg1);
                        FilterAdapter.this.lastSelected = arg1;
                        FilterAdapter.this.onFilterChangeListener.onFilterChanged(((FilterInfo) FilterAdapter.this.filterInfos.get(arg1)).getFilterType(), arg1);
                    }
                }
            });
        }
    }

    public FilterHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
        if (arg1 == -1) {
            return new FilterHolder(this.mInflater.inflate(R.layout.filter_division_layout, arg0, false));
        }
        View view = this.mInflater.inflate(R.layout.filter_item_layout, arg0, false);
        FilterHolder viewHolder = new FilterHolder(view);
        viewHolder.thumbImage = (ImageView) view.findViewById(R.id.filter_thumb_image);
        viewHolder.filterName = (TextView) view.findViewById(R.id.filter_thumb_name);
        viewHolder.filterRoot = (FrameLayout) view.findViewById(R.id.filter_root);
        viewHolder.thumbSelected = (FrameLayout) view.findViewById(R.id.filter_thumb_selected);
        viewHolder.filterFavourite = (FrameLayout) view.findViewById(R.id.filter_thumb_favorite_layout);
        viewHolder.thumbSelected_bg = view.findViewById(R.id.filter_thumb_selected_bg);
        return viewHolder;
    }

    public void setLastSelected(int arg) {
        this.lastSelected = arg;
    }

    public int getLastSelected() {
        return this.lastSelected;
    }

    public void setFilterInfos(List<FilterInfo> filterInfos) {
        this.filterInfos = filterInfos;
        notifyDataSetChanged();
    }

    public void setOnFilterChangeListener(onFilterChangeListener onFilterChangeListener) {
        this.onFilterChangeListener = onFilterChangeListener;
    }
}
