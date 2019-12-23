package me.soushin.higou.base.app.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gyf.immersionbar.components.ImmersionOwner;
import com.gyf.immersionbar.components.ImmersionProxy;
import com.hjq.toast.ToastUtils;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.FragmentLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;

import javax.inject.Inject;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public abstract class BaseFragment<P extends IPresenter> extends com.jess.arms.base.BaseFragment
        implements ImmersionOwner {

    /**
     * ImmersionBar代理类
     */
    private ImmersionProxy mImmersionProxy = new ImmersionProxy(this);

    @Override
    public void onDestroy() {
        super.onDestroy();
        mImmersionProxy.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        this.mPresenter = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionProxy.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionProxy.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mImmersionProxy.onPause();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mImmersionProxy.onConfigurationChanged(newConfig);
    }

    /**
     * 懒加载，在view初始化完成之前执行
     * On lazy after view.
     */
    @Override
    public void onLazyBeforeView() {
    }

    /**
     * 懒加载，在view初始化完成之后执行
     * On lazy before view.
     */
    @Override
    public void onLazyAfterView() {
    }

    /**
     * Fragment用户可见时候调用
     * On visible.
     */
    @Override
    public void onVisible() {
    }

    /**
     * Fragment用户不可见时候调用
     * On invisible.
     */
    @Override
    public void onInvisible() {
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean immersionBarEnabled() {
        return false;
    }

    @Override
    public void initImmersionBar() {

    }

    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ToastUtils.show(message);
    }

    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        startActivity(intent);
    }

    public void goActivity(Class clazz){
        startActivity(new Intent(getThis(),clazz));
    }

    public void killMyself() {

    }

    public Context getThis() {
        return mContext;
    }

}
