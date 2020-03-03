package me.soushin.tinmvparms.base.app.utils;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewUtils {

    /**
     * 初始化网格布局的RecyclerView
     * @param context
     * @param rv
     * @param adapter
     * @param spanCount
     */
    public static void initRecyclerViewG(Context context, RecyclerView rv, RecyclerView.Adapter adapter, int spanCount){
        rv.setLayoutManager(new GridLayoutManager(context,spanCount));
        rv.setAdapter(adapter);
    }

    /**
     * 初始化竖向布局的RecyclerView
     * @param context
     * @param rv
     * @param adapter
     */
    public static void initRecyclerViewV(Context context, RecyclerView rv, RecyclerView.Adapter adapter){
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setAdapter(adapter);
    }

    /**
     * 初始化横向布局的RecyclerView
     * @param context
     * @param rv
     * @param adapter
     */
    public static void initRecyclerViewH(Context context, RecyclerView rv, RecyclerView.Adapter adapter){
        rv.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL,false));
        rv.setAdapter(adapter);
    }


}
