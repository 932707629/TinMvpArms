package me.soushin.higou.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import me.soushin.higou.mvp.contract.KotlinContract
import me.soushin.higou.mvp.model.KotlinModel


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
@Module
//构建KotlinModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class KotlinModule(private val view: KotlinContract.View) {
    @ActivityScope
    @Provides
    fun provideKotlinView(): KotlinContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideKotlinModel(model: KotlinModel): KotlinContract.Model {
        return model
    }
}
