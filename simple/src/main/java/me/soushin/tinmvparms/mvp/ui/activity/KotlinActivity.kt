package me.soushin.tinmvparms.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import butterknife.BindView
import com.blankj.ALog

import com.jess.arms.di.component.AppComponent
import kotlinx.android.synthetic.main.activity_kotlin.*

import me.soushin.tinmvparms.di.component.DaggerKotlinComponent
import me.soushin.tinmvparms.di.module.KotlinModule
import me.soushin.tinmvparms.mvp.contract.KotlinContract
import me.soushin.tinmvparms.mvp.presenter.KotlinPresenter

import me.soushin.tinmvparms.R
import me.soushin.tinmvparms.base.app.base.BaseActivity

/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/26/2019 10:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
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
//        mPresenter?.requestUser()
        //kotlin中使用butterknife不用注解  直接用id才做即可
        ALog.e("判断BUTTON是否成功引入",btn_download!=null)
    }

    fun onClicker(view: View) {
        mPresenter?.download()
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
