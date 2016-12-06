# RecycleView实现Android树形列表控件

本文主要通过RecycleView实现了任意层次的树形ListView。实现的主要原理是通过保存两份List数据，一份是完整数据，一份是当前展开的数据来实现。再通过点击事件，控制展开数据的内容。这样即是实现树形控件。


## TreeView的使用

- build.gradle

```
//增加treeview包引用
compile 'org.chen.treeview:treeview:1.0.1'

```

- activity_main.xml

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    tools:context="com.chen.treedemo.MainActivity">


    <Button
        android:id="@+id/btn_select"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="选择节点" />


    <TextView
        android:id="@+id/tv_result"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:text="结果列表" />


    //引入控件
    <com.chen.treeview.TreeRecyclerView
        android:id="@+id/tree_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="10dp">

    </com.chen.treeview.TreeRecyclerView>


</LinearLayout>


```

- TestModel.java

```

//测试数据模型, 必须添加@NodeId ,  @NodeName 两个注解字段
public class TestModel {
	 //通过注解的方式，进行model转换
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


```


- MainActivity.java

```

public class MainActivity extends AppCompatActivity {

    private TreeRecyclerView tree_view;
    private Button btn_select;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tree_view = (TreeRecyclerView) findViewById(R.id.tree_view);

        //传入数据，以及对应的模式
        tree_view.setData(generateList(), TreeRecyclerView.MODE_SINGLE_SELECT);

        tv_result = (TextView) findViewById(R.id.tv_result);
        btn_select = (Button) findViewById(R.id.btn_select);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer buffer = new StringBuffer();
                ArrayList<TestModel> list = new ArrayList<>();
                //获取当前已经选中的数据， 传入进去list类型，必须与存储数据类型相同
                tree_view.getSelectedItems(list);
                for (TestModel model : list) {
                    buffer.append(model.name + " ");
                }
                tv_result.setText(buffer.toString());
            }
        });

    }


    //手动生成的测试数据
    private List<TestModel> generateList() {

        ArrayList<TestModel> result = new ArrayList<>();

        TestModel model1 = new TestModel();
        model1.id = "1";
        model1.name = "上海";
        model1.label = "魔都";
        model1.parentId = "";
        model1.child = null;


        TestModel model2 = new TestModel();
        model2.id = "2";
        model2.name = "北京";
        model2.label = "帝都";
        model2.parentId = "";
        model2.child = null;


        TestModel model3 = new TestModel();
        model3.id = "3";
        model3.name = "江苏";
        model3.label = "省";
        model3.parentId = "";
        model3.child = null;

        TestModel model4 = new TestModel();
        model4.id = "4";
        model4.name = "浙江";
        model4.label = "省";
        model4.parentId = "";
        model4.child = null;


        TestModel model5 = new TestModel();
        model5.id = "5";
        model5.name = "天津";
        model5.label = "直辖市";
        model5.parentId = "";
        model5.child = null;


        TestModel model31 = new TestModel();
        model31.id = "31";
        model31.name = "南京";
        model31.label = "省会城市";
        model31.parentId = "3";
        model31.child = null;


        TestModel model32 = new TestModel();
        model32.id = "32";
        model32.name = "苏州";
        model32.label = "牛B城市";
        model32.parentId = "3";
        model32.child = null;


        TestModel model33 = new TestModel();
        model33.id = "33";
        model33.name = "无锡";
        model33.label = "灵山大佛";
        model33.parentId = "3";
        model33.child = null;


        TestModel model41 = new TestModel();
        model41.id = "41";
        model41.name = "杭州";
        model41.label = "省会城市";
        model41.parentId = "4";
        model41.child = null;


        TestModel model42 = new TestModel();
        model42.id = "42";
        model42.name = "温州";
        model42.label = "炒房团+皮革厂";
        model42.parentId = "4";
        model42.child = null;


        TestModel model311 = new TestModel();
        model311.id = "311";
        model311.name = "玄武区";
        model311.label = "玄武湖好美";
        model311.parentId = "31";
        model311.child = null;

        TestModel model312 = new TestModel();
        model312.id = "312";
        model312.name = "建邺区";
        model312.label = "河西新城，房价真贵";
        model312.parentId = "31";
        model312.child = null;


        TestModel model411 = new TestModel();
        model411.id = "411";
        model411.name = "西湖区";
        model411.label = "西湖区好美，湖边来一套别墅";
        model411.parentId = "41";
        model411.child = null;

        TestModel model412 = new TestModel();
        model412.id = "412";
        model412.name = "滨江区";
        model412.label = "科技新城，阿里网易";
        model412.parentId = "41";
        model412.child = null;


        //杭州市
        ArrayList<TestModel> list_41 = new ArrayList<>();
        list_41.add(model411);
        list_41.add(model412);
        model41.child = list_41;

        //南京市
        ArrayList<TestModel> list_31 = new ArrayList<>();
        list_31.add(model311);
        list_31.add(model312);
        model31.child = list_31;


        //江苏省
        ArrayList<TestModel> list_3 = new ArrayList<>();
        list_3.add(model31);
        list_3.add(model32);
        list_3.add(model33);
        model3.child = list_3;


        //江苏省
        ArrayList<TestModel> list_4 = new ArrayList<>();
        list_4.add(model41);
        list_4.add(model42);
        model4.child = list_4;

        result.add(model1);
        result.add(model2);
        result.add(model3);
        result.add(model4);
        result.add(model5);

        return result;

    }



}

```

## TreeView源码解析


- Node.java

	```
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

    ... 省略get set 方法

	```


- TreeRecyclerView.java

```
public class TreeRecyclerView extends FrameLayout {

    public final static int MODE_SINGLE_SELECT = 1;        //单选模式
    public final static int MODE_MULTI_SELECT = 2;         //无限制，多选模式
    public final static int MODE_DEPEND_SELECT = 3;        //父子节点不可同选，多选模式
    public final static int MODE_CLICK_SELECT = 4;         //选中跳转模式

    private RecyclerView mRecyclerView;
    private TreeRecyclerAdapter mAdapter;


    public TreeRecyclerView(Context context) {
        super(context);
        initView(context);
    }

    public TreeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TreeRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mRecyclerView, lp);
        mAdapter = new TreeRecyclerAdapter<>(mRecyclerView.getContext());
    }


    /**
     * 初始化树形折叠控件数据
     *
     *
     * T 类型 示例
     *
     * public static class TestModel {
     *
     * @NodeId public String id;                    //必填字段
     * @NodeName public String name;                  //必填字段
     * @NodeLabel public String label;
     * @NodePid public String parentId;              //父节点id
     * @NodeChild public List<TestModel> child;        //child用来表示层级关系, child为空，则表示叶子节点
     * ...
     * others
     * ...
     * }
     */
    public <T> void setData(List<T> list, int mode) {

        ArrayList<Node<T>> nodeList = new ArrayList<>();

        try {
            nodeList = NodeDataConverter.convertToNodeList(list);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setMode(mode);
        mAdapter.addAllData(nodeList);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 设置点击事件, MODE_CLICK_SELECT模式下需要使用
     */
    public void setOnItemClickListener(OnNodeItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
    }


    /**
     * 获取选中的内容
     *
     * @param <T>
     * @return
     */
    public <T> void getSelectedItems(List<T> list) {
        mAdapter.getSelectedItems(list);
    }


    /**
     * 设置折叠控件的选择模式
     *
     * @param mode
     */
    private void setMode(int mode) {
        if (mode != MODE_SINGLE_SELECT
                && mode != MODE_MULTI_SELECT
                && mode != MODE_DEPEND_SELECT
                && mode != MODE_CLICK_SELECT) {
            return;
        }
        mAdapter.setMode(mode);
    }


}

```

- TreeRecyclerAdapter.java

```
class TreeRecyclerAdapter<T> extends RecyclerView.Adapter<TreeBaseViewHolder> {

    private Context mContext;
    private List<Node<T>> mVisibleNodes;
    private List<Node<T>> mRootNodes;
    private OnNodeItemClickListener mOnNodeItemClickListener;
    private int mSelectMode = TreeRecyclerView.MODE_SINGLE_SELECT;

    private OnNodeSwitchListener mOnNodeSwitchListener = new OnNodeSwitchListener() {

        @Override
        public void onExpand(Node node, int position) {
            NodeDataConverter.expandNode(NodeDataConverter.filterNodeById(node.getId(), mRootNodes));
            rearrangeVisibleNodes();
        }

        @Override
        public void onShrink(Node node, int position) {
            NodeDataConverter.shrinkNode(NodeDataConverter.filterNodeById(node.getId(), mRootNodes));
            rearrangeVisibleNodes();
        }
    };


    private OnNodeCheckListener mOnNodeCheckListener = new OnNodeCheckListener() {
        @Override
        public void onCheck(boolean isChecked, int position, Node node) {
            if (mOnNodeItemClickListener != null) {
                mOnNodeItemClickListener.onItemClick(node.getContent());
            }

            NodeDataConverter.checkNode(node.getId(), isChecked, mSelectMode, mVisibleNodes);
            notifyDataSetChanged();

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
        }

        for (Node<T> item : nodes) {
            filterVisibleNodes(item);
        }

        notifyDataSetChanged();
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
    public <T> void getSelectedItems(List<T> list) {

        List<Node<T>> resultNodeList = new ArrayList<>();

        for (Node item : mRootNodes) {
            resultNodeList.addAll(NodeDataConverter.filterCheckedNodeList(item));
        }

        for (Node<T> item : resultNodeList) {
            list.add(item.getContent());
        }

    }


    @Override
    public TreeBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case Node.TREE_NODE:
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.listitem_tree_node, parent, false);
                return new TreeNodeViewHolder<T>(view);
            case Node.TREE_LEAF:
                view = LayoutInflater.from(mContext).inflate(
                        R.layout.listitem_tree_leaf, parent, false);
                return new TreeLeafViewHolder<T>(view);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(TreeBaseViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case Node.TREE_NODE:
                TreeNodeViewHolder<T> nodeViewHolder = (TreeNodeViewHolder<T>) holder;
                nodeViewHolder.bindView(mVisibleNodes.get(position),
                        position, mOnNodeSwitchListener, mOnNodeCheckListener);
                break;
            case Node.TREE_LEAF:
                TreeLeafViewHolder<T> leafViewHolder = (TreeLeafViewHolder<T>) holder;
                leafViewHolder.bindView(mVisibleNodes.get(position),
                        position, mOnNodeCheckListener);
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

```

- Viewholder

	- TreeBaseViewHolder.java

	```
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

	```

	- TreeLeafViewHolder.java

	```
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

	```

	- TreeNodeViewHolder.java

	```
	public class TreeNodeViewHolder<T> extends TreeBaseViewHolder {

    private RelativeLayout rl_content;
    private ImageView iv_icon;
    private TextView tv_name;
    private TextView tv_label;
    private ImageView iv_checkbox;


    public TreeNodeViewHolder(View itemView) {
        super(itemView);
        rl_content = (RelativeLayout) itemView.findViewById(R.id.rl_content);
        tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
        tv_label = (TextView) itemView.findViewById(R.id.tv_label);
        iv_checkbox = (ImageView) itemView.findViewById(R.id.iv_checkbox);
    }

    public void bindView(final Node<T> node,
                         final int position,
                         final OnNodeSwitchListener onNodeSwitchListener,
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

        //设置节点展开状态
        setExpanded(iv_icon, node.isExpanded());


        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (node.isExpanded()) {
                    onNodeSwitchListener.onShrink(node, position);
                    rotationExpandIcon(iv_icon, 0, -90);
                } else {
                    onNodeSwitchListener.onExpand(node, position);
                    rotationExpandIcon(iv_icon, -90, 0);
                }

            }
        });


        rl_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onNodeCheckListener != null) {
                    onNodeCheckListener.onCheck(!node.isChecked(), position, node);
                }
            }
        });
    }


    /**
     * 设置展开图标效果
     *
     * @param view
     * @param isExpanded
     */
    private void setExpanded(View view, boolean isExpanded) {
        if (isExpanded) {
            view.setRotation(0);
        } else {
            view.setRotation(-90);
        }
    }


    /**
     * 根据角度选择图标
     *
     * @param view
     * @param from
     * @param to
     */
    private void rotationExpandIcon(final View view, final float from, final float to) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);
            valueAnimator.setDuration(150);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }

	}
	```




