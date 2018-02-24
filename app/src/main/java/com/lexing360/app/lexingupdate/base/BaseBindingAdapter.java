package com.lexing360.app.lexingupdate.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fenglingfeng on 2018/1/31.
 * 说明：支持下拉刷新和上滑加载更多(并且可以设置是否可以下拉刷新和上滑加载)
 */

public abstract class BaseBindingAdapter<T> extends RecyclerView.Adapter<BaseBindingViewHolder<T>> {

    protected List<T> mItems;
    protected Context mContext;
    private View mEmptyView;
    private RelativeLayout mFootLayout;
    private View mLoadingView;
    private View mLoadingPageView;

    public BaseBindingAdapter(Context context) {
        this.mContext = context;
        mItems = new ArrayList<>();
    }

    @Override
    public BaseBindingViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {

        return createVH(parent, viewType);
    }

    public abstract BaseBindingViewHolder<T> createVH(ViewGroup parent, int viewType);

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onBindViewHolder(BaseBindingViewHolder holder, final int position) {
        holder.bindTo(holder, mItems.get(position));
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(mItems.get(position));
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    public List<T> getData() {
        return new ArrayList<>(mItems);
    }

    public void add(T data) {
        mItems.add(data);
        notifyDataSetChanged();
    }

    public void addAll(List<T> list) {
        mItems.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(int index, List<T> list) {
        mItems.addAll(index, list);
        notifyDataSetChanged();
    }

    public void removeAll() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void setEmptyView(View view) {
        mEmptyView = view;
        mLoadingPageView = null;
    }

    public void setLoadingPageView(View view) {
        mLoadingPageView = view;
    }

    public void setLoadingView(View view) {
        mLoadingView = view;
        addLoadingView(view);
    }

    public void removeLoadingView() {
        mFootLayout.removeAllViews();
    }

    public void addLoadingView(View view) {
        if (view == null) {
            return;
        }
        if (mFootLayout == null) {
            mFootLayout = new RelativeLayout(mContext);
        }
        removeLoadingView();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mFootLayout.addView(view, params);
    }

    private OnItemClickListener<T> mItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.mItemClickListener = listener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(T data);
    }

}
