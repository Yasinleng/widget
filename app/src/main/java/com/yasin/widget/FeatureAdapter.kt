package com.yasin.widget

import android.content.Context
import android.widget.TextView
import com.yasin.view.recyclerview.BaseRecyclerAdapter
import com.yasin.view.recyclerview.BaseViewHolder

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2021/12/18.
 */
class FeatureAdapter(context: Context) :BaseRecyclerAdapter<String>(context,R.layout.item_view_feature){
    override fun bindViewHolder(holder: BaseViewHolder?, itemData: String?, position: Int) {
        if (holder==null){
            return

        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder, position)
        }

        holder.findViewById<TextView>(R.id.feature_name).text=itemData

    }
}