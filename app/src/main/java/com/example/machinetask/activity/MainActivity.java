package com.example.machinetask.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.machinetask.AppConstants;
import com.example.machinetask.R;
import com.example.machinetask.adapter.PromoAdapter;
import com.example.machinetask.model.TestModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mListRecylerView;
    AppCompatTextView mCartItems;


    //interface
    public static CartActvty.refrsh adapterCartRefresh;
    public static PromoAdapter.onClick adapterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //interface calling to handle count and item func
        adapterInterface = new PromoAdapter.onClick() {
            @Override
            public void itemFunc(int promo) {
                if(promo>0)
                {
                    mCartItems.setClickable(true);
                    mCartItems.setText("VEW CART ("+promo+" ITEMS )");
                }else{
                    mCartItems.setClickable(false);
                    mCartItems.setText("NO ITEMS IN CART");
                }


            }
        };

        //interface calling to handle refreshing
        adapterCartRefresh = new CartActvty.refrsh() {
            @Override
            public void refrshItesm() {
                mListRecylerView.removeAllViews();
                PromoAdapter promoAdapter = new PromoAdapter(AppConstants.mLst,MainActivity.this);
                mListRecylerView.setHasFixedSize(true);
                mListRecylerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mListRecylerView.setAdapter(promoAdapter);

                int count=0;
                for (int i=0;i<AppConstants.mLst.size();i++){
                    count = count+AppConstants.mLst.get(i).getCount();
                }

                if(count>0)
                {
                    mCartItems.setClickable(true);
                    mCartItems.setText("VEW CART ITEMS ("+count+" ITEMS )");
                }else{
                    mCartItems.setClickable(false);
                    mCartItems.setText("NO ITEMS IN CART");
                }
            }
        };

        //intialization
        mCartItems = findViewById(R.id.cartItemsTv);
        mListRecylerView = findViewById(R.id.listRv);
        mCartItems.setText("NO ITEMS IN CART");
        mCartItems.setClickable(false);

        //adding data to list (static)
        AppConstants.mLst.add(new TestModel(0,"Guaco de la Costa","a passion of cooking ins art for new recipe ",0,25,false));
        AppConstants.mLst.add(new TestModel(0,"Chicharan","a passion of cooking ins art for new item",0,50,false));
        AppConstants.mLst.add(new TestModel(0,"Chilitoes","a passion of cooking ins art for new item",0,100,false));
        AppConstants.mLst.add(new TestModel(0,"Lgaurdio","a passion of cooking ins art for new item",0,20,false));

        //Adapte calling above list data
        PromoAdapter promoAdapter = new PromoAdapter(AppConstants.mLst,MainActivity.this);
        mListRecylerView.setHasFixedSize(true);
        mListRecylerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mListRecylerView.setAdapter(promoAdapter);

        mCartItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check =0;
                for (int i=0;i<AppConstants.mLst.size();i++)
                {
                    if (AppConstants.mLst.get(i).isCheck())
                    {
                        check++;
                    }
                }
                Intent cartscreen = new Intent(MainActivity.this,CartActvty.class);
                startActivity(cartscreen);
            }
        });
    }
}