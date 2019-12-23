package me.soushin.higou.base.app.utils;

import com.jess.arms.mvp.IView;
import com.jess.arms.utils.RxLifecycleUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ================================================
 * 放置便于使用 RxJava 的一些工具方法
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 17:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class RxUtils {

    private RxUtils() {
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(final IView view) {
        return observable -> observable.subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    view.showLoading();//显示进度条
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> { view.hideLoading(); })//隐藏进度条
                .compose(RxLifecycleUtils.bindToLifecycle(view));
    }




}
