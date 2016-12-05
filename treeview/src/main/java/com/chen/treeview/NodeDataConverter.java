package com.chen.treeview;

import android.text.TextUtils;

import com.chen.treeview.model.Node;
import com.chen.treeview.model.NodeChild;
import com.chen.treeview.model.NodeId;
import com.chen.treeview.model.NodeLabel;
import com.chen.treeview.model.NodeName;
import com.chen.treeview.model.NodePid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenzhaohua on 16/12/3.
 */
class NodeDataConverter {


    /**
     * 收缩节点
     *
     * @param node
     */
    public static <T> void shrinkNode(Node<T> node) {
        if (node == null) {
            return;
        }
        node.setExpanded(false);

        if (node.getChildren() != null) {
            for (int i = 0; i < node.getChildren().size(); i++) {
                shrinkNode(node.getChildren().get(i));
            }
        }
    }


    /**
     * 展开节点
     *
     * @param node
     */
    public static <T> void expandNode(Node<T> node) {
        if (node == null) {
            return;
        }
        node.setExpanded(true);
    }


    /**
     * 选择单个节点，并改变当前列表中选项的选中状态，同时根据模式改变其父子节点的选中状态
     * @param nodeId
     * @param isChecked
     * @param mode
     * @param nodeList
     * @param <T>
     */
    public static <T> void checkNode(String nodeId, boolean isChecked, int mode, List<Node<T>> nodeList) {

        switch (mode) {
            //单选模式
            case TreeRecyclerView.MODE_SINGLE_SELECT:
                if (isChecked) {
                    for (Node<T> item : nodeList) {
                        if (nodeId.equals(item.getId())) {
                            item.setChecked(true);
                        } else {
                            item.setChecked(false);
                        }
                    }
                }
                break;
            //多选模式
            case TreeRecyclerView.MODE_MULTI_SELECT:
                for (Node<T> item : nodeList) {
                    if (nodeId.equals(item.getId())) {
                        item.setChecked(isChecked);
                    }
                }
                break;
            //子父节点联动
            case TreeRecyclerView.MODE_DEPEND_SELECT:
                for (Node<T> item : nodeList) {
                    if (nodeId.equals(item.getId())) {
                        item.setChecked(isChecked);
                        //当前选择true，所有父亲节点取消选择，所有孩子节点取消
                        if (isChecked) {
                            uncheckParentNode(item);
                            uncheckChildNode(item);
                        }
                    }
                }
                break;
            //选中进入
            case TreeRecyclerView.MODE_CLICK_SELECT:

                break;
            default:
                break;
        }

    }




    /**
     * 列表转换
     *
     * @param list
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static <T> ArrayList<Node<T>> convertToNodeList(List<T> list) throws IllegalAccessException, IllegalArgumentException {


        ArrayList<Node<T>> nodeList = new ArrayList<>();

        if (list == null || list.size() <= 0) {
            return nodeList;
        }

        for (T t : list) {

            Node<T> node = data2Node(t);

            if (node != null) {
                nodeList.add(node);
            }

        }

        //设置层级和parent
        setLevelAndParent(nodeList);

        return nodeList;
    }


    /**
     * 根据id 选择Node
     *
     * @param id
     * @param list
     * @return
     */
    public static <T> Node<T> filterNodeById(final String id, final List<Node<T>> list) {

        for (Node<T> item : list) {

            if (item.getId().equals(id)) {
                return item;
            }

            if (item.getChildren() != null) {
                Node<T> result = filterNodeById(id, item.getChildren());
                if (result != null) {
                    return result;
                }
            }

        }
        return null;
    }

    /**
     * 获取当前节点以及当前节点的子节点中，已经选择的选项
     *
     * @param node
     * @return
     */
    public static <T> List<Node<T>> filterCheckedNodeList(Node<T> node) {

        List<Node<T>> resultList = new ArrayList<>();

        if (node == null) {
            return resultList;
        }

        if (node.isChecked()) {
            resultList.add(node);
        }

        if (node.getChildren() != null) {
            for (Node<T> item : node.getChildren()) {
                resultList.addAll(filterCheckedNodeList(item));
            }
        }

        return resultList;
    }





    private static <T> void uncheckParentNode(Node<T> node) {
        //当前选择，则父已有的选择取消
        if (node.getParent() != null) {
            node.getParent().setChecked(false);
            uncheckParentNode(node.getParent());
        }
    }


    private static <T> void uncheckChildNode(Node<T> node) {
        //当前选择，则下面的子节点选择取消
        if (node.getChildren() != null) {
            for (Node<T> itemChild : node.getChildren()) {
                itemChild.setChecked(false);
                uncheckChildNode(itemChild);
            }
        }
    }



    /**
     * 根据pid 和 childlist 设置 parent 和 level
     *
     * @param nodeList
     * @param <T>
     */
    private static <T> void setLevelAndParent(List<Node<T>> nodeList) {

        if (nodeList == null) {
            return;
        }

        for (Node<T> node : nodeList) {

            //根节点
            if (TextUtils.isEmpty(node.getpId())) {
                node.setParent(null);
                node.setLevel(0);
            }

            //递归设置
            if (node.getChildren() != null) {
                for (Node<T> child : node.getChildren()) {
                    child.setParent(node);
                    child.setLevel(node.getLevel() + 1);
                }
                setLevelAndParent(node.getChildren());
            }

        }
    }


    /**
     * T 类型 示例
     *
     * public static class TestModel {
     *
     * @param t
     * @param <T>
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @NodeId public String id;       //必填字段
     * @NodeName public String name;     //必填字段
     * @NodeLabel public String label;
     * @NodePid public String parentId;
     * @NodeChild public List<TestModel> child;
     * }
     */
    private static <T> Node<T> data2Node(T t) throws IllegalAccessException, IllegalArgumentException {

        if (t == null) {
            return null;
        }

        String id = "";
        String pid = "";
        String name = "";
        String label = "";
        List<T> childs = null;

        Class clazz = t.getClass();
        //反射获取类中的字段
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            //id
            if (field.getAnnotation(NodeId.class) != null) {
                field.setAccessible(true);
                id = (String) field.get(t);
            }

            //pid
            if (field.getAnnotation(NodePid.class) != null) {
                field.setAccessible(true);
                pid = (String) field.get(t);
            }


            //name
            if (field.getAnnotation(NodeName.class) != null) {
                field.setAccessible(true);
                name = (String) field.get(t);
            }

            //lable
            if (field.getAnnotation(NodeLabel.class) != null) {
                field.setAccessible(true);
                label = (String) field.get(t);
            }

            //child
            if (field.getAnnotation(NodeChild.class) != null) {
                field.setAccessible(true);
                childs = (List<T>) field.get(t);
            }

        }

        //非法节点
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name)) {
            return null;
        }

        Node<T> node = new Node(id, pid, name);

        if (childs != null && childs.size() > 0) {
            ArrayList<Node<T>> nodeChilds = new ArrayList<>();
            for (T item : childs) {
                Node<T> itemNode = data2Node(item);
                if (itemNode != null) {
                    nodeChilds.add(itemNode);
                }
            }
            node.setChildren(nodeChilds);
            node.setType(Node.TREE_NODE);
        } else {
            node.setChildren(null);
            node.setType(Node.TREE_LEAF);
        }

        node.setLabel(label);
        node.setContent(t);

        return node;
    }


}
