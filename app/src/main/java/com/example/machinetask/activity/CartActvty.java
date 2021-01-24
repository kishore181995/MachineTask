package com.example.machinetask.activity;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.machinetask.AppConstants;
import com.example.machinetask.R;
import com.example.machinetask.adapter.PromoAdapter;
import com.example.machinetask.model.TestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;

public class CartActvty extends AppCompatActivity {

    AppCompatTextView showTv,cost;
    AppCompatImageView mbac;
    RecyclerView mCarttems;
    List<TestModel> mListModel = new ArrayList<>();
    int count=0;
    int sum=0;
    AppCompatTextView noitemsTv;
    RelativeLayout placeOrder;
    public static PromoAdapter.onClick adapter;
    PromoAdapter promoAdapter;
    AppCompatImageView donet,aeawy;
    CartActvty.refrsh callback;
    private boolean open = false;

    public static PromoAdapter.onRemove callbaclremove;

    public interface refrsh
    {
        public void refrshItesm();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.actvty_cart);

        //interface initialization
        callback = MainActivity.adapterCartRefresh;

        //interface calling to handle count and item func
        adapter = new PromoAdapter.onClick() {
            @Override
            public void itemFunc(int promo) {

                int sum=0;
                for (int i=0;i< mListModel.size();i++)
                {
                    sum = sum + (mListModel.get(i).getCount()*mListModel.get(i).getCost());
                }
                cost.setText(""+sum);
            }
        };

        //interface calling to handle Adapter items when removing items and size less than 2
        callbaclremove = new PromoAdapter.onRemove() {
            @Override
            public void removeItem(String name)
            {
                mCarttems.removeAllViews();
                for (int i=0;i< AppConstants.mLst.size();i++)
                {
                    if (AppConstants.mLst.get(i).getName().equalsIgnoreCase(name))
                    {
                        AppConstants.mLst.get(i).setCheck(false);
                    }
                }
                mListModel.clear();
                count=0;
                for (int i=0;i< AppConstants.mLst.size();i++)
                {
                    if (AppConstants.mLst.get(i).isCheck()){
                        count++;
                        mListModel.add(AppConstants.mLst.get(i));
                        sum = sum + (AppConstants.mLst.get(i).getCount()*AppConstants.mLst.get(i).getCost());
                    }
                }
                cost.setText(""+sum);
                List<TestModel> mCheckList = new ArrayList<>();
                if (count>2)
                {
                    for (int i=0;i<2;i++) {
                        mCheckList.add(mListModel.get(i));
                    }
                    promoAdapter = new PromoAdapter(mCheckList,CartActvty.this, 1);
                    showTv.setVisibility(View.VISIBLE);
                }else {
                    promoAdapter = new PromoAdapter(mListModel,CartActvty.this, 1);
                    showTv.setVisibility(View.GONE);
                }
                mCarttems.setHasFixedSize(true);
                mCarttems.setLayoutManager(new LinearLayoutManager(CartActvty.this));
                mCarttems.setAdapter(promoAdapter);

                if (count==0)
                {
                    placeOrder.setVisibility(View.GONE);
                    noitemsTv.setVisibility(View.VISIBLE);
                }
            }
        };

        //intialization
        mCarttems = findViewById(R.id.cartItemsRv);
        showTv = findViewById(R.id.showTv);
        mbac = findViewById(R.id.bacv);
        placeOrder  = findViewById(R.id.placeOrderRl);
        noitemsTv  = findViewById(R.id.noitemsTv);
        cost = findViewById(R.id.totalCostTv);
        showTv.setPaintFlags(showTv.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        donet  = findViewById(R.id.dneRb);
        aeawy  = findViewById(R.id.taeRb);

        //on Click
        donet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donet.setBackgroundResource(R.drawable.fill);
                aeawy.setBackgroundResource(R.drawable.empty);
            }
        });
        aeawy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donet.setBackgroundResource(R.drawable.empty);
                aeawy.setBackgroundResource(R.drawable.fill);
            }
        });

        mbac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackPressed();
            }
        });

        //Setting of the data
        for (int i=0;i< AppConstants.mLst.size();i++)
        {
            if (AppConstants.mLst.get(i).isCheck()){
                count++;
                mListModel.add(AppConstants.mLst.get(i));
                sum = sum + (AppConstants.mLst.get(i).getCount()*AppConstants.mLst.get(i).getCost());
            }
        }

        cost.setText(""+sum);
        List<TestModel> mCheckList = new ArrayList<>();
        if (count>2)
        {

            for (int i=0;i<2;i++)
            {
                mCheckList.add(mListModel.get(i));
            }
            promoAdapter = new PromoAdapter(mCheckList,CartActvty.this, 1);

            showTv.setVisibility(View.VISIBLE);
        }else
        {
            promoAdapter = new PromoAdapter(mListModel,CartActvty.this, 1);
            showTv.setVisibility(View.GONE);
        }
        mCarttems.setHasFixedSize(true);
        mCarttems.setLayoutManager(new LinearLayoutManager(CartActvty.this));
        mCarttems.setAdapter(promoAdapter);

        showTv.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v)
            {
                    mCarttems.removeAllViews();
                    promoAdapter = new PromoAdapter(mListModel,CartActvty.this, 1);
                    mCarttems.setHasFixedSize(true);
                    mCarttems.setLayoutManager(new LinearLayoutManager(CartActvty.this));
                    mCarttems.setAdapter(promoAdapter);
                    showTv.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void onBackPressed() {
        BackPressed();
    }

    private void BackPressed() {
        for (int i=0;i< AppConstants.mLst.size();i++)
        {
            for (int j=0;j< mListModel.size();j++)
            {
                if (mListModel.get(j).getName().equalsIgnoreCase(AppConstants.mLst.get(i).getName()))
                {
                    AppConstants.mLst.remove(i);
                    AppConstants.mLst.add(i,mListModel.get(j));
                }
            }
        }
        if (callback!=null)
        {
            callback.refrshItesm();
        }
        finish();
    }
}
