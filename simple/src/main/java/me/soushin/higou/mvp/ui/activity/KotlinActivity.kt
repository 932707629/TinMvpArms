package me.soushin.higou.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.di.component.AppComponent

import me.soushin.higou.di.component.DaggerKotlinComponent
import me.soushin.higou.di.module.KotlinModule
import me.soushin.higou.mvp.contract.KotlinContract
import me.soushin.higou.mvp.presenter.KotlinPresenter

import me.soushin.higou.R
import me.soushin.higou.base.app.base.BaseActivity

/**
 * kotlin使用测试
 */
class KotlinActivity : BaseActivity<KotlinPresenter>(), KotlinContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerKotlinComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .kotlinModule(KotlinModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_kotlin //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun launchActivity(intent: Intent) {
        super<BaseActivity>.launchActivity(intent)
    }

    override fun showLoading() {
        super<BaseActivity>.showLoading()
    }

    override fun hideLoading() {
        super<BaseActivity>.hideLoading()
    }

    override fun killMyself() {
        super<BaseActivity>.killMyself()
    }

}
