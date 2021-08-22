package com.example.customview.coordinatorLayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customview.NestedScrolling.common.CommonRecyclerAdapter;
import com.example.customview.NestedScrolling.common.CommonRecyclerHolder;
import com.example.customview.R;

import java.util.ArrayList;
import java.util.List;
/**
 * #CustomBehaviorActivity 自定义Behavior的嵌套滑动交互效果
 */
public class CustomBehaviorActivity extends AppCompatActivity {

    private String mText = "自定义Behavior";
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo02);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(linearLayoutManager);


        CommonRecyclerAdapter<String> commonRecyclerAdapter = new CommonRecyclerAdapter<String>(this,createData(), R.layout.item_recycle) {

            @Override
            public void convert(CommonRecyclerHolder holder, String item, int position, boolean isScrolling) {
                holder.setText(R.id.item_tv,item);
            }
        };
       recyclerView.setAdapter(commonRecyclerAdapter);
       recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private List<String> createData(){
        ArrayList<String> result = new ArrayList<>(100);
        for(int i = 0; i < 100; i++){
            result.add(mText + i);
        }
        return result;
    }
}