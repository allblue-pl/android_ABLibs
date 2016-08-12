package pl.allblue.json.fields;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pl.allblue.json.JSONField;


public class DateJSON extends JSONField<Long>
{

    public DateJSON(String name)
    {
        super(name);
    }

    public java.util.Date getDate()
    {
        if (this.getValue() == null)
            return null;

        java.util.Date date = new java.util.Date();
        date.setTime(this.getValue() * 1000);

        return date;
    }


    /* JSONField Overrides */
    @Override
    protected boolean compareValue(Long value)
    {
        return this.getValue().compareTo(value) == 0;
    }

    @Override
    protected Long readValue(JSONArray json_array, int index) throws JSONException
    {
        return json_array.getLong(index);
    }

    @Override
    protected Long readValue(JSONObject json_object) throws JSONException
    {
        return json_object.getLong(this.getName());
    }

    @Override
    protected void writeValue(JSONArray json_array, int index, Long value)
            throws JSONException
    {
        json_array.put(index, (long)value);
    }

    @Override
    protected void writeValue(JSONObject json_object, Long value)
            throws JSONException
    {
        json_object.put(this.getName(), (long)value);
    }

}
