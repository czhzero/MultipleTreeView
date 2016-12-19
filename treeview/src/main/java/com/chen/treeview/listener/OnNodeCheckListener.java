package com.chen.treeview.listener;

import com.chen.treeview.model.Node;

/**
 * Created by chenzhaohua on 16/11/25.
 *
 * 节点选中状态监听
 *
 */
public interface OnNodeCheckListener {
    void onCheck(boolean isChecked, int position, Node node);
}
