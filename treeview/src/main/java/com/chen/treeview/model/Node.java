package com.chen.treeview.model;

import java.util.List;

/**
 * Created by chenzhaohua on 16/11/25.
 *
 * 树形节点数据模型
 *
 * T 代表 节点自定义的数据内容
 */
public class Node<T> {

    public final static int TREE_LEAF = 0;  //树叶节点
    public final static int TREE_NODE = 1;  //普通节点


    /**
     * 节点Id
     */
    private String id;


    /**
     * 父节点Id
     */
    private String pId;


    /**
     * 节点名称
     */
    private String name;


    /**
     * 节点类型, 0为树叶节点, 1为普通节点
     */
    private int type;


    /**
     * 节点级别, 根节点level = 0，子节点依次+1
     */
    private int level;



    /**
     * 父Node
     */
    private Node<T> parent;


    /**
     * 下一级的子Node
     */
    private List<Node<T>> children;


    /**
     * 是否展开
     */
    private boolean isExpanded;


    /**
     * 是否被选中
     */
    private boolean isChecked;


    /**
     * 节点描述
     */
    private String label;


    /**
     * 节点信息其他内容
     */
    private T content;


    /**
     * 节点前面的Icon
     */
    private int headDrawableId;


    /**
     * 节点选中的Icon
     */
    private int checkDrawableId;



    public Node(String id, String pid, String name) {
        this.id = id;
        this.pId = pid;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getHeadDrawableId() {
        return headDrawableId;
    }

    public void setHeadDrawableId(int headDrawableId) {
        this.headDrawableId = headDrawableId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCheckDrawableId() {
        return checkDrawableId;
    }

    public void setCheckDrawableId(int checkDrawableId) {
        this.checkDrawableId = checkDrawableId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}


