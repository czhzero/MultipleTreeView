package com.chen.treeview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chenzhaohua on 16/11/25.
 */
public class TreeBaseViewHolder extends RecyclerView.ViewHolder {

    protected int mLevelMargin;

    protected TreeBaseViewHolder(View itemView) {
        super(itemView);
        mLevelMargin = 30;
    }


    protected void setChecked(View view, boolean isChecked) {
        if (isChecked) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


}
