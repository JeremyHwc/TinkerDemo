package com.tencent.tinkerdemo.tinker;

import android.content.Context;

import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;

/**
 * author: Jeremy
 * date: 2018/9/29
 * desc: 对Tinker所有api进行一层封装
 */
public class TinkerManager {
    private static boolean isInstalled = false;
    private static ApplicationLike mAppLike;

    /**
     * 完成Tinker初始化
     */
    public static void installTinker(ApplicationLike applicationLike) {
        mAppLike = applicationLike;
        if (isInstalled) {
            return;
        }
        TinkerInstaller.install(mAppLike);//完成Tinker的初始化
        isInstalled = true;
    }

    /**
     * 加载补丁文件
     * @param path 补丁文件路径
     */
    public static void loadPatch(String path) {
        if (Tinker.isTinkerInstalled()) {
            TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),path);
        }
    }

    /**
     * 通过 ApplicationLike对象获取context
     * @return
     */
    private static Context getApplicationContext() {
        if (mAppLike != null) {
            return mAppLike.getApplication().getApplicationContext();
        }
        return null;
    }
}
