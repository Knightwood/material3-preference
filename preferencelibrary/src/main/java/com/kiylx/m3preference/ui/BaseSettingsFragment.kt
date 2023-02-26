package com.kiylx.m3preference.ui

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.preference.*
import androidx.recyclerview.widget.RecyclerView
import com.kiylx.m3preference.core.M3EditTextPreferenceDialogFragmentCompat
import com.kiylx.m3preference.core.M3ListPreferenceDialogFragmentCompat
import com.kiylx.m3preference.core.M3MultiSelectListPreferenceDialogFragmentCompat

abstract class BaseSettingsFragment : PreferenceFragmentCompat() {
    //是否显示图标
    var iconHide = true

    private fun setAllPreferencesToAvoidHavingExtraSpace(preference: Preference) {
        preference.isIconSpaceReserved = false
        if (preference is PreferenceGroup) for (i in 0 until preference.preferenceCount) {
            setAllPreferencesToAvoidHavingExtraSpace(preference.getPreference(i))
        }
    }

    override fun setPreferenceScreen(preferenceScreen: PreferenceScreen?) {
        if (iconHide) {
            preferenceScreen?.let {
                setAllPreferencesToAvoidHavingExtraSpace(it)
            }
        }
        super.setPreferenceScreen(preferenceScreen)
    }

    @SuppressLint("RestrictedApi")
    override fun onCreateAdapter(preferenceScreen: PreferenceScreen): RecyclerView.Adapter<*> {
        return object : PreferenceGroupAdapter(preferenceScreen) {
            override fun onPreferenceHierarchyChange(preference: Preference) {
                if (iconHide)
                    setAllPreferencesToAvoidHavingExtraSpace(preference)
                super.onPreferenceHierarchyChange(preference)
            }
        }
    }


    @SuppressLint("RestrictedApi")
    override fun onDisplayPreferenceDialog(preference: Preference) {
        val tag = "androidx.preference.PreferenceFragment.DIALOG"
        var handled = false
        if (callbackFragment is OnPreferenceDisplayDialogCallback) {
            handled = (callbackFragment as OnPreferenceDisplayDialogCallback)
                .onPreferenceDisplayDialog(this, preference)
        }
        //  If the callback fragment doesn't handle OnPreferenceDisplayDialogCallback, looks up
        //  its parent fragment in the hierarchy that implements the callback until the first
        //  one that returns true
        var callbackFragment: Fragment? = this
        while (!handled && callbackFragment != null) {
            if (callbackFragment is OnPreferenceDisplayDialogCallback) {
                handled = (callbackFragment as OnPreferenceDisplayDialogCallback)
                    .onPreferenceDisplayDialog(this, preference)
            }
            callbackFragment = callbackFragment.parentFragment
        }
        if (!handled && context is OnPreferenceDisplayDialogCallback) {
            handled = (context as OnPreferenceDisplayDialogCallback)
                .onPreferenceDisplayDialog(this, preference)
        }
        // Check the Activity as well in case getContext was overridden to return something other
        // than the Activity.
        if (!handled && activity is OnPreferenceDisplayDialogCallback) {
            handled = (activity as OnPreferenceDisplayDialogCallback)
                .onPreferenceDisplayDialog(this, preference)
        }

        if (handled) {
            return
        }

        // check if dialog is already showing
        if (parentFragmentManager.findFragmentByTag(tag) != null) {
            return
        }

        val f: PreferenceDialogFragmentCompat = when (preference) {
            is EditTextPreference -> {
                M3EditTextPreferenceDialogFragmentCompat.newInstance(preference.getKey())
            }
            is ListPreference -> {
                M3ListPreferenceDialogFragmentCompat.newInstance(preference.getKey())
            }
            is MultiSelectListPreference -> {
                M3MultiSelectListPreferenceDialogFragmentCompat.newInstance(preference.getKey())
            }
            else -> {
                throw IllegalArgumentException(
                    "Cannot display dialog for an unknown Preference type: "
                            + preference.javaClass.simpleName
                            + ". Make sure to implement onPreferenceDisplayDialog() to handle "
                            + "displaying a custom dialog for this Preference.")
            }
        }

        f.setTargetFragment(this, 0)
        f.show(parentFragmentManager, tag)
    }


}