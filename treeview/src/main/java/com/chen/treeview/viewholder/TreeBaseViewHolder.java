package com.chen.treeview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by chenzhaohua on 16/11/25.
 */
public class TreeBaseViewHolder extends RecyclerView.ViewHolder {

    private static final int LEVEL_MARGIN = 30;

    protected TreeBaseViewHolder(View itemView) {
        super(itemView);
    }



    /**
     * 设置选中状态
     * @param view
     * @param isChecked
     */
    protected void setChecked(View view, boolean isChecked) {
        if (isChecked) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }


    /**
     * 设置文本内容
     * @param view
     * @param text
     */
    protected void setText(TextView view, String text) {
        view.setText(text);
    }




    /**
     * 根据节点层级，进行缩进处理
     *
     * @param view
     * @param level
     */
    protected void setLevelMargin(View view, int level) {
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        params.leftMargin = LEVEL_MARGIN * level;
        view.setLayoutParams(params);
    }



}
