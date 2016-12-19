package com.chen.treeview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chen.treeview.listener.OnNodeCheckListener;
import com.chen.treeview.listener.OnNodeItemClickListener;
import com.chen.treeview.listener.OnNodeSwitchListener;
import com.chen.treeview.model.Node;
import com.chen.treeview.viewholder.TreeBaseViewHolder;
import com.chen.treeview.viewholder.TreeLeafViewHolder;
import com.chen.treeview.viewholder.TreeNodeViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class TreeRecyclerAdapter<T> extends RecyclerView.Adapter<TreeBaseViewHolder> {

    private Context mContext;
    private List<Node<T>> mVisibleNodes;
    private List<Node<T>> mRootNodes;
    private OnNodeItemClickListener mOnNodeItemClickListener;
    private int mSelectMode = TreeRecyclerView.MODE_SINGLE_SELECT;

    private OnNodeSwitchListener mOnNodeSwitchListener = new OnNodeSwitchListener() {
        @Override
        public void onExpand(Node node, int position) {
            NodeDataConverter.expandNode(NodeDataConverter.filterNodeByIdOnCascadedList(node.getId(), mRootNodes));
            rearrangeVisibleNodes();
        }

        @Override
        public void onShrink(Node node, int position) {
            NodeDataConverter.shrinkNode(NodeDataConverter.filterNodeByIdOnCascadedList(node.getId(), mRootNodes));
            rearrangeVisibleNodes();
        }
    };


    private OnNodeCheckListener mOnNodeCheckListener = new OnNodeCheckListener() {
        @Override
        public void onCheck(boolean isChecked, int position, Node node) {

            if (mOnNodeItemClickListener != null) {
                mOnNodeItemClickListener.onItemClick(node.getContent());
            }

            NodeDataConverter.checkNodeOnVisibleList(node.getId(), isChecked, mSelectMode, mVisibleNodes);
            notifyDataSetChanged();

            if (mOnNodeItemClickListener != null) {
                mOnNodeItemClickListener.onPostItemClick();
            }
        }
    };


    public TreeRecyclerAdapter(Context context) {
        mContext = context;
        mVisibleNodes = new ArrayList<>();
        mRootNodes = new ArrayList<>();
    }


    /**
     * 填充数据
     *
     * @param nodes
     */
    public void addAllData(List<Node<T>> nodes) {

        if (nodes != null && !nodes.isEmpty()) {
            mRootNodes.clear();
            mRootNodes.addAll(nodes);
            rearrangeVisibleNodes();
        }

    }


    /**
     * 设置模式
     *
     * @param mode
     */
    public void setMode(int mode) {
        mSelectMode = mode;
    }


    /**
     * 设置点击事件
     */
    public void setOnItemClickListener(OnNodeItemClickListener listener) {
        mOnNodeItemClickListener = listener;
    }

    /**
     * 返回当前选择数据
     *
     * @return
     */
    public List<T> getSelectedItems() {

        List<T> resultDataList = new ArrayList<>();

        List<Node<T>> resultNodeList = new ArrayList<>();

        for (Node item : mRootNodes) {
            resultNodeList.addAll(NodeDataConverter.filterCheckedNodeList(item));
        }

        for (Node<T> item : resultNodeList) {
            resultDataList.add(item.getContent());
        }

        return resultDataList;

    }


    /**
     * 清除所有已经选择的内容
     */
    public void clearSelectedItems(String... nodeIds) {

        ArrayList<String> nodeIdList = new ArrayList<>();

        if (nodeIds != null && nodeIds.length > 0) {
            nodeIdList = (ArrayList<String>) Arrays.asList(nodeIds);
        }

        NodeDataConverter.uncheckNodeOnCascadedList(nodeIdList, mRootNodes);

        rearrangeVisibleNodes();

    }


    @Override
    public TreeBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Node.TREE_NODE:
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.listitem_tree_node, parent, false);
                return new TreeNodeViewHolder(view);
            case Node.TREE_LEAF:
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.listitem_tree_leaf, parent, false);
                return new TreeLeafViewHolder(view);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(TreeBaseViewHolder holder, int position) {

        OnNodeCheckListener checkListener = mOnNodeCheckListener;

        //点击模式，不可以选中
        if (mSelectMode == TreeRecyclerView.MODE_CLICK_SELECT) {
            checkListener = null;
        }

        switch (getItemViewType(position)) {
            case Node.TREE_NODE:
                TreeNodeViewHolder nodeViewHolder = (TreeNodeViewHolder) holder;
                nodeViewHolder.bindView(mVisibleNodes.get(position),
                        position, mOnNodeSwitchListener, checkListener);
                break;
            case Node.TREE_LEAF:
                TreeLeafViewHolder leafViewHolder = (TreeLeafViewHolder) holder;
                leafViewHolder.bindView(mVisibleNodes.get(position),
                        position, checkListener);
                break;
            default:
                break;
        }
    }


    @Override
    public int getItemCount() {
        return mVisibleNodes.size();
    }


    @Override
    public int getItemViewType(int position) {
        return mVisibleNodes.get(position).getType();
    }


    /**
     * 根据visible属性，重新刷新，可视节点
     */
    private void rearrangeVisibleNodes() {

        if (mRootNodes == null || mRootNodes.size() <= 0) {
            return;
        }

        mVisibleNodes.clear();

        for (Node<T> node : mRootNodes) {
            filterVisibleNodes(node);
        }

        notifyDataSetChanged();

    }


    /**
     * 将展开节点归结到可视节点中, 递归总是从根节点开始
     *
     * @return
     */
    private void filterVisibleNodes(Node<T> node) {
        mVisibleNodes.add(node);
        if (node.isExpanded()) {
            if (node.getChildren() != null) {
                for (Node<T> item : node.getChildren()) {
                    filterVisibleNodes(item);
                }
            }
        }
    }


}
