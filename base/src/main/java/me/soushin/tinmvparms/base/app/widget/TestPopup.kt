package me.soushin.tinmvparms.base.app.widget

import android.content.Context
import me.soushin.tinmvparms.base.R
import me.soushin.tinmvparms.base.app.base.BasePopup

/**
 *
 * @auther SouShin
 * @time 2019/10/28 16:20
 */
class TestPopup(context: Context) : BasePopup(context) {

    override fun getLayoutId(): Int {
        return R.layout.layout_loading
    }

    init {
        setOutSideDismiss(false)
        contentView.setOnClickListener {
            getContext().finish()
        }
    }




}