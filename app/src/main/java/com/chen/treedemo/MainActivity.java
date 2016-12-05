package com.chen.treedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chen.treeview.TreeRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TreeRecyclerView tree_view;
    private Button btn_select;
    private TextView tv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tree_view = (TreeRecyclerView) findViewById(R.id.tree_view);
        tree_view.setData(generateList(), TreeRecyclerView.MODE_SINGLE_SELECT);

        tv_result = (TextView) findViewById(R.id.tv_result);
        btn_select = (Button) findViewById(R.id.btn_select);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuffer buffer = new StringBuffer();
                ArrayList<TestModel> list = new ArrayList<>();
                tree_view.getSelectedItems(list);
                for (TestModel model : list) {
                    buffer.append(model.name + " ");
                }
                tv_result.setText(buffer.toString());
            }
        });

    }

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
