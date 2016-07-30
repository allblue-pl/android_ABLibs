package pl.allblue.development.design;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by SfTd on 08/06/2015.
 */
public class TestListViewAdapter extends BaseAdapter
{

    private LayoutInflater inflater = null;
    private int listItemLayoutId = -1;

    public TestListViewAdapter(Activity activity, int list_item_layout_id)
    {
        super();

        this.listItemLayoutId = list_item_layout_id;

        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return 10;
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convert_view, ViewGroup parent)
    {
        if(convert_view == null)
            convert_view = this.inflater.inflate(this.listItemLayoutId, null);

        return convert_view;
    }

}
