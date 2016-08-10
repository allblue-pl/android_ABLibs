package pl.allblue.json;

import android.renderscript.Sampler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

abstract public class JSONField<ValueClass>
{

    private String name = null;
    private JSONFieldsSet fieldsSet = null;

    Object value = null;
    private boolean value_Set = false;

    Object updatedValue = null;
    private boolean updatedValue_Set = false;

    public JSONField(String name)
    {
        this.name = name;
    }

    public boolean isSet()
    {
        return this.value_Set;
    }

    public void read(JSONArray json_array, int index) throws JSONException
    {
        if (json_array.isNull(index)) {
            this.setValue(null);
            return;
        }

        this.readValue(json_array, index);
    }

    public void read(JSONObject json_object) throws JSONException
    {
        if (json_object.isNull(this.getName())) {
            this.setValue(null);
            return;
        }

        this.readValue(json_object);
    }

    public void write(JSONArray json_array, int index) throws JSONException
    {
        if (this.value == null) {
            json_array.put(index, JSONObject.NULL);
            return;
        }

        this.writeValue(json_array, index);
    }

    public void write(JSONObject json_object) throws JSONException
    {
        if (this.value == null) {
            json_object.put(this.getName(), JSONObject.NULL);
            return;
        }

        this.writeValue(json_object);
    }

    public ValueClass getValue()
    {
        if (this.updatedValue_Set)
            return (ValueClass)this.updatedValue;

        return (ValueClass)this.value;
    }

    public void setFieldsSet(JSONFieldsSet fields_set)
    {
        this.fieldsSet = fields_set;
    }

    public void setValue(ValueClass value, boolean update)
    {
        if (update) {
            if (this.isEqual(value))
                return;

            this.updatedValue = value;
            this.updatedValue_Set = true;

            if (this.fieldsSet != null) {
                if (!this.fieldsSet.isNew())
                    this.fieldsSet.setState_Updated();
            }
        } else {
            this.value = value;
            this.value_Set = true;
        }
    }

    public void setValue(ValueClass value)
    {
        this.setValue(value, false);
    }

    protected String getName()
    {
        return this.name;
    }


    abstract public boolean isEqual(ValueClass value);

    abstract protected void readValue(JSONArray json_array, int index)
            throws JSONException;
    abstract protected void readValue(JSONObject json_object)
            throws JSONException;

    abstract protected void writeValue(JSONArray json_array, int index)
            throws JSONException;
    abstract protected void writeValue(JSONObject json_object)
            throws JSONException;

}
