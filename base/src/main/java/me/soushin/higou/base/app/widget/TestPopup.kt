package me.soushin.higou.base.app.widget

import android.app.Activity
import android.content.Context
import android.widget.ProgressBar
import com.blankj.ALog
import me.soushin.higou.base.R
import me.soushin.higou.base.app.base.BasePopup

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