package com.lexing360.app.lexingupdate.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lexing360.app.lexingupdate.R;
import com.lexing360.app.lexingupdate.base.BaseBindingAdapter;
import com.lexing360.app.lexingupdate.base.BaseBindingViewHolder;
import com.lexing360.app.lexingupdate.databinding.ItemListMainBinding;

/**
 * Created by fenglingfeng on 2018/2/9.
 */

public class MainListAdapter extends BaseBindingAdapter<String> {


    public MainListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseBindingViewHolder<String> createVH(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                viewType, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.item_list_main;
    }

    public class ViewHolder extends BaseBindingViewHolder<String> {

        public ViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding);
        }

        @Override
        public void bindTo(BaseBindingViewHolder<String> holder, String item) {
            ItemListMainBinding binding = (ItemListMainBinding) this.binding;
            binding.setName(item);
            //binding.tvItemMain.setText(item);
        }
    }
}
