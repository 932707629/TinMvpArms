package me.soushin.higou.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import io.reactivex.Observable
import io.reactivex.Observer
import me.soushin.higou.base.app.network.kotlinNet.ResultBean
import javax.inject.Inject

import me.soushin.higou.mvp.contract.KotlinContract
import me.soushin.higou.mvp.model.api.service.UserService
import me.soushin.higou.mvp.model.entity.User
import okhttp3.ResponseBody
import retrofit2.Call


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
class KotlinModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), KotlinContract.Model {

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun getUsers(lastIdQueried: Int, update: Boolean): Call<ResultBean<List<User>>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).getUsers(1,1)
    }

    override fun download(url: String): Observable<ResponseBody> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).download(url)
    }

    override fun onDestroy() {
        super.onDestroy();
    }



}
