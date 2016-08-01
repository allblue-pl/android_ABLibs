package pl.allblue.json.fields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;


public class DateJSON extends JSONField
{

    private Long value = null;

    public DateJSON(String name)
    {
        super(name);
    }

    public void setValue(Long value)
    {
        this.value = value;
    }

    public Long getValue()
    {
        if (this.value == null)
            return null;

        return this.value;
    }

    public java.util.Date getDate()
    {
        if (this.value == null)
            return null;

        java.util.Date date = new java.util.Date();
        date.setTime(this.value * 1000);

        return date;
    }

    @Override
    public void read(JSONArray json_array, int index) throws JSONException
    {
        if (json_array.isNull(index)) {
            this.value = null;
            return;
        }

        this.value = json_array.getLong(index);
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
