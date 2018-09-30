package com.tencent.tinkerdemo.tinker;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * author: Jeremy
 * date: 2018/9/29
 * desc:
 */
@DefaultLifeCycle(application = "com.tencent.tinkerdemo.tinker.SampleApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class CustomTinkerLike extends ApplicationLike {

    public CustomTinkerLike(Application application,
                            int tinkerFlags,
                            boolean tinkerLoadVerifyFlag,
                            long applicationStartElapsedTime,
                            long applicationStartMillisTime,
                            Intent tinkerResultIntent) {

        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //使应用支持分包
        MultiDex.install(base);
        //初始化Tinker
        TinkerManager.installTinker(this);
    }
}
