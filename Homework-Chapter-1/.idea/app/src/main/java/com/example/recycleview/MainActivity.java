package com.example.recycleview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new SearchAdapter(getData());

        mAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"click " + position + " item", Toast.LENGTH_SHORT).show();
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

    private SearchAdapter.OnItemClickListener MyItemClickListener = new SearchAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, int position) {
            System.out.println("1");
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("extra", position);
        //viewName可区分item及item内部控件
//            switch (v.getId()){
//                case R.id.btn_agree:
//                    Toast.makeText(MainActivity.this,"你点击了同意按钮"+(position+1),Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.btn_refuse:
//                    Toast.makeText(MainActivity.this,"你点击了拒绝按钮"+(position+1),Toast.LENGTH_SHORT).show();
//                    break;
//                default:
//                    Toast.makeText(MainActivity.this,"你点击了item按钮"+(position+1),Toast.LENGTH_SHORT).show();
//                    break;
//            }
        }
    };



}