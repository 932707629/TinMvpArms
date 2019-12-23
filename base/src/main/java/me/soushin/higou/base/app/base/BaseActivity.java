package me.soushin.higou.base.app.base;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.hjq.toast.ToastUtils;
import com.jess.arms.mvp.IPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

public abstract class BaseActivity<P extends IPresenter> extends com.jess.arms.base.BaseActivity{


    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ToastUtils.show(message);
    }

    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        startActivity(intent);
    }

    public void showLoading(){

    }

    public void hideLoading(){

    }

    public void goActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void killMyself() {
        finish();
    }

    public Context getThis() {
        return this;
    }

}
