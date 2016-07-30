package pl.allblue.development.design;

import android.widget.ListView;

import pl.allblue.app.ABActivity;

/**
 * Created by SfTd on 08/06/2015.
 */
public class TestDesignActivity extends ABActivity
{

    public TestDesignActivity(String activity_layout_name)
    {
        super(activity_layout_name);
    }

    protected void addListView(int list_view_id, int item_list_layout_id)
    {
        ListView item_list = (ListView)this.findViewById(list_view_id);
        item_list.setAdapter(new TestListViewAdapter(this, item_list_layout_id));
    }

}
