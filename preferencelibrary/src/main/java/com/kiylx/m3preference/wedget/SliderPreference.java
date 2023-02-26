package com.kiylx.m3preference.wedget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.google.android.material.slider.Slider;
import com.kiylx.preferencelibrary.R;

public class SliderPreference extends Preference {
    public static final String TAG = "SliderPreference";
    float mMin;
    float mMax = 0F;
    float mSliderBarValue = mMin;
    float mStepValue;

    // Whether the Slider should respond to the left/right keys
    @SuppressWarnings("WeakerAccess") /* synthetic access */
            boolean mAdjustable;
    @SuppressWarnings("WeakerAccess") /* synthetic access */
            boolean mTrackingTouch;
    // Whether to show the Slider value TextView next to the bar
    private boolean mShowSliderValue;
    private Slider slider;
    private boolean mUpdatesInstantly;
    private TextView mSliderValueTextView;

    private final Slider.OnSliderTouchListener sliderTouchListener = new Slider.OnSliderTouchListener() {
        @Override
        public void onStartTrackingTouch(@NonNull Slider slider) {
            // Responds to when slider's touch event is being started
            mTrackingTouch = true;
        }

        @Override
        public void onStopTrackingTouch(@NonNull Slider slider) {
            // Responds to when slider's touch event is being stopped
            mTrackingTouch = false;
            if (slider.getValue() + mMin != mSliderBarValue) {
                syncValueInternal(slider);
            }
        }
    };
    private final Slider.OnChangeListener onChangeListener = new Slider.OnChangeListener() {
        @Override
        public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
            // Responds to when slider's value is changed
            if (fromUser && (mUpdatesInstantly || !mTrackingTouch)) {
                syncValueInternal(slider);
            } else {
                // We always want to update the text while the seekbar is being dragged
                updateLabelValue(value);
            }
        }
    };

    /**
     * Listener reacting to the user pressing DPAD left/right keys if {@code
     * adjustable} attribute is set to true; it transfers the key presses to the {@link SeekBar}
     * to be handled accordingly.
     */
    private final View.OnKeyListener mSeekBarKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() != KeyEvent.ACTION_DOWN) {
                return false;
            }

            if (!mAdjustable && (keyCode == KeyEvent.KEYCODE_DPAD_LEFT
                    || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)) {
                // Right or left keys are pressed when in non-adjustable mode; Skip the keys.
                return false;
            }

            // We don't want to propagate the click keys down to the SeekBar view since it will
            // create the ripple effect for the thumb.
            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                return false;
            }

            if (slider == null) {
                Log.e(TAG, "SeekBar view is null and hence cannot be adjusted.");
                return false;
            }
            return slider.onKeyDown(keyCode, event);
        }
    };

    public SliderPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.SliderPreference, defStyleAttr, defStyleRes);
        mMin = a.getFloat(R.styleable.SliderPreference_minValue, 1.0F);
        setMax(a.getFloat(R.styleable.SliderPreference_maxValue, 10F));
        mAdjustable = a.getBoolean(R.styleable.SliderPreference_adjustable, true);
        mShowSliderValue = a.getBoolean(R.styleable.SliderPreference_showSliderValue, false);
        mUpdatesInstantly = a.getBoolean(R.styleable.SliderPreference_updatesInstantly,
                false);
        mStepValue = a.getFloat(R.styleable.SliderPreference_stepValue, 0F);
        a.recycle();
    }

    public SliderPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SliderPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.sliderPreferenceStyle);
    }

    public SliderPreference(@NonNull Context context) {
        this(context, null);
    }


    @Override
    public void onBindViewHolder(@NonNull PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);
        holder.itemView.setOnKeyListener(mSeekBarKeyListener);
        slider = (Slider) holder.findViewById(R.id.slider);
        mSliderValueTextView = (TextView) holder.findViewById(R.id.slider_value);
        if (mShowSliderValue) {
            mSliderValueTextView.setVisibility(View.VISIBLE);
        } else {
            mSliderValueTextView.setVisibility(View.GONE);
            mSliderValueTextView = null;
        }
        if (slider == null) {
            Log.e(TAG, "Slider view is null in onBindViewHolder.");
            return;
        }
        slider.addOnSliderTouchListener(sliderTouchListener);
        slider.addOnChangeListener(onChangeListener);

        updateLabelValue(mSliderBarValue);
        slider.setEnabled(isEnabled());

        slider.setValueFrom(mMin);
        slider.setValueTo(mMax);

        float currentValue = mSliderBarValue;
        if (mSliderBarValue < mMin)
            currentValue = mMin;
        if (mSliderBarValue > mMax)
            currentValue = mMax;
        slider.setValue(currentValue);

        if (mStepValue != 0F) {
            slider.setStepSize(mStepValue);
        }
    }

    //default value
    @Override
    protected void onSetInitialValue(Object defaultValue) {
        if (defaultValue == null) {
            defaultValue = mMin;
        }
        setValue(getPersistedFloat((float) defaultValue));
    }
    //default value
    @Override
    protected @Nullable
    Object onGetDefaultValue(@NonNull TypedArray a, int index) {
        return a.getFloat(index, 0F);
    }

    /**
     * Gets the lower bound set on the {@link Slider}.
     *
     * @return The lower bound set
     */
    public float getMin() {
        return mMin;
    }

    /**
     * Sets the lower bound on the {@link Slider}.
     *
     * @param min The lower bound to set
     */
    public void setMin(float min) {
        if (min > mMax) {
            min = mMax;
        }
        if (min != mMin) {
            mMin = min;
            notifyChanged();
        }
    }


    /**
     * Gets the upper bound set on the {@link Slider}.
     *
     * @return The upper bound set
     */
    public float getMax() {
        return mMax;
    }

    /**
     * Sets the upper bound on the {@link Slider}.
     *
     * @param max The upper bound to set
     */
    public final void setMax(float max) {
        if (max < mMin) {
            max = mMin;
        }
        if (max != mMax) {
            mMax = max;
            notifyChanged();
        }
    }

    /**
     * Gets whether the {@link Slider} should respond to the left/right keys.
     *
     * @return Whether the {@link Slider} should respond to the left/right keys
     */
    public boolean isAdjustable() {
        return mAdjustable;
    }

    /**
     * Sets whether the {@link Slider} should respond to the left/right keys.
     *
     * @param adjustable Whether the {@link Slider} should respond to the left/right keys
     */
    public void setAdjustable(boolean adjustable) {
        mAdjustable = adjustable;
    }

    /**
     * Gets whether the {@link SliderPreference} should continuously save the {@link Slider} value
     * while it is being dragged. Note that when the value is true,
     * {@link Preference.OnPreferenceChangeListener} will be called continuously as well.
     *
     * @return Whether the {@link SliderPreference} should continuously save the {@link Slider}
     * value while it is being dragged
     * @see #setUpdatesInstantly(boolean)
     */
    public boolean getUpdatesInstantly() {
        return mUpdatesInstantly;
    }

    /**
     * Sets whether the {@link SliderPreference} should continuously save the {@link Slider} value
     * while it is being dragged.
     *
     * @param updatesInstantly Whether the {@link SliderPreference} should continuously save
     *                         the {@link Slider} value while it is being dragged
     * @see #getUpdatesInstantly()
     */
    public void setUpdatesInstantly(boolean updatesInstantly) {
        mUpdatesInstantly = updatesInstantly;
    }

    /**
     * Gets whether the current {@link Slider} value is displayed to the user.
     *
     * @return Whether the current {@link Slider} value is displayed to the user
     * @see #setShowSliderValue(boolean)
     */
    public boolean getShowSliderValue() {
        return mShowSliderValue;
    }

    /**
     * Sets whether the current {@link Slider} value is displayed to the user.
     *
     * @param showSliderValue Whether the current {@link Slider} value is displayed to the user
     * @see #getShowSliderValue()
     */
    public void setShowSliderValue(boolean showSliderValue) {
        mShowSliderValue = showSliderValue;
        notifyChanged();
    }

    private void setValueInternal(float sliderValue, boolean notifyChanged) {
        if (sliderValue < mMin) {
            sliderValue = mMin;
        }
        if (sliderValue > mMax) {
            sliderValue = mMax;
        }

        if (sliderValue != mSliderBarValue) {
            mSliderBarValue = sliderValue;
            updateLabelValue(mSliderBarValue);
            persistFloat(sliderValue);//保存进sp
            if (notifyChanged) {
                notifyChanged();
            }
        }
    }

    /**
     * Gets the current progress of the {@link Slider}.
     *
     * @return The current progress of the {@link Slider}
     */
    public float getValue() {
        return mSliderBarValue;
    }

    /**
     * Sets the current progress of the {@link Slider}.
     *
     * @param sliderValue The current progress of the {@link Slider}
     */
    public void setValue(float sliderValue) {
        setValueInternal(sliderValue, true);
    }

    /**
     * Persist the {@link Slider}'s Slider value if callChangeListener returns true, otherwise
     * set the {@link Slider}'s value to the stored value.
     */
    @SuppressWarnings("WeakerAccess") /* synthetic access */
    void syncValueInternal(@NonNull Slider slider) {
        float sliderValue = slider.getValue();
        if (sliderValue != mSliderBarValue) {//slider的值和当前值不一致时，存储新值
            if (callChangeListener(sliderValue)) {
                setValueInternal(sliderValue, false);
            } else {
                slider.setValue(mSliderBarValue);
                updateLabelValue(mSliderBarValue);
            }
        }
    }

    /**
     * Attempts to update the TextView label that displays the current value.
     *
     * @param value the value to display next to the {@link Slider}
     */
    @SuppressWarnings("WeakerAccess") /* synthetic access */
    void updateLabelValue(float value) {
        if (mSliderValueTextView != null) {
            mSliderValueTextView.setText(String.valueOf(value));
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable superState = super.onSaveInstanceState();
        if (isPersistent()) {
            // No need to save instance state since it's persistent
            return superState;
        }

        // Save the instance state
        final SliderPreference.SavedState myState = new SliderPreference.SavedState(superState);
        myState.mSliderBarValue = mSliderBarValue;
        myState.mMin = mMin;
        myState.mMax = mMax;
        return myState;
    }

    @Override
    protected void onRestoreInstanceState(@Nullable Parcelable state) {
        if (state == null || !state.getClass().equals(SliderPreference.SavedState.class)) {
            // Didn't save state for us in onSaveInstanceState
            super.onRestoreInstanceState(state);
            return;
        }

        // Restore the instance state
        SliderPreference.SavedState myState = (SliderPreference.SavedState) state;
        super.onRestoreInstanceState(myState.getSuperState());
        mSliderBarValue = myState.mSliderBarValue;
        mMin = myState.mMin;
        mMax = myState.mMax;
        notifyChanged();
    }

    /**
     * SavedState, a subclass of {@link BaseSavedState}, will store the state of this preference.
     *
     * <p>It is important to always call through to super methods.
     */
    private static class SavedState extends BaseSavedState {
        public static final Parcelable.Creator<SliderPreference.SavedState> CREATOR =
                new Parcelable.Creator<SliderPreference.SavedState>() {
                    @Override
                    public SliderPreference.SavedState createFromParcel(Parcel in) {
                        return new SliderPreference.SavedState(in);
                    }

                    @Override
                    public SliderPreference.SavedState[] newArray(int size) {
                        return new SliderPreference.SavedState[size];
                    }
                };

        float mSliderBarValue;
        float mMin;
        float mMax;

        SavedState(Parcel source) {
            super(source);

            // Restore the click counter
            mSliderBarValue = source.readFloat();
            mMin = source.readFloat();
            mMax = source.readFloat();
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);

            // Save the click counter
            dest.writeFloat(mSliderBarValue);
            dest.writeFloat(mMin);
            dest.writeFloat(mMax);
        }
    }
}
