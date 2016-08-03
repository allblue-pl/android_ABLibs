package pl.allblue.helpers;

import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

public class ABWidgets
{

    static public void Initialize(Object widgets_object, View parent_view,
            String widget_name_prefix, String field_name_prefix)
    {
        Class<?> widgets_object_class = widgets_object.getClass();

        int field_name_prefix_length = field_name_prefix.length();

        while (true) {
            Field[] fields = widgets_object_class.getDeclaredFields();
            for (Field field : fields) {
                try {
                    if (field.getName().length() < field_name_prefix_length + 1)
                        continue;

                    if (field.getName().indexOf(field_name_prefix) != 0)
                        continue;

                    if (!Character.isUpperCase(field.getName()
                            .charAt(field_name_prefix_length)))
                        continue;

                    if (!View.class.isAssignableFrom(field.getType())) {
                        Log.w("ABWidgets", "Incompatible widget type: " +
                                field.getName());
                        continue;
                    }

                    String view_name = widget_name_prefix + "_" +
                            field.getName().substring(1);
                    int view_id = parent_view.getResources().getIdentifier(view_name,
                            "id", parent_view.getContext().getPackageName());

                    if (view_id == 0)
                        throw new AssertionError("Cannot find widget: " + view_name);

                    View view = parent_view.findViewById(view_id);

                    field.set(widgets_object, view);
                } catch (Exception e) {
                    Log.e("ABWidgets", "Initialize error.", e);
                    throw new AssertionError("Cannot initialize ABWidgets.");
                }
            }

            widgets_object_class = widgets_object_class.getSuperclass();
            if (widgets_object_class == null)
                break;
        }
    }

    static public void Initialize(Object widgets_object, View parent_view,
            String resource_name_prefix)
    {
        ABWidgets.Initialize(widgets_object, parent_view,
                resource_name_prefix, "w");
    }

}
