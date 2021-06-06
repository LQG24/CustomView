package com.example.customview.event_conflict;

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

import com.example.customview.MainActivity;
import com.example.customview.R;

import java.util.ArrayList;
import java.util.List;
//事件冲突外部拦截法
public class EventConflictActivity extends AppCompatActivity {
    private MyAdater myAdater;
    private HorizontalScrollViewEx mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_conflict);
        mScrollView = findViewById(R.id.scrollView);

        for (int i = 0; i < 3; i++) {
            createChildView(mScrollView);
        }


    }

    private void createChildView(ViewGroup viewGroup) {
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        ArrayList<String> mDatas = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mDatas.add("第 " + i + " 个item");
        }
        myAdater = new MyAdater(mDatas);
        recyclerView.setAdapter(myAdater);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(500,ViewGroup.LayoutParams.MATCH_PARENT);
        viewGroup.addView(recyclerView, layoutParams);
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