package pl.allblue.json.fields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;

/**
 * Created by SfTd on 28/05/2015.
 */
public class StringJSON extends JSONField
{

    private java.lang.String value = null;

    public StringJSON(java.lang.String name)
    {
        super(name);
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public String getValue()
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

        this.value = json_array.getString(index);
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
