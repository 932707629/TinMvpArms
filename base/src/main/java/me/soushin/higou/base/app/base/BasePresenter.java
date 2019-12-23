package me.soushin.higou.base.app.base;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CancellationException;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.ChildHandle;
import kotlinx.coroutines.ChildJob;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.selects.SelectClause0;

/**
 * @auther SouShin
 * @time 2019/12/19 18:47
 */
public class BasePresenter<M extends IModel, V extends IView> extends com.jess.arms.mvp.BasePresenter
        implements CoroutineScope {

    private Job job;


    @NotNull
    @Override
    public CoroutineContext getCoroutineContext() {
        return Dispatchers.getMain().plus(job);
    }

    @Override
    public void onStart() {
        super.onStart();

    }




}
