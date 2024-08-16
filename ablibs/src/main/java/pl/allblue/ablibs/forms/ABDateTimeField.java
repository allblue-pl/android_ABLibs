package pl.allblue.ablibs.forms;

import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import pl.allblue.ablibs.R;
import pl.allblue.ablibs.util.Date;

public class ABDateTimeField extends ABFormField {

    private AppCompatActivity activity;
    private TextInputEditText editText;
    private TextInputLayout layout;

    private Long dateTime;

    public ABDateTimeField(AppCompatActivity activity, TextInputEditText editText,
            TextInputLayout layout) {
        this.activity = activity;
        this.editText = editText;
        this.layout = layout;

        this.dateTime = null;

        MaterialDatePicker<Long> mdp = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText(activity.getString(R.string.text_select_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build();
        mdp.addOnPositiveButtonClickListener(selection -> {
            Log.d("Test", selection == null ?
                    "Date: NULL" : ("Date: " + selection));
            this.dateTime = selection / 1000;
            this.editText.setText(Date.Format_Date(dateTime));
//            this.layout.requestFocus(View.FOCUS_FORWARD);
            View view = this.editText.focusSearch(View.FOCUS_DOWN);
            view.requestFocus();
        });

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
                if (s == null) {
                    Log.d("Test", "Test: null");
                }

                Log.d("Test", "Test: " + s.toString());
            }
        });
        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus)
                return;

            mdp.show(this.activity.getSupportFragmentManager(),
                    "abDateTimeField");
        });
        editText.setOnClickListener(v -> {
            mdp.show(this.activity.getSupportFragmentManager(),
                    "abDateTimeField");
        });
    }

    @Override
    public void clear() {

    }

    @Override
    public void setError(String message) {
        this.activity.runOnUiThread(() -> {
            this.layout.setError(message);
        });
    }
}
