package pl.allblue.ablibs.forms;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

abstract public class ABFormField {

    private ArrayList<OnValueChanged> listeners_OnValueChanged;


    public ABFormField() {
        this.listeners_OnValueChanged = new ArrayList<>();
    }

    public void addOnValueChangedListener(OnValueChanged onValueChanged) {
        this.listeners_OnValueChanged.add(onValueChanged);
    }


    protected void notifyValueChanged() {
        Iterator<OnValueChanged> i = this.listeners_OnValueChanged.iterator();
        while (i.hasNext())
            i.next().onChanged();
    }


    public interface OnValueChanged {
        void onChanged();
    }


    abstract public void clear();
    abstract public void putValueInJSONObject(JSONObject row, String fieldName)
            throws JSONException;
    abstract public void setError(String message);
    abstract public void setValueFromJSONObject(JSONObject row,
            String fieldName) throws JSONException;
}
