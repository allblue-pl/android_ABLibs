package pl.allblue.ablibs.forms;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class ABCheckboxFormField extends ABFormField {

    private AppCompatActivity activity;
    private CheckBox checkbox;

    public ABCheckboxFormField(AppCompatActivity activity, CheckBox checkbox) {
        final ABCheckboxFormField self = this;

        this.activity = activity;
        this.checkbox = checkbox;

        this.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            this.notifyValueChanged();
        });
    }

    public boolean getValue() {
        return this.checkbox.isChecked();
    }

    public void setValue(Boolean value) {
        if (this.getValue() == value)
            return;

        this.checkbox.setChecked(value);
        this.notifyValueChanged();
    }


    @Override
    public void clear() {
        this.checkbox.setChecked(false);
    }

    @Override
    public void putValueInJSONObject(JSONObject row, String fieldName) throws
            JSONException {
        row.put(fieldName, this.getValue());
    }

    @Override
    public void setError(String message) {
        this.activity.runOnUiThread(() -> {
            this.checkbox.setError(message);
        });
    }

    @Override
    public void setValueFromJSONObject(JSONObject row, String fieldName) throws
            JSONException {
        if (row.isNull(fieldName))
            this.setValue(false);
        else
            this.setValue(row.getBoolean(fieldName));
    }
}
