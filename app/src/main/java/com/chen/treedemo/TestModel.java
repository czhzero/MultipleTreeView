package com.chen.treedemo;

import com.chen.treeview.model.NodeChild;
import com.chen.treeview.model.NodeId;
import com.chen.treeview.model.NodeLabel;
import com.chen.treeview.model.NodeName;
import com.chen.treeview.model.NodePid;

import java.util.List;


/**
 * Created by chenzhaohua on 16/12/3.
 */
public class TestModel {

    @NodeId
    public String id;       //必填字段
    @NodeName
    public String name;     //必填字段
    @NodeLabel
    public String label;
    @NodePid
    public String parentId;
    @NodeChild
    public List<TestModel> child;

    public String other1;
    public String other2;
    public int ohter3;
}
