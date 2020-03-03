package me.soushin.tinmvparms.base.app

import com.chad.library.adapter.base.BaseQuickAdapter
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.ArmsUtils

/**
 * 扩展代码
 *
 * @auther SouShin
 * @time 2019/12/19 16:51
 */


fun BaseQuickAdapter<*, *>.getAppComponent(): AppComponent {
    return ArmsUtils.obtainAppComponentFromContext(AppManager.getAppManager().topActivity)
}

