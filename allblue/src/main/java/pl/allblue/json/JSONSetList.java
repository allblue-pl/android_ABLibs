package pl.allblue.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JSONSetList<SetClass extends JSONSet> extends ArrayList<SetClass>
{

    static public <SetClass extends JSONSet> JSONSetList<SetClass>
            Create_FromArrays(Class<? extends JSONSet> set_class,
            List<String> field_names, JSONArray json_array) throws
            IllegalAccessException, InstantiationException, JSONException
    {
        JSONSetList set_list = new JSONSetList();
        set_list.addJSONArrays(set_class, field_names, json_array);

        return set_list;
    }

    static public <SetClass extends JSONSet> JSONSetList<SetClass>
            Create_FromObjects(Class<? extends JSONSet> set_class,
            JSONArray json_array) throws IllegalAccessException,
            InstantiationException, JSONException
    {
        JSONSetList set_list = new JSONSetList();
        set_list.addAll_JSONObjects(set_class, json_array);

        return set_list;
    }


    public JSONSetList()
    {
        super();
    }

    public void addJSONArrays(Class<? extends JSONSet> set_class,
            List<String> field_names, JSONArray json_array)
            throws IllegalAccessException, InstantiationException, JSONException
    {
        int json_array_length = json_array.length();
        for (int i = 0; i < json_array_length; i++) {
            SetClass json_set = (SetClass)set_class.newInstance();
            json_set.read(field_names, json_array.getJSONArray(i));

            this.add(json_set);
        }
    }

    public void addAll_JSONObjects(int index, Class<? extends JSONSet> set_class,
            JSONArray json_array) throws
            IllegalAccessException, InstantiationException, JSONException
    {
        int json_array_length = json_array.length();
        for (int i = 0; i < json_array_length; i++) {
            SetClass json_set = (SetClass)set_class.newInstance();
            json_set.read(json_array.getJSONObject(i));

            this.add(index + i, json_set);
        }
    }

    public void addAll_JSONObjects(Class<? extends JSONSet> set_class,
            JSONArray json_array) throws
            IllegalAccessException, InstantiationException, JSONException
    {
        this.addAll_JSONObjects(0, set_class, json_array);
    }

    public JSONArray getAllNew_JSONObjects() throws JSONException
    {
        JSONArray json_array = new JSONArray();
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).isNew())
                continue;

            json_array.put(i, this.get(i).getJSONObject());
        }

        return json_array;
    }

    public JSONArray getAllUpdated_JSONObjects(String id_field_name) throws JSONException
    {
        JSONArray json_array = new JSONArray();
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).isUpdated())
                continue;

            JSONObject set_json = this.get(i).getJSONObject(true);
            this.get(i).getField(id_field_name).write(set_json, false);

            Log.d("JSONSetList", "Id: " + this.get(i).getField(id_field_name).getValue().toString());
            Log.d("JSONSetList", "Setting: " + i + "# " + set_json.toString());
            json_array.put(set_json);
        }

        return json_array;
    }

    public SetClass getByField(String field_name, Object value)
    {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getField(field_name).isEqual(value))
                return this.get(i);
        }

        return null;
    }

    public void removeDoubles(String field_name)
    {
        Iterator<SetClass> iter = this.iterator();
        while (iter.hasNext()) {
            SetClass fields = iter.next();
            if (!fields.isNew())
                continue;

            for (int i = 0; i < this.size(); i++) {
                JSONSet fields_set_a = fields;
                JSONSet fields_set_b = this.get(i);

                if (fields_set_b.isNew())
                    continue;

                if (fields_set_a.getField(field_name).compareValue(
                        fields_set_b.getField(field_name).getValue())) {
                    iter.remove();
                    break;
                }
            }
        }
    }

    public void updateAllBy_JSONObjects(String compare_field_name,
            Class<? extends JSONSet> set_class, JSONArray json_array) throws
            IllegalAccessException, JSONException, InstantiationException
    {
        JSONSetList<JSONSet> set_list = JSONSetList.Create_FromObjects(set_class,
                json_array);
        for (int i = 0; i < set_list.size(); i++) {
            Log.d("JSONSetList", "Updating: " +
                    set_list.get(i).getField(compare_field_name).getValue());
            this.updateBy(compare_field_name, set_list.get(i));
        }
    }

    public void updateBy(String compare_field_name, JSONSet update_set)
    {
        for (int i = 0; i < this.size(); i++) {
            JSONSet set = this.get(i);
            JSONField field = set.getField(compare_field_name);
            JSONField update_field = update_set.getField(compare_field_name);

            if (field.isEqual(update_field.getValue()))
                set.update(update_set);
        }
    }

}
