package me.soushin.tinmvparms.base.app.utils

import com.blankj.ALog
import me.soushin.tinmvparms.base.app.base.BasePopup

/**
 * popup管理类
 * @auther SouShin
 * @time 2019/10/30 10:14
 */
object PopupManager {

    var popupList= mutableListOf<BasePopup>()

    /**
     * 添加弹窗
     */
    fun addPopup (basePopup: BasePopup) {
        popupList.add(basePopup)
    }

    /**
     * 关闭单个弹窗
     */
    fun dismiss (basePopup: BasePopup) {
         if (basePopup.isShowing){
             basePopup.dismiss()
             popupList.remove(basePopup)
         }
    }

    /**
     * 关闭所有弹窗
     */
    fun dismissAll(tag:String){
        for (basePop in popupList){
            ALog.e("需要关闭的弹窗",tag,basePop.getTag())
            if (basePop.getTag()==tag){
                dismiss(basePop)
            }
        }
    }


}