# TinkerDemo

## Tinker基本介绍
    Tinker是微信官方的Android热补丁解决方案，她支持动态下发代码、So库以及资源，让应用能够在不需要重新安装的情况下实现更新。当前，你也可以使用Tinker来更新你的插件
    主要包括以下几个部分：
        1.gradle编译插件：tinker-patch-gradle-plugin(这个插件主要是在android studio中直接完成patch文件的生成)
        2.核心sdk库：tinker-android-lib（存放的为应用程序提供的所有api）
        3.非gradle编译用户的命令行版本：tinker-patch-cli.jar
        
    为什么使用Tinker
        当前市面的热补丁方案有很多，其中比较出名的有阿里的AndFix、美团的Robust以及QZone的超级补丁方案。但它们都存在无法解决的问题，这也是正是我们推出Tinker的原因。
        
        Tinker	QZone	AndFix	Robust
        类替换	yes	yes	no	no
        So替换	yes	no	no	no
        资源替换	yes	yes	no	no
        全平台支持	yes	yes	yes	yes
        即时生效	no	no	yes	yes
        性能损耗	较小	较大	较小	较小
        补丁包大小	较小	较大	一般	一般
        开发透明	yes	yes	no	no
        复杂度	较低	较低	复杂	复杂
        gradle支持	yes	no	no	no
        Rom体积	较大	较小	较小	较小
        成功率	较高	较高	一般	最高
        
        总的来说:
            AndFix作为native解决方案，首先面临的是稳定性与兼容性问题，更重要的是它无法实现类替换，它是需要大量额外的开发成本的；
            Robust兼容性与成功率较高，但是它与AndFix一样，无法新增变量与类只能用做的bugFix方案；
            Qzone方案可以做到发布产品功能，但是它主要问题是插桩带来Dalvik的性能问题，以及为了解决Art下内存地址问题而导致补丁包急速增大的。
            特别是在Android N之后，由于混合编译的inline策略修改，对于市面上的各种方案都不太容易解决。而Tinker热补丁方案不仅支持类、So以及资源的替换，
            它还是2.X－8.X(1.9.0以上支持8.X)的全平台支持。利用Tinker我们不仅可以用做bugfix,甚至可以替代功能的发布。Tinker已运行在微信的数亿Android设备上，那么为什么你不使用Tinker呢？
            
        Tinker的已知问题：
            由于原理与系统限制，Tinker有以下已知问题，
            Tinker不支持修改AndroidManifest.xml，Tinker不支持新增四大组件(1.9.0支持新增非export的Activity)；
            由于Google Play的开发者条款限制，不建议在GP渠道动态更新代码；
            在Android N上，补丁对应用启动时间有轻微的影响；
            不支持部分三星android-21机型，加载补丁时会主动抛出"TinkerRuntimeException:checkDexInstall failed"；
            对于资源替换，不支持修改remoteView。例如transition动画，notification icon以及桌面图标。
## Tinker执行原理及流程
    基于android原生的classloader开发了自己的classloader
    基于android原生的aapt，开发了自己的aapt，tinker通过定义自己的AssetManager完成对patch文件中的资源文件的加载
    微信团队自己基于Dex文件的格式，研发了DexDiff算法
## 使用Tinker完成线上bug修复
    1.集成阶段
        gradle中添加Tinker依赖
        在代码中完成对Tinker的初始化
    2.准备阶段
        build一个old apk并安装到手机
        修改一些功能后，build一个new apk
    3.patch生成方式
        使用命令行的方式完成patch包的生成
        使用gradle插件的方式完成patch包的生成
        
## Tinker源码讲解
