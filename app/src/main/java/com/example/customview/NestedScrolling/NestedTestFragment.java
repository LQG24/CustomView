package com.example.customview.NestedScrolling;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customview.NestedScrolling.common.CommonRecyclerAdapter;
import com.example.customview.NestedScrolling.common.CommonRecyclerHolder;
import com.example.customview.R;


import java.util.ArrayList;
import java.util.List;


public class NestedTestFragment extends Fragment {
    private RecyclerView mRecyclerView;



    private String mText;

    public static Fragment newIntance(String text) {
       NestedTestFragment fragment = new NestedTestFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text",text);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_1,container,false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mText = getArguments().getString("text","");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);


        CommonRecyclerAdapter<String> commonRecyclerAdapter = new CommonRecyclerAdapter<String>(requireContext(),createData(), R.layout.item_recycle) {

            @Override
            public void convert(CommonRecyclerHolder holder, String item, int position, boolean isScrolling) {
                holder.setText(R.id.item_tv,item);
            }
        };
        mRecyclerView.setAdapter(commonRecyclerAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));


    }

    private List<String> createData(){
        ArrayList<String> result = new ArrayList<>(100);
        for(int i = 0; i < 100; i++){
            result.add(mText + i);
        }
        return result;
    }



}
