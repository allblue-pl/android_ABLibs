package pl.allblue.json.fields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;

public class IntJSON extends JSONField
{

    private Integer value = null;

    public IntJSON(String name)
    {
        super(name);
    }

    public void setValue(Integer value)
    {
        this.value = value;
    }

    public Integer getValue()
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

        this.value = json_array.getInt(index);
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
