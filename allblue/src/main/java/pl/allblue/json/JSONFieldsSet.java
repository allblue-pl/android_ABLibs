package pl.allblue.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONFieldsSet
{

    static public <JSONFieldsClass extends JSONFieldsSet> List<JSONFieldsClass>
            GetList(Class fields_class, List<String> field_names,
            JSONArray json_array) throws Exception
    {
        List<JSONFieldsClass> fields_list = new ArrayList<>();
        int json_array_length = json_array.length();
        for (int i = 0; i < json_array_length; i++) {
            JSONFieldsClass json_fields = (JSONFieldsClass)fields_class.newInstance();

            if (field_names == null)
                json_fields.read(json_array.getJSONObject(i));
            else
                json_fields.read(field_names, json_array.getJSONArray(i));

            fields_list.add(json_fields);
        }

        return fields_list;
    }

    static public <JSONFieldsClass extends JSONFieldsSet> List<JSONFieldsClass>
            GetList_FromArrays(Class fields_class, List<String> field_names,
            JSONArray json_array) throws Exception
    {
        return JSONFieldsSet.GetList(fields_class, field_names, json_array);
    }

    static public <JSONFieldsClass extends JSONFieldsSet> List<JSONFieldsClass>
            GetList_FromObjects(Class fields_class, JSONArray json_array)
            throws Exception
    {
        return JSONFieldsSet.GetList(fields_class, null, json_array);
    }

    static public <JSONFieldsClass extends JSONFieldsSet>
            void RemoveDoubles(List<JSONFieldsClass> fields_list, String field_name)
    {
        Iterator<JSONFieldsClass> iter = fields_list.iterator();
        while (iter.hasNext()) {
            JSONFieldsClass fields = iter.next();
            if (!fields.isNew())
                continue;

            for (int i = 0; i < fields_list.size(); i++) {
                JSONFieldsSet fields_set_a = fields;
                JSONFieldsSet fields_set_b = fields_list.get(i);

                if (fields_set_b.isNew())
                    continue;

                if (fields_set_a.getField(field_name).isEqual(
                        fields_set_b.getField(field_name).getValue())) {
//                    Log.d("JSONFieldsSet", "Removing! " +
//                            fields.getField(field_name).getValue().toString() + "#" +
//                            fields_list.get(i).getField(field_name).getValue());
                    iter.remove();
                    break;
                }
            }
        }
    }


    private State state = State.NONE;
    private List<JSONField> fields = new ArrayList<>();

    public JSONFieldsSet()
    {

    }

    public JSONField getField(String field_name)
    {
        for (int i = 0; i < this.fields.size(); i++)
            if (this.fields.get(i).getName().equals(field_name))
                return this.fields.get(i);

        return null;
    }

    public List<JSONField> getFields()
    {
        return this.fields;
    }

    public JSONArray getJSONArray(List<String> field_names) throws JSONException
    {
        JSONArray json = new JSONArray();

        for (JSONField field : this.fields) {
            int index = field_names.indexOf(field.getName());
            if (index == -1)
                continue;

            field.write(json, index);
        }

        return json;
    }

    public JSONObject getJSONObject() throws JSONException
    {
        JSONObject json = new JSONObject();

        for (JSONField field : this.fields) {
            if (!field.isSet())
                continue;

            field.write(json);
        }

        return json;
    }

    public State getState()
    {
        return this.state;
    }

    public boolean isUpdated()
    {
        return this.state == State.UPDATED;
    }

    public boolean isNew()
    {
        return this.state == State.NEW;
    }

    public void read(List<String> field_names, JSONArray json_array)
            throws JSONException
    {
        for (JSONField field : this.fields) {
            int index = field_names.indexOf(field.getName());
            if (index == -1) {
//                Log.w("JSONFieldsSet", "Cannot find field `" + field.getName() +
//                        "` in: " + Arrays.toString(field_names.toArray()));
                continue;
            }

            field.read(json_array, index);
        }
    }

    public void read(JSONObject json_object)
            throws JSONException
    {
        for (JSONField field : this.fields) {
            if (json_object.has(field.getName()))
                field.read(json_object);
        }
    }

    public void setState(State state)
    {
        this.state = state;
    }

    public void setState_Updated()
    {
        this.state = State.UPDATED;
    }

    public void setState_New()
    {
        this.state = State.NEW;
    }

    protected void initializeFields()
    {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                if (!JSONField.class.isAssignableFrom(field.getType()))
                    continue;

                JSONField json_field = (JSONField)field.get(this);
                if (json_field == null)
                    continue;

                json_field.setFieldsSet(this);
                this.fields.add(json_field);
            } catch (Exception e) {
                Log.d("ABActivity", "Field exception: " + e.getMessage());
            }
        }
    }


    public enum State
    {

        NONE,
        NEW,
        UPDATED

    }

}
