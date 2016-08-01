package pl.allblue.json.fields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;


public class FloatJSON extends JSONField
{

    private java.lang.Float value = null;

    public FloatJSON(String name)
    {
        super(name);
    }

    public float getValue()
    {
        return this.value;
    }

    public void setValue(java.lang.Float value)
    {
        this.value = value;
    }

    @Override
    public void read(JSONArray json_array, int index) throws JSONException
    {
        if (json_array.isNull(index)) {
            this.value = null;
            return;
        }

        this.value = (float)json_array.getDouble(index);
    }

    @Override
    public void write(JSONArray json_array, int index) throws JSONException
    {
        if (this.value == null) {
            json_array.put(index, JSONObject.NULL);
            return;
        }

        json_array.put(index, this.value);
    }
}
