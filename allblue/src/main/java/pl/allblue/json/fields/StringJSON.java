package pl.allblue.json.fields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;

public class StringJSON extends JSONField<String>
{

    public StringJSON(java.lang.String name)
    {
        super(name);
    }


    /* JSONField Overrides */
    @Override
    protected boolean compareValue(String value)
    {
        if (this.getValue() == null)
            return value == null;

        return this.getValue().compareTo(value) == 0;
    }

    @Override
    protected String readValue(JSONArray json_array, int index) throws JSONException
    {
        return json_array.getString(index);
    }

    @Override
    protected String readValue(JSONObject json_object) throws JSONException
    {
        return json_object.getString(this.getName());
    }

    @Override
    protected void writeValue(JSONArray json_array, int index, String value)
            throws JSONException
    {
        json_array.put(index, value);
    }

    @Override
    protected void writeValue(JSONObject json_object, String value) throws JSONException
    {
        json_object.put(this.getName(), value);
    }

}
