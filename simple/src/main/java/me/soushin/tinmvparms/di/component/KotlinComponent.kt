package me.soushin.tinmvparms.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import me.soushin.tinmvparms.di.module.KotlinModule

import com.jess.arms.di.scope.ActivityScope
import me.soushin.tinmvparms.mvp.ui.activity.KotlinActivity


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
@ActivityScope
@Component(modules = arrayOf(KotlinModule::class), dependencies = arrayOf(AppComponent::class))
interface KotlinComponent {
    fun inject(activity: KotlinActivity)



}
