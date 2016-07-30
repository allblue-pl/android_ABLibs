package pl.allblue.json.fields;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;

/**
 * Created by SfTd on 23/04/2016.
 */
public class ObjectJSON extends JSONField
{

    private org.json.JSONObject value = null;

    public ObjectJSON(String name)
    {
        super(name);
    }

    public org.json.JSONObject getValue()
    {
        return this.value;
    }

    public void setValue(String value)
    {
        try {
            this.value = new org.json.JSONObject(value);
        } catch (Exception e) {
            Log.d("Object", "Cannot parse json_array.");
            this.value = null;
        }
    }

    @Override
    public void read(JSONArray json_array, int index) throws JSONException
    {
        if (json_array.isNull(index)) {
            this.value = null;
            return;
        }

        this.setValue((String)json_array.getString(index));
    }

    @Override
    public void write(JSONArray json_array, int index) throws JSONException
    {
        if (this.value == null) {
            json_array.put(index, org.json.JSONObject.NULL);
            return;
        }

        json_array.put(index, this.value.toString());
    }

}
