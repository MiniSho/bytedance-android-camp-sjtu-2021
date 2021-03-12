package com.example.chapter3.homework;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class PlaceholderFragment extends Fragment {

    private LottieAnimationView mAnimationView;
    private RecyclerView mRecyclerView;

    private static final String TAG = "PlaceholderFragment";
    private final ListAdapter mListAdapter = new ListAdapter();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);

        mAnimationView = view.findViewById(R.id.animation_view);

        mRecyclerView = view.findViewById(R.id.rv);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mListAdapter);
        mRecyclerView.setAlpha(0.0f);

        final List<String> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            items.add("item "+ i);
        }
        mListAdapter.notifyItems(items);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: postDelayed");
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(mAnimationView,
                        "alpha",
                        1.0f,
                        0.0f);
                animator1.setDuration(1000);
                /*animator1.setRepeatMode();
                animator1.setRepeatCount(1);*/

                ObjectAnimator animator2 = ObjectAnimator.ofFloat(mRecyclerView,
                        "alpha",
                        0.0f,
                        1.0f);
                animator1.setDuration(1000);
                animator1.setRepeatCount(1);

                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(animator1,animator2);
                animatorSet.start();

            }
        }, 5000);
    }
}
