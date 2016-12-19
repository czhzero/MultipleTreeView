package com.chen.treeview.listener;

import com.chen.treeview.model.Node;

/**
 * Created by chenzhaohua on 16/11/25.
 *
 * 展开与收缩操作
 *
 */
public interface OnNodeSwitchListener {
    void onExpand(Node node, int position);

    void onShrink(Node node, int position);
}
