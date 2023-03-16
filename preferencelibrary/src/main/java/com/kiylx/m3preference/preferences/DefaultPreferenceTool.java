package com.kiylx.m3preference.preferences;

import android.content.Context;
import android.preference.PreferenceManager;
import java.io.IOException;
import java.util.HashMap;

/**
 * 创建者 kiylx
 * 创建时间 2020/4/29 16:11
 * 对默认的preferense（PreferenceManager.getDefaultSharedPreferences(context)）进行操作
 */
public class DefaultPreferenceTool {

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static String getStrings(Context context, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }

    public static void putHashmap(Context context, String key, HashMap<String, String> map) throws IOException {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, ConvertHashmapAndString.TransformHashMapToStr(map)).apply();
    }

    public static HashMap<String, String> getHashmap(Context context, String key) throws IOException, ClassNotFoundException {
        return ConvertHashmapAndString.TransformStrToHashMap(PreferenceManager.getDefaultSharedPreferences(context).getString(key, null));
    }

}
