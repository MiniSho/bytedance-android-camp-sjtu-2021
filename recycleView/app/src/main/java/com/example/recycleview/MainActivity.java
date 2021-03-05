package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mAddItemBtn;

    private TextView mDelItemBtn;

    private RecyclerView mRecyclerView;

    private SearchAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
//        initAction();
    }

//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//
////        if(id == R.id.rv_add_item_btn) {
////            mAdapter.addNewItem();
////            mLayoutManager.scrollToPosition(0);
////        } else if(id == R.id.rv_del_item_btn){
////            mAdapter.deleteItem();
////            mLayoutManager.scrollToPosition(0);
////        }
//    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new SearchAdapter(getData());

        mAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"click " + position + " item", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this,"long click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
//        mAddItemBtn = (TextView) findViewById(R.id.rv_add_item_btn);
//        mDelItemBtn = (TextView) findViewById(R.id.rv_del_item_btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        // 设置布局管理器
        mRecyclerView.setLayoutManager(mLayoutManager);
        // 设置adapter
        mRecyclerView.setAdapter(mAdapter);
        // 设置Item添加和移除的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // 设置Item之间间隔样式
        mRecyclerView.addItemDecoration(new Decoration(this, LinearLayoutManager.VERTICAL));
    }

//    private void initAction() {
//        mAddItemBtn.setOnClickListener(this);
//        mDelItemBtn.setOnClickListener(this);
//    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        for(int i = 1; i < 101; i++) {
            data.add("这是第"+ i + "行");
        }

        return data;
    }
}