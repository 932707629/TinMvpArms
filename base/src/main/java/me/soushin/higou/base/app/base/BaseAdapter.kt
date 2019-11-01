package me.soushin.higou.base.app.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity

import me.soushin.higou.base.R
import me.soushin.higou.base.app.utils.AppUtils


/**
 * 基于SmartRefresh和BaseQuickAdapter封装的BaseAdapter
 * 设置adapter请用bindToRecyclerView();这种方式
 *
 * @author SouShin
 * @time 2018/11/13 11:34
 */
class BaseAdapter<T : MultiItemEntity, K : BaseViewHolder> : BaseMultiItemQuickAdapter<T, K> {

    companion object {
        val item_type_one = 1
        val item_type_two = 2
        val item_type_three = 3
    }

    constructor() : super(null)

    constructor(data: List<T>) : super(data)

    override fun convert(helper: K, item: T) {

    }

    /**
     * 加载数据
     */
    fun showLoading() {
        setNewData(null)
        emptyView = getEmptyView(R.layout.layout_loading)
    }

    /**
     * 使用默认的空布局
     * @param srlRefresh
     * @param data
     */
    fun setNewData(srlRefresh: SwipeRefreshLayout?, data: List<T>?) {
        val layoutRes = if (AppUtils.isEmpty(data)) R.layout.layout_no_data else 0
        setNewData(srlRefresh, data, layoutRes)
    }

    /**
     * 使用自定义的空布局
     * @param srlRefresh
     * @param data
     * @param layoutRes
     */
    fun setNewData(srlRefresh: SwipeRefreshLayout?, data: List<T>?, layoutRes: Int) {
        if (srlRefresh != null) {
            srlRefresh.isRefreshing = false
        }
        setEnableLoadMore(true)
        if (AppUtils.isEmpty(data)) {
            // TODO: 2019/8/23 设置空布局
            if (!AppUtils.isEmpty(getData())) {
                setNewData(null)
            }
            if (layoutRes != 0) {
                emptyView = getEmptyView(layoutRes)
            }
            return
        }
        setNewData(data)
        loadMoreComplete()
    }

    /**
     * 使用自定义的空布局
     * @param srlRefresh
     * @param data
     * @param emptyView
     */
    fun setNewData(srlRefresh: SwipeRefreshLayout?, data: List<T>?, emptyView: View?) {
        if (srlRefresh != null) {
            srlRefresh.isRefreshing = false
        }
        setEnableLoadMore(true)
        if (AppUtils.isEmpty(data)) {
            // TODO: 2019/8/23 设置空布局
            if (!AppUtils.isEmpty(getData())) {
                setNewData(null)
            }
            if (emptyView != null) {
                setEmptyView(emptyView)
            }
            return
        }
        setNewData(data)
        loadMoreComplete()
    }

    override fun addData(newData: Collection<T>) {
        if (AppUtils.isEmpty(newData)) {
            loadMoreEnd()
        } else {
            super.addData(newData)
            loadMoreComplete()
        }
    }

    override fun loadMoreFail() {
        if (AppUtils.isEmpty(data)) {
            loadMoreFail(R.layout.layout_net_error)
        } else {
            super.loadMoreFail()
        }
    }

    fun loadMoreFail(layoutRes: Int) {
        if (AppUtils.isEmpty(data)) {
            setEmptyView(getEmptyView(layoutRes))
        } else {
            super.loadMoreFail()
        }
    }

    private fun getEmptyView(layoutRes: Int): View {
        return LayoutInflater.from(recyclerView.context).inflate(layoutRes, recyclerView.parent as ViewGroup, false)
    }


}
