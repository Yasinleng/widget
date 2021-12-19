package com.yasin.view.recyclerview;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2021/12/18.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private Context context;
    private View itemView;
    private SparseArray<View> viewSparseArray;

    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.itemView = itemView;
        this.viewSparseArray = new SparseArray<View>();
    }

    public View getItemView() {
        return itemView;
    }

    public <T extends View> T findViewById(int viewId) {
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }
}
