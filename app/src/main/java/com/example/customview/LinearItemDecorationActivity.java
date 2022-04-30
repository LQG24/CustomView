package com.example.customview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customview.View.LinearItemDecoration;

import java.util.ArrayList;
import java.util.List;
/**
 * 自定义ItemDecoration与蒙版效果
 */
public class LinearItemDecorationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_layout_manager);
        recyclerView  = findViewById(R.id.recyclerView);
        addRecyclerView();
    }

    //实现ItemDecoration与蒙版效果
    private void addRecyclerView() {
        recyclerView.addItemDecoration(new LinearItemDecoration(this));

        ArrayList<String> mDatas = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            mDatas.add("第 " + i + " 个item");
        }
        MyAdater myAdater = new MyAdater(mDatas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdater);
    }

    private class MyAdater extends RecyclerView.Adapter<MyViewHolder> {
        private List<String> mList;
        private int createView = 0;
        private int bindView = 0;

        public MyAdater(ArrayList<String> mDatas) {
            super();
            mList = mDatas;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ++createView;
            Log.i(MainActivity.class.getSimpleName(), "onCreateViewHolder:" + createView);
            View itemView = LayoutInflater.from(getBaseContext()).inflate(R.layout.item_list, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ++bindView;
            Log.i(MainActivity.class.getSimpleName(), "onBindViewHolder:" + bindView);
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
}