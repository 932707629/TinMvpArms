package me.soushin.tinmvparms.base.app;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import me.soushin.tinmvparms.base.R;
import me.soushin.tinmvparms.base.app.utils.PopupManager;
import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link Application.ActivityLifecycleCallbacks} 的用法
 * <p>
 * Created by MVPArmsTemplate on 10/24/2019 17:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Timber.tag("onActivityCreated").e(activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.tag("onActivityStarted").e(activity.getLocalClassName());
        if (!activity.getIntent().getBooleanExtra("isInitToolbar", false)) {
            //由于加强框架的兼容性,故将 setContentView 放到 onActivityCreated 之后,onActivityStarted 之前执行
            //而 findViewById 必须在 Activity setContentView() 后才有效,所以将以下代码从之前的 onActivityCreated 中移动到 onActivityStarted 中执行
            activity.getIntent().putExtra("isInitToolbar", true);
            switch (activity.getLocalClassName()){
                case "mvp.ui.activity.UserActivity":
                    View view=activity.findViewById(R.id.toolbar);
                    ((TextView)activity.findViewById(R.id.toolbar_title)).setText("海狗子");
                    view.findViewById(R.id.toolbar_back).setOnClickListener(v -> {
                        activity.onBackPressed();
                    });
                    break;
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.tag("onActivityResumed").e(activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.tag("onActivityPaused").e(activity.getLocalClassName());

    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.tag("onActivityStopped").e(activity.getLocalClassName());

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.tag("onSaveInstanceState").e(activity.getLocalClassName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.tag("onActivityDestroyed").e(activity.getLocalClassName());
        //横竖屏切换或配置改变时, Activity 会被重新创建实例, 但 Bundle 中的基础数据会被保存下来,移除该数据是为了保证重新创建的实例可以正常工作
        activity.getIntent().removeExtra("isInitToolbar");
        PopupManager.INSTANCE.dismissAll(activity.getLocalClassName());
    }
}
