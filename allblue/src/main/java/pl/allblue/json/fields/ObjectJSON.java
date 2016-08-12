package pl.allblue.json.fields;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;

/**
 * Created by SfTd on 23/04/2016.
 */
public class ObjectJSON extends JSONField<JSONObject>
{

    public ObjectJSON(String name)
    {
        super(name);
    }

    public void setValue(String value)
    {
        try {
            this.setValue(new org.json.JSONObject(value));
        } catch (Exception e) {
            Log.e("ObjectJSON", "Cannot parse json_array.");
            this.setValue((JSONObject)null);
        }
    }


    /* ObjectJSON Overrides */
    @Override
    protected boolean compareValue(JSONObject value)
    {
        return this.getValue().toString().compareTo(value.toString()) == 0;
    }

    @Override
    protected JSONObject readValue(JSONArray json_array, int index)
            throws JSONException
    {
        return new JSONObject(json_array.getString(index));
    }

    @Override
    protected JSONObject readValue(JSONObject json_object) throws JSONException
    {
        return new JSONObject(json_object.getString(this.getName()));
    }

    @Override
    protected void writeValue(JSONArray json_array, int index, JSONObject value)
            throws JSONException
    {
        json_array.put(index, value.toString());
    }

    @Override
    protected void writeValue(JSONObject json_object, JSONObject value)
            throws JSONException
    {
        json_object.put(this.getName(), value.toString());
    }

}
