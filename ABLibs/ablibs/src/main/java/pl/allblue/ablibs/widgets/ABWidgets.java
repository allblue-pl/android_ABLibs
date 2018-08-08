package pl.allblue.ablibs.widgets;

import android.content.Context;
import android.util.Log;
import android.view.View;

import junit.framework.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ABWidgets
{

    static public void FindAll(Context context, Object widgetsHolder,
            View layoutView, String layoutPrefix, String idsPrefix)
    {
        Class<?> widgetsHolder_Class = widgetsHolder.getClass();
        while (true) {
            Field[] fields = null;
            try {
                fields = widgetsHolder_Class.getDeclaredFields();
            } catch (Exception e) {
                break;
            }

            for (Field field : fields) {
                try {
                    String fieldName = field.getName();
                    if (!Modifier.isPublic(field.getModifiers()))
                        continue;
                    if (!View.class.isAssignableFrom(field.getType()))
                        continue;

                    if (idsPrefix != null) {
                        if (fieldName.length() < idsPrefix.length() + 1)
                            continue;

                        if (!fieldName.substring(0, idsPrefix.length())
                                .equals(idsPrefix))
                            continue;

                        if (!Character.isUpperCase(fieldName.charAt(idsPrefix.length())))
                            continue;

                        fieldName = fieldName.substring(idsPrefix.length());
                    }

//                    if (!Modifier.isPublic(field.getModifiers())) {
//                        throw new AssertionError("Widget field '" +
//                                field.getName() + "' must be public.");
//                    }

//                    if (!View.class.isAssignableFrom(field.getType())) {
//                        throw new AssertionError("Field '" + field.getName() +
//                                "' type does not extend 'View'.");
//                    }

                    String viewName = layoutPrefix + "_" + fieldName;
                    int viewId = context.getResources().getIdentifier(viewName,
                            "id", context.getPackageName());

                    if (viewId == 0)
                        throw new AssertionError("Cannot get widget identifier: " + viewName);

                    View view = layoutView.findViewById(viewId);
                    if (view == null)
                        throw new AssertionError("Cannot find widget: " + viewName);

                    Log.d("ABWidgets", "Setting: " + field.getName() + " = " + (view == null));

                    field.set(widgetsHolder, view);
                } catch (Exception e) {
                    throw new AssertionError(e);
                }
            }

            widgetsHolder_Class = widgetsHolder_Class.getSuperclass();
            if (widgetsHolder_Class == null)
                break;
        }
    }

    static public void FindAll(Context context, Object widgetsHolder,
            View layoutView, String layoutPrefix)
    {
        ABWidgets.FindAll(context, widgetsHolder, layoutView, layoutPrefix, null);
    }

    static public void FindAll(Context context, Object widgetsHolder,
            View layoutView, int layoutId)
    {
        String layoutPrefix = context.getResources().getResourceEntryName(layoutId);

        ABWidgets.FindAll(context, widgetsHolder, layoutView, layoutPrefix, null);
    }

}
