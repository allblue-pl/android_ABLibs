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
    public boolean isEqual(JSONObject value)
    {
        return this.getValue().toString().compareTo(value.toString()) == 0;
    }

    @Override
    public void readValue(JSONArray json_array, int index) throws JSONException
    {
        this.setValue(json_array.getString(index));
    }

    @Override
    public void readValue(JSONObject json_object) throws JSONException
    {
        this.setValue(json_object.getString(this.getName()));
    }

    @Override
    public void writeValue(JSONArray json_array, int index) throws JSONException
    {
        json_array.put(index, this.getValue().toString());
    }

    @Override
    public void writeValue(JSONObject json_object) throws JSONException
    {
        json_object.put(this.getName(), this.getValue().toString());
    }

}
