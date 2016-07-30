package pl.allblue.json;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by SfTd on 24/06/2016.
 */
public class JSONHelper
{

    static public JSONObject GetJSONFromFile(Context context, String file_path) throws IOException, JSONException
    {
        /* Data */
        File file = new File(context.getFilesDir(), file_path);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String json_string = new String(data, "UTF-8");

            return new JSONObject(json_string);
        }

        throw new FileNotFoundException();
    }

    static public String[] GetStringArray(JSONArray json_array)
            throws JSONException
    {
        String[] string_array = new String[json_array.length()];
        for (int i = 0; i < json_array.length(); i++)
            string_array[i] = json_array.getString(i);

        return string_array;
    }

}
