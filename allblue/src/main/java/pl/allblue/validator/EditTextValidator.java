package pl.allblue.validator;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import pl.allblue.R;

public class EditTextValidator
{

    private Context context = null;
    List<RegexpField> fields = new ArrayList<>();

    public EditTextValidator(Context context)
    {
        this.context = context;
    }

    public void addError(EditText edit_text, String error_message)
    {

    }

    public void addRegexp(final EditText edit_text, boolean required, String regexp,
            String format)
    {
        RegexpField f = new RegexpField();
        f.editText = edit_text;
        f.required = required;
        f.regexp = regexp;
        f.format = format;

        edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                edit_text.setError(null);
            }
        });

        this.fields.add(f);
    }

    public void addRegexp(final EditText edit_text, boolean required)
    {
        this.addRegexp(edit_text, required, null, "");
    }

    public boolean validate()
    {
        boolean valid = true;

        for (int i = 0; i < this.fields.size(); i++) {
            RegexpField f = this.fields.get(i);
            String text = f.editText.getText().toString();

            f.editText.clearFocus();

            f.editText.setError(null);

            if (text.equals("")) {
                if (f.required) {
                    f.editText.setError(this.context.getString(R.string.notValid_Empty));

                    if (valid)
                        f.editText.requestFocus();
                    valid = false;
                }

                continue;
            }

            if (f.regexp == null)
                continue;

            if (Pattern.matches(f.regexp, text))
                continue;

            f.editText.setError(this.context.getString(R.string.notValid_Regexp) +
                    f.format);

            if (valid)
                f.editText.requestFocus();
            valid = false;
        }

        return valid;
    }

    class RegexpField
    {
        EditText editText = null;
        boolean required = false;
        String regexp = null;
        String format = "";
    }

}
