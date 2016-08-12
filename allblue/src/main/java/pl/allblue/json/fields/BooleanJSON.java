package pl.allblue.json.fields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;


public class BooleanJSON extends JSONField<Boolean>
{

    public BooleanJSON(String name)
    {
        super(name);
    }


    /* JSONField Overrides */
    @Override
    protected boolean compareValue(Boolean value)
    {
        return this.getValue().compareTo(value) == 0;
    }

    @Override
    protected Boolean readValue(JSONArray json_array, int index)
            throws JSONException
    {
        return json_array.getBoolean(index);
    }

    @Override
    protected Boolean readValue(JSONObject json_object) throws JSONException
    {
        return json_object.getBoolean(this.getName());
    }

    @Override
    protected void writeValue(JSONArray json_array, int index, Boolean value)
            throws JSONException
    {
        json_array.put(index, (boolean)value);
    }

    @Override
    protected void writeValue(JSONObject json_object, Boolean value)
            throws JSONException
    {
        json_object.put(this.getName(), (boolean)value);
    }

}
