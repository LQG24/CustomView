package com.example.customview;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customview.Animator.OAnimPointView;
import com.example.customview.View.CustomLayoutManager;
import com.example.customview.View.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
    private LinearLayout mRootLLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mRootLLayout =findViewById(R.id.root_clayout);


//        setContentView(R.layout.activity_main);
//        addFlowLayout();
        addRecyclerView();


//        addSingleDraw();
//        mRootLLayout.addView(new MyRegionView(this));
//        mRootLLayout.addView(new DrawText(this),new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        mRootLLayout.addView(new PointView(this),new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        addObjectAnimatorView();
    }

    //实现ItemDecoration与蒙版效果
    private void addRecyclerView() {
        setContentView(R.layout.activity_main);
        mRootLLayout = findViewById(R.id.root_clayout);
        RecyclerView recyclerView = new RecyclerView(this);
//        recyclerView.addItemDecoration(new LinearItemDecoration(this));

        ArrayList<String> mDatas = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            mDatas.add("第 " + i + " 个item");
        }
        MyAdater myAdater = new MyAdater(mDatas);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new CustomLayoutManager());
        recyclerView.setAdapter(myAdater);
        mRootLLayout.addView(recyclerView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    private class MyAdater extends RecyclerView.Adapter<MyViewHolder> {
        private List<String> mList;

        public MyAdater(ArrayList<String> mDatas) {
            super();
            mList = mDatas;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.txt.setText(mList.get(position));
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt = itemView.findViewById(R.id.txt_content);
        }
    }

    //添加FlowLayout自适应容器
    private void addFlowLayout() {
        setContentView(R.layout.activity_flow_layout);
    }


    /**
     * 设置pointRadius属性
     * <p>
     * ObjectAnimator  属性第一个字母大写 并加上set 调用自身的方法
     * 例如:在ObjectAnimator中，则是先根据属性值拼装成对应的set函数的名字，
     * 比如这里的scaleY的拼装方法就是将属性的第一个字母强制大写后，与set拼接，所以就是setScaleY。
     * 然后通过反射找到对应控件的setScaleY(float scaleY)函数，将当前数字值做为setScaleY(float scale)的参数将其传入。
     * <p>
     * ObjectAnimator 会动态调用OAnimPointView 自身的setPointRadius
     */
    private void addObjectAnimatorView() {
        final OAnimPointView oAnimPointView = new OAnimPointView(this);
        mRootLLayout.addView(oAnimPointView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofInt(oAnimPointView, "pointRadius", 0, 300, 0);
                objectAnimator.setDuration(3000);
                objectAnimator.setRepeatCount(-1);
                objectAnimator.start();
            }
        }, 2000);

    }

    //
    private void addSingleDraw() {
        mRootLLayout.addView(new SimgleDraw(this));
    }
}
