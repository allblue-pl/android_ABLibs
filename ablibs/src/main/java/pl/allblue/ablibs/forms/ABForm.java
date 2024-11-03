package pl.allblue.ablibs.forms;

import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class ABForm {

    private List<String> fieldNames;
    private List<ABFormField> fields;

    private TextView messageView;
    private State state;

    public ABForm() {
        this.fieldNames = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.messageView = null;
        this.state = State.NOT_SET;
    }

    public void addField(String fieldName, ABFormField field) {
        field.addOnValueChangedListener(() -> {
            notifyFieldValueChanged();
        });
        fieldNames.add(fieldName);
        fields.add(field);
    }

    public void clearValues() {
        for (int i = 0; i < fields.size(); i++)
            fields.get(i).clear();
    }

    public State getState() {
        return state;
    }

    public JSONObject getValues() {
        JSONObject row = new JSONObject();

        for (int i = 0; i < fieldNames.size(); i++) {
            try {
                fields.get(i).putValueInJSONObject(row, fieldNames.get(i));
            } catch (JSONException e) {
                Log.e("ABForm", "Cannot put '" + fieldNames.get(i) +
                        "' value in row.");
            }
        }

        return row;
    }

    public void notifyFieldValueChanged() {
        if (state == State.NOT_SET)
            return;

        state = State.CHANGED;
    }

    public void resetState() {
        state = State.NOT_SET;
    }

    public void setField_Message(TextView messageView) {
        messageView = messageView;
    }

    public void setMessage_Failure(String message) {
        if (messageView == null)
            return;

        if (message == null) {
            messageView.setText("");
            messageView.setVisibility(TextView.INVISIBLE);
            return;
        }

        messageView.setText(message);
        messageView.setVisibility(TextView.VISIBLE);
    }

    public void setValidatorInfo(JSONObject validatorInfo) {
        try {
            JSONObject vFields = validatorInfo.getJSONObject("fields");
            Iterator<String> i = vFields.keys();
            while (i.hasNext()) {
                String fieldName = i.next();
                int fieldIndex = fieldNames.indexOf(fieldName);
                if (fieldIndex == -1)
                    continue;

                JSONObject vField = vFields.getJSONObject(
                        fieldName);
                if (vField.getBoolean("valid"))
                    fields.get(fieldIndex).setError(null);
                else {
                    JSONArray errorsArr = vField.getJSONArray("errors");
                    String error = "";
                    for (int j = 0; j < errorsArr.length(); j++) {
                        if (j > 0)
                            error += " ";
                        error += errorsArr.get(j);
                    }
                    fields.get(fieldIndex).setError(error);
                }
            }
        } catch (JSONException e) {
            Log.e("ABForm", "Cannot parse validator info.", e);
        }
    }

    public void setValues(JSONObject row) {
        for (int i = 0; i < fieldNames.size(); i++) {
            String fieldName = fieldNames.get(i);
            if (!row.has(fieldName))
                Log.d("ABForm", "No '" + fieldName + "' value in row.");
            try {
                fields.get(i).setValueFromJSONObject(row, fieldName);
            } catch (JSONException e) {
                Log.e("ABForm", "Cannot get '" + fieldName +
                        "' value from row.", e);
            }
        }

        state = State.NOT_CHANGED;
    }


    public enum State {
        NOT_SET,
        NOT_CHANGED,
        CHANGED
    };

}
