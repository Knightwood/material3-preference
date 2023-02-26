# Material 3  Preference 

This library implements Material 3 Preference theme by extending AndroidX preference theme.

### Download

```groovy
dependencies {
    implementation "com.github.knightwood:material3-preference:1.2"
}
```

### 使用方法

* 把`material3的preference`的主题添加到你的`app`的主题（需要`material3`的主题）

```
<!-- Extend this style if you have other custom preferences -->
<item name="preferenceTheme">@style/M3PreferenceStyle</item>
```

* 另设置页面的`fragment`继承自`BaseSettingsFragment`

```
 class SettingsFragment : BaseSettingsFragment() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }  
```

### preference.xml 示例

* 添加了一个新的组件 : `SliderPreference`
* switch preference 要使用`SwitchPreferenceCompat`

```
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <PreferenceCategory app:title="@string/messages_header">

        <EditTextPreference
            app:key="signature"
            app:title="@string/signature_title"
            app:useSimpleSummaryProvider="true" />

        <ListPreference
            app:defaultValue="reply"
            app:entries="@array/reply_entries"
            app:entryValues="@array/reply_values"
            app:key="reply"
            app:title="@string/reply_title"
            app:useSimpleSummaryProvider="true" />
        <MultiSelectListPreference
            android:defaultValue="@array/reply_entries"
            android:entries="@array/reply_values"
            android:entryValues="@array/reply_values"
            android:key="multi_select_list_preference_1"
            android:title="Multi select list preference" />

    </PreferenceCategory>

    <PreferenceCategory app:title="@string/sync_header">

        <SwitchPreferenceCompat
            app:key="sync"
            app:title="@string/sync_title" />

        <SwitchPreferenceCompat
            app:dependency="sync"
            app:key="attachment"
            app:summaryOff="@string/attachment_summary_off"
            app:summaryOn="@string/attachment_summary_on"
            app:title="@string/attachment_title" />
            
        <com.kiylx.m3preference.wedget.SliderPreference
            app:key="slider11"
            app:showSliderValue="true"
            app:title="SliderPreference"
            app:stepValue="1.0"
            android:defaultValue="4"
            app:minValue="0.0"
            app:maxValue="5.0" />
    </PreferenceCategory>

</PreferenceScreen>
```

