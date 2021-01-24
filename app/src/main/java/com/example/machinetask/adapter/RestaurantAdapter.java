package com.example.machinetask.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.machinetask.R;
import com.example.machinetask.activity.CartActivity;
import com.example.machinetask.activity.MainActivity;
import com.example.machinetask.model.TestModel;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    List<TestModel> mListCodesAdd;
    Context context;
    int mainCount=0;
    RestaurantAdapter.onClick callback;
    RestaurantAdapter.onRemove callbaclremove;

    public RestaurantAdapter(List<TestModel> mLst, CartActivity cartActvty, int i) {
        this.context = cartActvty;
        this.callback = CartActivity.adapter;
        this.mListCodesAdd = mLst;
        this.mainCount = i;
        this.callbaclremove = CartActivity.callbaclremove;
    }

    public interface onClick{
        public void itemFunc(int promo);
    }

    public interface onRemove
    {
        public void removeItem(String name);
    }

    public RestaurantAdapter(List<TestModel> mListCodes, MainActivity promoCodesActivity) {
        this.context = promoCodesActivity;
        this.mListCodesAdd = mListCodes;
        callback= MainActivity.adapterInterface;
    }

    @NonNull
    @Override
    public RestaurantAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_items_restaurant, parent, false);
        return new RestaurantAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final TestModel response = mListCodesAdd.get(position);
        holder.itemName.setText(response.getName());
        holder.itemDetails.setText(response.getDetals());
        holder.cost.setText(""+response.getCost());
        holder.addLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (response.count>=0 && response.count<20)
                {
                    int count = response.getCount();
                    count++;
                    response.setCount(count);
                    holder.countLl.setVisibility(View.VISIBLE);
                    holder.count.setText(""+response.getCount());
                    holder.minus.setVisibility(View.VISIBLE);
                }

                checkCount();
                if (response.count>0)
                {
                    response.setCheck(true);
                }else{
                    response.setCheck(false);
                }

            }
        });

        holder.minLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (response.count>0)
                {
                    int count = response.getCount();
                    count--;
                    if(count>0)
                    {
                        response.setCount(count);
                        holder.countLl.setVisibility(View.VISIBLE);
                        holder.count.setText(""+response.getCount());
                        holder.minus.setVisibility(View.VISIBLE);
                    }else{
                        response.setCount(0);
                        holder.countLl.setVisibility(View.GONE);
                        holder.minus.setVisibility(View.GONE);
                    }
                }
                if (response.count>0)
                {
                    response.setCheck(true);
                }else{
                    response.setCheck(false);
                }
                if (mainCount==1)
                {
                    if(mListCodesAdd.get(position).getCount()==0)
                    {
                        String name = mListCodesAdd.get(position).getName();
                        mListCodesAdd.remove(position);
                        if(mListCodesAdd.size()==2 || (mListCodesAdd.size()<2))
                        {
                            if(callbaclremove!=null)
                            {
                                callbaclremove.removeItem(name);
                            }
                        }

                        notifyDataSetChanged();
                    }

                }
                checkCount();
            }
        });

        if(response.getCount()>0){
            holder.countLl.setVisibility(View.VISIBLE);
            holder.count.setText(""+response.getCount());
            holder.minus.setVisibility(View.VISIBLE);
        }

        if (mainCount==1)
        {
            if(mListCodesAdd.get(position).getCount()==0)
            {
                holder.mainRl.setVisibility(View.INVISIBLE);
            }else
            {
                holder.mainRl.setVisibility(View.VISIBLE);
            }
        }


        //        holder.applyTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (callback!=null)
//                {
////                    callback.applypromoCode(response.getPromo());
//                }
//            }
//        });
    }

    private void checkCount() {

        int count=0;
        for (int i=0;i<mListCodesAdd.size();i++){
            count = count+mListCodesAdd.get(i).getCount();
        }

        if (callback!=null){
            callback.itemFunc(count);
        }
    }

    @Override
    public int getItemCount() {
        return mListCodesAdd.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView itemName,itemDetails,cost,nTv,dTv,count;
        LinearLayout countLl,addLl,minLl;
        RelativeLayout mainRl;
        AppCompatImageView plus,minus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemDetails =(AppCompatTextView)itemView.findViewById(R.id.itemdetailsTv);
            itemName =(AppCompatTextView)itemView.findViewById(R.id.itemName);
            nTv =(AppCompatTextView)itemView.findViewById(R.id.nTv);
            dTv =(AppCompatTextView)itemView.findViewById(R.id.dTv);

            count = (AppCompatTextView)itemView.findViewById(R.id.countTv);
            cost  = (AppCompatTextView)itemView.findViewById(R.id.costTv);
            dTv =(AppCompatTextView)itemView.findViewById(R.id.dTv);
            dTv =(AppCompatTextView)itemView.findViewById(R.id.dTv);
            countLl = (LinearLayout)itemView.findViewById(R.id.countLl);

            plus= (AppCompatImageView)itemView.findViewById(R.id.plusIv);
            minus = (AppCompatImageView)itemView.findViewById(R.id.minusIV);

            mainRl =(RelativeLayout)itemView.findViewById(R.id.mainRl);
            addLl =(LinearLayout) itemView.findViewById(R.id.addLL);
            minLl =(LinearLayout) itemView.findViewById(R.id.minusLl);
        }
    }
}
