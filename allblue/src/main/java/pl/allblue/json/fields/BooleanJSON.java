package pl.allblue.json.fields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;


public class BooleanJSON extends JSONField
{

    private java.lang.Boolean value = null;

    public BooleanJSON(String name)
    {
        super(name);
    }

    public void setValue(java.lang.Boolean value)
    {
        this.value = value;
    }

    public java.lang.Boolean getValue()
    {
        return this.value;
    }

    @Override
    public void read(JSONArray json_array, int index) throws JSONException
    {
        if (json_array.isNull(index)) {
            this.value = null;
            return;
        }

        this.value = json_array.getBoolean(index);
    }

    @Override
    public void write(JSONArray json_array, int index) throws JSONException
    {
        if (this.value == null) {
            json_array.put(index, JSONObject.NULL);
            return;
        }

        json_array.put(index, (boolean)this.value);
    }

}
