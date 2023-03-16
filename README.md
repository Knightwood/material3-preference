# Material 3  Preference 

This library implements Material 3 Preference theme by extending AndroidX preference theme.

### Download Tag: [![](https://jitpack.io/v/Knightwood/material3-preference.svg)](https://jitpack.io/#Knightwood/material3-preference)

* 1.2 first release
* 1.3 无新功能及改进，仅将minsdk从23降至21
* 1.4 添加编辑偏好设置的委托工具

```groovy
dependencies {
    implementation "com.github.knightwood:material3-preference:Tag"
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

### preference委托工具类

* [来自github](https://github.com/fengzhizi715/SAF-Object-Delegate)

* 支持 SharedPreferences 的int、long、float、boolean、string以及Set\<String\>
* 支持对上述类型使用 AES 算法进行加密，以保障数据安全


没有使用 AES 算法，SharedPreferences的数据文件大致是这样的：

```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="name">tony</string>
    <string name="password">1234abcd</string>
    <int name="age" value="20" />
    <boolean name="isForeigner" value="false" />
</map>
```


使用了 AES 算法之后，可能会变成这样：

```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="PUoKC8qjz+tmMUV+EVz9Pw==">x2jMB8gvbhkLpMsUuvN7VA==</string>
    <string name="/dSJ/TeQoeRnpoMvZWI67A==">lV+O+RVVrE/p4CjmBX3IbA==</string>
    <string name="viAoG1taVD8rPP9+0n4ORg==">O4DxiobDhUeGEPe0cQFrCQ==</string>
    <string name="Mq2QcYiQhvDjHwBohnGWEQ==">er4sZVGF7k45nNmUq6p7Cg==</string>
</map>
```

###  使用

可以编写一个 PrefsHelper，把所需要使用 SharedPreferences 保存的属性放进去。

```kotlin
class PrefsHelper(prefs: SharedPreferences) {

    var name by prefs.string("name")

    var password by prefs.string("password")

    var age by prefs.int("age")

    var isForeigner by prefs.boolean("isForeigner")
}
```

要使用加密功能的话，需要先初始化密钥，且密钥是16位。

```kotlin
class EncryptPrefsHelper(prefs: SharedPreferences) {

    init {

        prefs.initKey("12345678910abcde") // 初始化密钥，且密钥是16位的
    }

    var name by prefs.string("name",isEncrypt=true)

    var password by prefs.string("password",isEncrypt=true)

    var age by prefs.int("age",isEncrypt=true)

    var isForeigner by prefs.boolean("isForeigner",isEncrypt=true)
}
```

注意，实际使用过程中 PrefsHelper 应该是单例。

