package pl.allblue.ablibs.forms;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.TimeZone;

import pl.allblue.ablibs.R;
import pl.allblue.ablibs.util.Date;

public class ABDateField extends ABFormField {

    private AppCompatActivity activity;
    private TextInputEditText editText;
    private TextInputLayout layout;

    private Long value;
    private Long defaultValue;
    private boolean utc;

    public ABDateField(AppCompatActivity activity, TextInputEditText editText,
            TextInputLayout layout, Long defaultValue, boolean utc) {
        final ABDateField self = this;

        this.activity = activity;
        this.editText = editText;
        this.layout = layout;

        this.value = null;
        this.defaultValue = defaultValue;
        this.utc = utc;

        editText.setText("");
        editText.setInputType(InputType.TYPE_NULL);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                    self.value = null;
            }
        });
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                return;

            this.showMaterialDatePicker();
        });
        editText.setOnClickListener(v -> {
            this.showMaterialDatePicker();
        });
    }

    public ABDateField(AppCompatActivity activity, TextInputEditText editText,
            TextInputLayout layout, Long defaultValue) {
        this(activity, editText, layout, defaultValue, true);
    }

    public Long getValue() {
        return this.value;
    }

    public void setValue(Long value) {
        if (value != null)
            value = utc ? Date.getDay_UTC(value) : Date.getDay(value);

        if (value == this.value)
            return;

        if (value == null) {
            this.value = null;
            this.editText.setText("");
            this.notifyValueChanged();
            return;
        }

        this.value = value;
        this.editText.setText(utc ? Date.format_Date_UTC(value) :
                Date.format_Date(value));
        this.notifyValueChanged();

        this.layout.setEndIconMode(TextInputLayout.END_ICON_NONE);
        this.layout.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
        this.layout.setEndIconVisible(this.editText.isEnabled() &&
                !this.editText.getText().toString().equals(""));
    }


    private void showMaterialDatePicker() {
        TimeZone userTimeZone = TimeZone.getDefault();

        long parsedValue = (value == null ? defaultValue : value) * 1000l;
        if (!utc) {
            parsedValue += Date.getUTCOffset_Seconds(value == null ?
                    defaultValue : value) * 1000l;
        }

        MaterialDatePicker<Long> mdp = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText(activity.getString(R.string.text_select_date))
            .setSelection(parsedValue)
            .build();

        mdp.addOnPositiveButtonClickListener(selection -> {
            long time = selection / 1000l;
            this.setValue(time + (utc ? 0 : Date.getUTCOffset_Seconds(time)));

//            View view = this.editText.focusSearch(View.FOCUS_DOWN);
//            view.requestFocus();
        });

        mdp.show(this.activity.getSupportFragmentManager(),
                "abDateTimeField");
    }


    @Override
    public void clear() {

    }

    @Override
    public void putValueInJSONObject(JSONObject row, String fieldName)
            throws JSONException {
        row.put(fieldName, this.value == null ? JSONObject.NULL : this.value);
    }

    @Override
    public void setError(String message) {
        this.activity.runOnUiThread(() -> {
            this.layout.setError(message);
        });
    }

    @Override
    public void setValueFromJSONObject(JSONObject row, String fieldName)
            throws JSONException {
        this.setValue(row.isNull(fieldName) ? null :  row.getLong(fieldName));
    }
}
