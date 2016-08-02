package pl.allblue.json;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import pl.allblue.json.fields.BooleanJSON;
import pl.allblue.json.fields.DateJSON;
import pl.allblue.json.fields.FloatJSON;
import pl.allblue.json.fields.IntJSON;
import pl.allblue.json.fields.ObjectJSON;
import pl.allblue.json.fields.StringJSON;

abstract public class JSONField
{

    private String name = null;
    private boolean valueSet = false;

    public JSONField(String name)
    {
        this.name = name;
    }

    public boolean isSet()
    {
        return this.valueSet;
    }

    protected String getName()
    {
        return this.name;
    }

    protected void initValue()
    {
        this.valueSet = true;
    }

    abstract public void read(JSONArray json_array, int index)
            throws JSONException;
    abstract public void write(JSONArray json_array, int index)
            throws JSONException;

}
