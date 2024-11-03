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
        this.activity = activity;
        this.editText = editText;
        this.layout = layout;

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                notifyValueChanged();
            }
        });
    }

    public String getLabel() {
        return editText.getHint().toString();
    }

    public String getValue() {
        return editText.getText().toString();
    }

    public void setValue(String value) {
        if (getValue().equals(value))
            return;

        editText.setText(value);
        notifyValueChanged();

        if (layout.getEndIconMode() == TextInputLayout.END_ICON_CLEAR_TEXT) {
            layout.setEndIconMode(TextInputLayout.END_ICON_NONE);
            layout.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);
            layout.setEndIconVisible(editText.isEnabled() &&
                    !editText.getText().toString().equals(""));
        }
    }


    @Override
    public void clear() {
        editText.setText("");
    }

    @Override
    public void putValueInJSONObject(JSONObject row, String fieldName) throws
            JSONException {
        row.put(fieldName, editText.getText().toString());
    }

    @Override
    public void setError(String message) {
        activity.runOnUiThread(() -> {
            layout.setError(message);
        });
    }

    @Override
    public void setValueFromJSONObject(JSONObject row, String fieldName) throws
            JSONException {
        if (row.isNull(fieldName))
            setValue("");
        else
            setValue(row.getString(fieldName));
    }
}
