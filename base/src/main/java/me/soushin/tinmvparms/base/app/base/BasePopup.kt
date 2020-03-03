package me.soushin.tinmvparms.base.app.base

import android.content.Context
import android.view.View
import me.soushin.tinmvparms.base.app.utils.PopupManager
import razerdp.basepopup.BasePopupWindow

/**
 * 弹窗基础类
 * 这里的context最好是Activity的
 * 如果是其他的在onDestroy处理的时候不会匹配到
 * @auther SouShin
 * @time 2019/10/30 10:14
 */
abstract class BasePopup(context: Context?) : BasePopupWindow(context) {

    override fun onCreateContentView(): View {
        PopupManager.addPopup(this)
        return createPopupById(getLayoutId())
    }

    abstract fun getLayoutId():Int

    fun getTag(): String {
        return context.localClassName
    }

}