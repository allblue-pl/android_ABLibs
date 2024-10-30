package pl.allblue.ablibs.forms;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class ABTextFormField extends ABFormField {

    private AppCompatActivity activity;
    private TextInputEditText editText;
    private TextInputLayout layout;

    public ABTextFormField(AppCompatActivity activity, TextInputEditText editText,
            TextInputLayout layout) {
        final ABTextFormField self = this;

        this.activity = activity;
        this.editText = editText;
        this.layout = layout;

        this.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                self.notifyValueChanged();
            }
        });
    }

    public String getValue() {
        return this.editText.getText().toString();
    }

    public void setValue(String value) {
        if (this.getValue().equals(value))
            return;

        this.editText.setText(value);
        this.notifyValueChanged();

        if (this.layout.getEndIconMode() == TextInputLayout.END_ICON_CLEAR_TEXT) {
            this.layout.setEndIconMode(TextInputLayout.END_ICON_NONE);
            this.layout.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
            this.layout.setEndIconVisible(this.editText.isEnabled() &&
                    !this.editText.getText().toString().equals(""));
        }
    }


    @Override
    public void clear() {
        this.editText.setText("");
    }

    @Override
    public void putValueInJSONObject(JSONObject row, String fieldName) throws
            JSONException {
        row.put(fieldName, this.editText.getText().toString());
    }

    @Override
    public void setError(String message) {
        this.activity.runOnUiThread(() -> {
            this.layout.setError(message);
        });
    }

    @Override
    public void setValueFromJSONObject(JSONObject row, String fieldName) throws
            JSONException {
        if (row.isNull(fieldName))
            this.setValue("");
        else
            this.setValue(row.getString(fieldName));
    }
}
