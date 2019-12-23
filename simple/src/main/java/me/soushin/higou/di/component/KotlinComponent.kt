package me.soushin.higou.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import me.soushin.higou.di.module.KotlinModule

import com.jess.arms.di.scope.ActivityScope
import me.soushin.higou.mvp.ui.activity.KotlinActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 12/19/2019 17:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = arrayOf(KotlinModule::class), dependencies = arrayOf(AppComponent::class))
interface KotlinComponent {
    fun inject(activity: KotlinActivity)
}
