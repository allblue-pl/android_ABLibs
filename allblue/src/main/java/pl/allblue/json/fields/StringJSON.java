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
    public boolean isEqual(String value)
    {
        return this.getValue().compareTo(value) == 0;
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
        json_array.put(index, this.getValue());
    }

    @Override
    public void writeValue(JSONObject json_object) throws JSONException
    {
        json_object.put(this.getName(), this.getValue());
    }

}
