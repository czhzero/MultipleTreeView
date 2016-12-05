package com.chen.treeview.viewholder;

import android.animation.ValueAnimator;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chen.treeview.R;
import com.chen.treeview.listener.OnNodeCheckListener;
import com.chen.treeview.listener.OnNodeSwitchListener;
import com.chen.treeview.model.Node;


/**
 * Created by chenzhaohua on 16/11/25.
 */
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
