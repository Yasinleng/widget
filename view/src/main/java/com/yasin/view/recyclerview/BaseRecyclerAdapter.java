package com.yasin.view.recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2021/12/18.
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    private static final String TAG = "BaseRecyclerAdapter";

    protected Context context;
    protected int layoutId;
    protected ArrayList<T> dataList = new ArrayList<>();

    protected OnItemClickListener onItemClickListener;

    public BaseRecyclerAdapter(Context context, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder() called viewType = [" + viewType + "]");
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseViewHolder(context, itemView);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder() called with:, position = [" + position + "]");
        bindViewHolder(holder, dataList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public abstract void bindViewHolder(BaseViewHolder holder, T itemData, int position);

    public List<T> getDataList() {
        return dataList;
    }

    public void setData(List<T> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);

    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(BaseViewHolder holder,int position);
    }

}
