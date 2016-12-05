package com.chen.treeview.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.treeview.R;
import com.chen.treeview.listener.OnNodeCheckListener;
import com.chen.treeview.model.Node;


/**
 * Created by chenzhaohua on 16/11/25.
 */
public class TreeLeafViewHolder<T> extends TreeBaseViewHolder {

    private RelativeLayout rl_content;
    private TextView tv_name;
    private TextView tv_label;
    private ImageView iv_checkbox;


    public TreeLeafViewHolder(View itemView) {
        super(itemView);
        rl_content = (RelativeLayout) itemView.findViewById(R.id.rl_content);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        tv_label = (TextView) itemView.findViewById(R.id.tv_label);
        iv_checkbox = (ImageView) itemView.findViewById(R.id.iv_checkbox);
    }


    public void bindView(final Node<T> node,
                         final int position,
                         final OnNodeCheckListener onNodeCheckListener) {

        //根据节点层级，进行缩进处理
        RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                rl_content.getLayoutParams();
        params.leftMargin = mLevelMargin * node.getLevel();

        rl_content.setLayoutParams(params);


        //设置节点名称
        tv_name.setText(node.getName());

        //设置节点描述
        tv_label.setText(node.getLabel());

        //设置节点选中状态
        setChecked(iv_checkbox, node.isChecked());

        rl_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNodeCheckListener != null) {
                    onNodeCheckListener.onCheck(!node.isChecked(), position, node);
                }
            }
        });
    }


}
