package com.kiylx.m3preference.core

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.preference.EditTextPreferenceDialogFragmentCompat
import androidx.preference.PreferenceDialogFragmentCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.Exception

class M3EditTextPreferenceDialogFragmentCompat : EditTextPreferenceDialogFragmentCompat() {
    @SuppressLint("RestrictedApi")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return try {
            val mWhichButtonClicked =
                ReflectHelper.getField(PreferenceDialogFragmentCompat::class.java,
                    "mWhichButtonClicked")
                    .setInt(this, DialogInterface.BUTTON_NEGATIVE)
            //dialog的标题
            val mDialogTitleField =
                ReflectHelper.getField(PreferenceDialogFragmentCompat::class.java, "mDialogTitle")
            val mDialogTitle = mDialogTitleField[this] as CharSequence?
            //dialog的图标
            val mDialogIconField =
                ReflectHelper.getField(PreferenceDialogFragmentCompat::class.java, "mDialogIcon")
            val mDialogIcon = mDialogIconField[this] as BitmapDrawable?
            //确定按钮
            val mPositiveButtonTextField =
                ReflectHelper.getField(PreferenceDialogFragmentCompat::class.java,
                    "mPositiveButtonText")
            val mPositiveButtonText = mPositiveButtonTextField[this] as CharSequence?
            //取消按钮
            val mNegativeButtonTextField =
                ReflectHelper.getField(PreferenceDialogFragmentCompat::class.java,
                    "mNegativeButtonText")
            val mNegativeButtonText = mNegativeButtonTextField[this] as CharSequence?
            //dialog builder
            val builder = MaterialAlertDialogBuilder(requireContext())
                .setTitle(mDialogTitle)
                .setIcon(mDialogIcon)
                .setPositiveButton(mPositiveButtonText, this)
                .setNegativeButton(mNegativeButtonText, this)
            val contentView = onCreateDialogView(requireContext())
            if (contentView != null) {
                onBindDialogView(contentView)
                builder.setView(contentView)
            } else {
                //消息
                val mDialogMessageField =
                    ReflectHelper.getField(PreferenceDialogFragmentCompat::class.java,
                        "mDialogMessage")
                val mDialogMessage = mDialogMessageField[this] as CharSequence?

                builder.setMessage(mDialogMessage)
            }
            onPrepareDialogBuilder(builder)

            val requestInputMethod =
                PreferenceDialogFragmentCompat::class.java.getDeclaredMethod("requestInputMethod",
                    Dialog::class.java)
            requestInputMethod.isAccessible = true

            // Create the dialog
            val dialog: Dialog = builder.create()
            if (needInputMethod()) {
                requestInputMethod.invoke(this, dialog)
            }
            dialog
        } catch (e: Exception) {
            e.printStackTrace()
            Dialog(requireContext(), theme)
        }
    }

    companion object {
        fun newInstance(key: String?): M3EditTextPreferenceDialogFragmentCompat {
            val fragment = M3EditTextPreferenceDialogFragmentCompat()
            val b = Bundle(1)
            b.putString(ARG_KEY, key)
            fragment.arguments = b
            return fragment
        }
    }
}