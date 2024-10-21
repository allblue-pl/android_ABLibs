package pl.allblue.ablibs.forms;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class ABForm {

    private HashMap<String, ABFormField> fields;
    private TextView messageView;
    private State state;

    public ABForm() {
        this.fields = new HashMap<>();
        this.messageView = null;
        this.state = State.NOT_SET;
    }

    public void addField(String fieldName, ABFormField field) {
        field.addOnValueChangedListener(() -> {
            this.notifyFieldValueChanged();
        });
        this.fields.put(fieldName, field);
    }

    public State getState() {
        return this.state;
    }

    public JSONObject getValues() {
        JSONObject row = new JSONObject();

        Iterator<String> i = this.fields.keySet().iterator();
        while (i.hasNext()) {
            String fieldName = i.next();
            try {
                this.fields.get(fieldName).putValueInJSONObject(row, fieldName);
            } catch (JSONException e) {
                Log.e("ABForm", "Cannot put '" + fieldName +
                        "' value in row.");
            }
        }

        return row;
    }

    public void notifyFieldValueChanged() {
        if (this.state == State.NOT_SET)
            return;

        this.state = State.CHANGED;
    }

    public void resetState() {
        this.state = State.NOT_SET;
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

    public void setValidatorInfo(JSONObject validatorInfo) {
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

    public void setValues(JSONObject row) {
        Iterator<String> i = this.fields.keySet().iterator();
        while (i.hasNext()) {
            String fieldName = i.next();
            if (!row.has(fieldName))
                Log.d("ABForm", "No '" + fieldName + "' value in row.");
            try {
                this.fields.get(fieldName).setValueFromJSONObject(row, fieldName);
            } catch (JSONException e) {
                Log.e("ABForm", "Cannot get '" + fieldName +
                        "' value from row.", e);
            }
        }

        this.state = State.NOT_CHANGED;
    }


    public enum State {
        NOT_SET,
        NOT_CHANGED,
        CHANGED
    };

}
