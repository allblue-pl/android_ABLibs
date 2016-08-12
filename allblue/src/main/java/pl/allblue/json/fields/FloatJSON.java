package pl.allblue.json.fields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;


public class FloatJSON extends JSONField<Float>
{

    public FloatJSON(String name)
    {
        super(name);
    }


    /* JSONField Overrides */
    @Override
    protected boolean compareValue(Float value)
    {
        return this.getValue().compareTo(value) == 0;
    }

    @Override
    protected Float readValue(JSONArray json_array, int index) throws JSONException
    {
        return (float)json_array.getDouble(index);
    }

    @Override
    protected Float readValue(JSONObject json_object) throws JSONException
    {
        return (float)json_object.getDouble(this.getName());
    }

    @Override
    protected void writeValue(JSONArray json_array, int index, Float value)
            throws JSONException
    {
        json_array.put(index, (float)value);
    }

    @Override
    protected void writeValue(JSONObject json_object, Float value)
            throws JSONException
    {
        json_object.put(this.getName(), (float)value);
    }

}
