package pl.allblue.ablibs.forms;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ABTextFormField extends ABFormField {

    private AppCompatActivity activity;
    private TextInputEditText editText;
    private TextInputLayout layout;

    public ABTextFormField(AppCompatActivity activity, TextInputEditText editText,
            TextInputLayout layout) {
        this.activity = activity;
        this.editText = editText;
        this.layout = layout;
    }

    public void setText(String text) {
        this.editText.setText(text);
    }


    @Override
    public void clear() {
        this.editText.setText("");
    }

    @Override
    public void setError(String message) {
        this.activity.runOnUiThread(() -> {
            this.layout.setError(message);
        });
    }
}
