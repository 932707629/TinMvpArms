package me.soushin.higou.base.app.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.Collection;
import java.util.List;

import me.soushin.higou.base.R;
import me.soushin.higou.base.app.utils.AppUtils;


/**
 * 基于SmartRefresh和BaseQuickAdapter封装的BaseAdapter
 * 设置adapter请用bindToRecyclerView();这种方式
 *
 * @author SouShin
 * @time 2018/11/13 11:34
 */
public class BaseAdapter<T extends MultiItemEntity, K extends BaseViewHolder> extends BaseMultiItemQuickAdapter<T, K> {

    public static final int item_type_one=1;
    public static final int item_type_two=2;
    public static final int item_type_three=3;


    public BaseAdapter() {
        super(null);
    }

    public BaseAdapter(@Nullable List<T> data) {
        super(data);
    }

    @Override
    protected void convert(K helper, T item) {

    }

    /**
     * 加载数据
     */
    public void showLoading(){
        setNewData(null);
        setEmptyView(getEmptyView(R.layout.layout_loading));
    }

    /**
     * 使用默认的空布局
     * @param srlRefresh
     * @param data
     */
    public void setNewData(@Nullable SwipeRefreshLayout srlRefresh, @Nullable List<T> data){
        int layoutRes= AppUtils.isEmpty(data)?R.layout.layout_no_data:0;
        setNewData(srlRefresh,data,layoutRes);
    }

    /**
     * 使用自定义的空布局
     * @param srlRefresh
     * @param data
     * @param layoutRes
     */
    public void setNewData(@Nullable SwipeRefreshLayout srlRefresh, @Nullable List<T> data, int layoutRes) {
        if (srlRefresh != null) {
            srlRefresh.setRefreshing(false);
        }
        setEnableLoadMore(true);
        if (AppUtils.isEmpty(data)){
            // TODO: 2019/8/23 设置空布局
            if (!AppUtils.isEmpty(getData())){
                setNewData(null);
            }
            if (layoutRes!=0){
                setEmptyView(getEmptyView(layoutRes));
            }
            return;
        }
        setNewData(data);
        loadMoreComplete();
    }

    /**
     * 使用自定义的空布局
     * @param srlRefresh
     * @param data
     * @param emptyView
     */
    public void setNewData(@Nullable SwipeRefreshLayout srlRefresh, @Nullable List<T> data, View emptyView) {
        if (srlRefresh != null) {
            srlRefresh.setRefreshing(false);
        }
        setEnableLoadMore(true);
        if (AppUtils.isEmpty(data)){
            // TODO: 2019/8/23 设置空布局
            if (!AppUtils.isEmpty(getData())){
                setNewData(null);
            }
            if (emptyView!=null){
                setEmptyView(emptyView);
            }
            return;
        }
        setNewData(data);
        loadMoreComplete();
    }

    @Override
    public void addData(@NonNull Collection<? extends T> newData) {
        if (AppUtils.isEmpty(newData)){
            loadMoreEnd();
        }else {
            loadMoreComplete();
            super.addData(newData);
        }
    }

    @Override
    public void loadMoreFail() {
        if (AppUtils.isEmpty(getData())){
            loadMoreFail(R.layout.layout_net_error);
        }else {
            super.loadMoreFail();
        }
    }

    public void loadMoreFail(int layoutRes) {
        if (AppUtils.isEmpty(getData())){
            setEmptyView(getEmptyView(layoutRes));
        }else {
            super.loadMoreFail();
        }
    }

    private View getEmptyView(int layoutRes){

        return  LayoutInflater.from(getRecyclerView().getContext()).inflate(layoutRes, (ViewGroup) getRecyclerView().getParent(),false);
    }


}
