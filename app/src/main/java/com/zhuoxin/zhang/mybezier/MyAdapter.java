package com.zhuoxin.zhang.mybezier;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/19.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHold> {

    private List<ShopBean> mBeanList = new ArrayList<>();


    public void setShopOnClickListener(ShopOnClickListener shopOnClickListener) {
        mShopOnClickListener = shopOnClickListener;
    }

    private ShopOnClickListener mShopOnClickListener;

    public MyAdapter(List<ShopBean> beanList) {
        mBeanList = beanList;


    }

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {


        return new MyViewHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_menu, null));
    }

    @Override
    public void onBindViewHolder(MyViewHold holder, final int position) {
        final ShopBean mShopBean = mBeanList.get(position);
        ((MyViewHold) holder).mTvTitle.setText(mShopBean.getTitle());
        int mCount = mShopBean.getCount();
        ((MyViewHold) holder).mTvCount.setText(mCount + "");
        ((MyViewHold) holder).mTvPrice.setText(mShopBean.getPrice());
        ((MyViewHold) holder).mIvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShopOnClickListener != null) {
                    mShopOnClickListener.add(view, position);

                }
            }
        });
        ((MyViewHold) holder).mIvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShopOnClickListener != null) {
                    mShopOnClickListener.remove(view, position);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mBeanList.size();
    }


    public interface ShopOnClickListener {
        void add(View view, int position);

        void remove(View view, int position);
    }




    static class  MyViewHold extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.tv_price)
        TextView mTvPrice;
        @BindView(R.id.iv_remove)
        ImageView mIvRemove;
        @BindView(R.id.tv_count)
        TextView mTvCount;
        @BindView(R.id.iv_add)
        ImageView mIvAdd;
        @BindView(R.id.right_dish_item)
        LinearLayout mRightDishItem;

        MyViewHold(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
