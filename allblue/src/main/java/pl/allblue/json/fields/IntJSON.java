package pl.allblue.json.fields;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;

public class IntJSON extends JSONField<Integer>
{

    private Integer value = null;

    public IntJSON(String name)
    {
        super(name);
    }


    /* JSONField Overrides */
    @Override
    public boolean isEqual(Integer value)
    {
        return this.getValue().compareTo(value) == 0;
    }

    @Override
    public void readValue(JSONArray json_array, int index) throws JSONException
    {
        this.setValue(json_array.getInt(index));
    }

    @Override
    public void readValue(JSONObject json_object) throws JSONException
    {
        this.setValue(json_object.getInt(this.getName()));
    }

    @Override
    public void writeValue(JSONArray json_array, int index) throws JSONException
    {
        json_array.put(index, (int)this.getValue());
    }

    @Override
    public void writeValue(JSONObject json_object) throws JSONException
    {
        json_object.put(this.getName(), (int)this.getValue());
    }
    
}
