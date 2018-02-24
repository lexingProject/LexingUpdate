package com.lexing360.app.lexingupdate;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by fenglingfeng on 2018/2/9.
 */

public abstract class UpDataSubscriber<T> extends ResourceSubscriber<T> {

    @Override
    public void onNext(T response) {
        onSucess(response);
    }

    public abstract void onSucess(T response);

    @Override
    public void onError(final Throwable t) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getInstance(), "put" + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onComplete() {

    }
}
