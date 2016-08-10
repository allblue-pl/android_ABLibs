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
    public boolean isEqual(Long value)
    {
        return this.getValue().compareTo(value) == 0;
    }

    @Override
    public void readValue(JSONArray json_array, int index) throws JSONException
    {
        this.setValue(json_array.getLong(index));
    }

    @Override
    public void readValue(JSONObject json_object) throws JSONException
    {
        this.setValue(json_object.getLong(this.getName()));
    }

    @Override
    public void writeValue(JSONArray json_array, int index) throws JSONException
    {
        json_array.put(index, (long)this.getValue());
    }

    @Override
    public void writeValue(JSONObject json_object) throws JSONException
    {
        json_object.put(this.getName(), (long)this.getValue());
    }

}
