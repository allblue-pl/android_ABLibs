package pl.allblue.ablibs.forms;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class ABForm {

    private HashMap<String, ABFormField> fields;
    private TextView messageView;

    public ABForm() {
        this.fields = new HashMap<>();
        this.messageView = null;
    }

    public void addField(String fieldName, ABFormField field) {
        this.fields.put(fieldName, field);
    }

    public void setField_Message(TextView messageView) {
        this.messageView = messageView;
    }

    public void setMessage_Failure(String message) {
        if (this.messageView == null)
            return;

        if (message == null) {
            this.messageView.setText("");
            this.messageView.setVisibility(TextView.INVISIBLE);
            return;
        }

        this.messageView.setText(message);
        this.messageView.setVisibility(TextView.VISIBLE);
    }

    public void setValidatorInfo(JSONObject validatorInfo)
    {
        Log.d("Test", validatorInfo.toString());

        try {
            JSONObject vFields = validatorInfo.getJSONObject("fields");
            Iterator<String> i = vFields.keys();
            while (i.hasNext()) {
                String fieldName = i.next();
                if (!this.fields.containsKey(fieldName))
                    continue;

                JSONObject vField = vFields.getJSONObject(
                        fieldName);
                if (vField.getBoolean("valid"))
                    this.fields.get(fieldName).setError(null);
                else {
                    JSONArray errorsArr = vField.getJSONArray("errors");
                    String error = "";
                    for (int j = 0; j < errorsArr.length(); j++) {
                        if (j > 0)
                            error += " ";
                        error += errorsArr.get(j);
                    }
                    this.fields.get(fieldName).setError(error);
                }
            }
        } catch (JSONException e) {
            Log.e("ABForm", "Cannot parse validator info.", e);
        }
    }

}
