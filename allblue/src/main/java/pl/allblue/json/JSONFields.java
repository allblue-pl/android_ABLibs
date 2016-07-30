package pl.allblue.json;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by SfTd on 28/05/2015.
 */
public class JSONFields
{

    static public <JSONFieldsClass extends JSONFields> List<JSONFieldsClass>
            ReadList(Class fields_class, String[] field_names,
            JSONArray json_array) throws Exception
    {
        List<JSONFieldsClass> fields_list = new ArrayList<>();
        int json_array_length = json_array.length();
        for (int i = 0; i < json_array_length; i++) {
            JSONFieldsClass fields = (JSONFieldsClass)fields_class.newInstance();
            fields.read(field_names, json_array.getJSONArray(i));
            fields_list.add(fields);
        }

        return fields_list;
    }


    private List<JSONField> fields = new ArrayList<>();

    public JSONFields()
    {

    }

    public void read(String[] field_names, JSONArray json_array)
            throws JSONException
    {
        List<String> field_names_list = Arrays.asList(field_names);

        for (JSONField field : this.fields) {
            int index = field_names_list.indexOf(field.getName());
            if (index == -1)
                    continue;

            field.read(json_array, index);
        }
    }

    public void write(String[] field_names, JSONArray json_array)
            throws JSONException
    {
        List<String> field_names_list = Arrays.asList(field_names);

        for (JSONField field : this.fields) {
            int index = field_names_list.indexOf(field.getName());
            if (index == -1)
                continue;

            field.write(json_array, index);
        }
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

                this.fields.add(json_field);
            } catch (Exception e) {
                Log.d("ABActivity", "Field exception: " + e.getMessage());
            }
        }
    }

}
