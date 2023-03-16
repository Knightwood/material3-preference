package com.kiylx.notex

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.kiylx.m3preference.preferences.delegatetools.saf_prefs.boolean
import com.kiylx.m3preference.preferences.delegatetools.saf_prefs.getPreference
import com.kiylx.m3preference.preferences.delegatetools.saf_prefs.initKey
import com.kiylx.m3preference.preferences.delegatetools.saf_prefs.string

/**
 * all preference
 */
class AllPrefs private constructor() {
    val prefs: SharedPreferences = getPreference(
        MyApplication.mContext,
        "aze16hA_34isfsq"
    )
    val defPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.mContext)

    init {
        prefs.initKey("12345678910abcde")
    }

    var deviceCode by defPrefs.string(deviceCodePref, "")//设备id

    /**
     * 默认的相机选择
     * true：前置
     * false：后置
     */
    var cameraSelector by defPrefs.boolean(cameraSelectorPref, true)

    companion object {
        const val deviceCodePref = "device_code"
        const val cameraSelectorPref = "camera_selector"

        private var repoInstance: AllPrefs? = null
            get() {
                if (field == null) {
                    field = AllPrefs()
                }
                return field
            }

        @Synchronized
        fun get(): AllPrefs = repoInstance!!
    }
}
