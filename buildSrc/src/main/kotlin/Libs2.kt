object Retrofit2 {
    const val core = "com.squareup.retrofit2:retrofit:2.9.0"
    const val converterScalars = "com.squareup.retrofit2:converter-scalars:2.6.2"
    const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val logging = "com.squareup.okhttp3:logging-interceptor:4.2.0"

    //打印okhttp的log库
    const val logging2 = "com.github.ihsanbal:LoggingInterceptor:3.1.0"    //使用logging2需要添加 exclude(group = "org.json", module = "json")

    //kotlin的转换器
    const val converterKotlin =
        "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"

}


object Common {

    //权限申请
    const val perms = "com.guolindev.permissionx:permissionx:1.6.1"

    //页面切换
    const val loadsir = "com.kingja.loadsir:loadsir:1.3.8"

    //载图
    //implementation("com.github.bumptech.glide:glide:4.10.0")
    //annotationProcessor("com.github.bumptech.glide:compiler:4.10.0")
    object SmartRefresh {
        //刷新
        const val refreshKernal = "io.github.scwang90:refresh-layout-kernel:2.0.5"
        const val refreshMaterialStyle = "io.github.scwang90:refresh-header-material:2.0.5"
    }
    //高德地图
    //implementation("com.amap.api:map2d:6.0.0")
    //implementation("com.amap.api:location:6.1.0")

    //recyclerview
    //implementation("com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.50")

    //indexRecyclerview
    //implementation("me.yokeyword:indexablerecyclerview:1.3.0")
    //implementation("com.contrarywind:Android-PickerView:4.1.9")

    //刘海屏适配
    //implementation("com.github.KilleTom:BangScreenToolsMaster:v1.0.0")

    //图表库
    //implementation("com.openxu.viewlib:OXViewLib:1.0.2")
    //implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    //日历库
    //implementation("com.necer.ncalendar:ncalendar:5.0.2")
    //环形视图
    //implementation("com.github.jakob-grabner:Circle-Progress-View:1.4")

    //app update
    const val appUpdate="com.github.azhon:AppUpdate:4.0.0"
}

object Tools {
    //崩溃日志上传
    const val logReport = "com.github.wenmingvs:LogReport:1.0.3"

    //内存泄漏分析
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.8.1"

    //log库（不是okhttp的log库）
    const val logger = "com.orhanobut:logger:2.2.0"

    //crash捕获库
    const val crasher = "me.jfenn:crasher:0.0.2"

    //工具库
    const val utilcodex = "com.blankj:utilcodex:1.31.0"

    //取代旧startactivity方式
    //const val activitymessager = "com.wuyr:activitymessenger:1.2.2"
}
