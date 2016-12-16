package com.chen.treeview.viewholder;


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
public class TreeLeafViewHolder extends TreeBaseViewHolder {

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


    public void bindView(final Node node,
                         final int position,
                         final OnNodeCheckListener onNodeCheckListener) {

        //根据节点层级，进行缩进处理
        setLevelMargin(rl_content, node.getLevel());

        //设置节点名称
        setText(tv_name, node.getName());

        //设置节点描述
        setText(tv_label, node.getLabel());

        //设置节点选中状态
        setChecked(iv_checkbox, node.isChecked());

        //设置选中点击事件
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
