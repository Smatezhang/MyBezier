package com.zhuoxin.zhang.mybezier;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MyAdapter.ShopOnClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.shopping_cart_bottom)
    LinearLayout mShoppingCartBottom;
    @BindView(R.id.shopping_cart)
    ImageView mShoppingCart;
    @BindView(R.id.shopping_cart_layout)
    FrameLayout mShoppingCartLayout;
    @BindView(R.id.main_layout)
    RelativeLayout mMainLayout;
    private List<ShopBean> datas;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        datas = new ArrayList<>();
        datas.add(new ShopBean("面包", "1.00", 10));
        datas.add(new ShopBean("蛋挞", "1.00", 10));
        datas.add(new ShopBean("牛奶", "1.00", 10));
        datas.add(new ShopBean("肠粉", "1.00", 10));
        datas.add(new ShopBean("绿茶饼", "1.00", 10));
        datas.add(new ShopBean("花卷", "1.00", 10));
        datas.add(new ShopBean("包子", "1.00", 10));

        datas.add(new ShopBean("粥", "1.00", 10));
        datas.add(new ShopBean("炒饭", "1.00", 10));
        datas.add(new ShopBean("炒米粉", "1.00", 10));
        datas.add(new ShopBean("炒粿条", "1.00", 10));
        datas.add(new ShopBean("炒牛河", "1.00", 10));
        datas.add(new ShopBean("炒菜", "1.00", 10));

        datas.add(new ShopBean("淋菜", "1.00", 10));
        datas.add(new ShopBean("川菜", "1.00", 10));
        datas.add(new ShopBean("湘菜", "1.00", 10));
        datas.add(new ShopBean("粤菜", "1.00", 10));
        datas.add(new ShopBean("赣菜", "1.00", 10));
        datas.add(new ShopBean("东北菜", "1.00", 10));

        datas.add(new ShopBean("淋菜", "1.00", 10));
        datas.add(new ShopBean("川菜", "1.00", 10));
        datas.add(new ShopBean("湘菜", "1.00", 10));
        datas.add(new ShopBean("粤菜", "1.00", 10));
        datas.add(new ShopBean("赣菜", "1.00", 10));
        datas.add(new ShopBean("东北菜", "1.00", 10));
        mRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecycler.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
        mAdapter = new MyAdapter(datas);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setShopOnClickListener(this);
    }


    @Override
    public void add(View view, int position) {
        ShopBean mShopBean = datas.get(position);
        int mCount = mShopBean.getCount();

        mShopBean.setCount(++mCount);
        mAdapter.notifyDataSetChanged();

        //贝塞尔起始数据点
        int[] startPosition = new int[2];
        //贝塞尔结束数据点
        int[] endPosition = new int[2];
        //控制点
        int[] recyclerPosition = new int[2];

        view.getLocationInWindow(startPosition);
        mShoppingCart.getLocationInWindow(endPosition);
        mRecycler.getLocationInWindow(recyclerPosition);

        PointF startF = new PointF();
        PointF endF = new PointF();
        PointF controllF = new PointF();

        startF.x = startPosition[0];
        startF.y = startPosition[1] - recyclerPosition[1];
        endF.x = endPosition[0];
        endF.y = endPosition[1] - recyclerPosition[1];
        controllF.x = endF.x;
        controllF.y = startF.y;

        final ImageView imageView = new ImageView(this);
        mMainLayout.addView(imageView);
        imageView.setImageResource(R.mipmap.ic_add_circle_blue_700_36dp);
        imageView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        imageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.item_dish_circle_size);
        imageView.setVisibility(View.VISIBLE);
        imageView.setX(startF.x);
        imageView.setY(startF.y);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierTypeEvaluator(controllF), startF, endF);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
                //Log.i("wangjtiao", "viewF:" + view.getX() + "," + view.getY());
            }
        });


        ObjectAnimator objectAnimatorX = new ObjectAnimator().ofFloat(mShoppingCart, "scaleX", 0.6f, 1.0f);
        ObjectAnimator objectAnimatorY = new ObjectAnimator().ofFloat(mShoppingCart, "scaleY", 0.6f, 1.0f);
        objectAnimatorX.setInterpolator(new AccelerateInterpolator());
        objectAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimatorX).with(objectAnimatorY).after(valueAnimator);
        set.setDuration(800);
        set.start();
        Toast.makeText(MainActivity.this, "加", Toast.LENGTH_SHORT).show();

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                imageView.setVisibility(View.GONE);
                mMainLayout.removeView(imageView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void remove(View view, int position) {
        ShopBean mShopBean = datas.get(position);
        int mCount = mShopBean.getCount();
        mShopBean.setCount(--mCount);
        mAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "减", Toast.LENGTH_SHORT).show();
    }


}
