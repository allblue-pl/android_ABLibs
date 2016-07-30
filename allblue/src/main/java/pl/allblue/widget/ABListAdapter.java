
package pl.allblue.widget;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by SfTd on 2015-04-15.
 */
abstract public class ABListAdapter<Class> extends BaseAdapter
    implements View.OnClickListener
{

    static private LayoutInflater inflater = null;

    private OnItemClickListener<Class> listener = null;

    private Activity activity = null;
    private List<Class> data;
    private int listItemLayoutId = -1;

    public ABListAdapter(Activity activity, int list_item_layout_id)
    {
        super();

        this.activity = activity;
        this.data = new ArrayList<>();
        this.listItemLayoutId = list_item_layout_id;

        this.inflater = (LayoutInflater)activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addView(int position, View view)
    {
        if (view == null)
            return;

        view.setOnClickListener(this);
        view.setTag(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

    public int getCount()
    {
        return this.data.size();
    }

    public List<Class> getData()
    {
        return this.data;
    }

    public Object getItem(int position)
    {
        return this.data.get(position);
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convert_view, ViewGroup parent)
    {
        if(convert_view == null)
            convert_view = inflater.inflate(this.listItemLayoutId, null);

        this.setViews(position, convert_view);

        convert_view.setTag(position);
        convert_view.setOnClickListener(this);

        return convert_view;
    }

    public Class get(int position)
    {
        return this.data.get(position);
    }

    @Override
    public void onClick(View v)
    {
        if (this.listener == null)
            return;

        Object tag = v.getTag();
        if (tag == null)
            this.listener.onItemClick(-1, v.getId());
        else
            this.listener.onItemClick((int)tag, v.getId());
    }

    abstract protected void setViews(int position, View view);

    public interface OnItemClickListener<CLASS>
    {
        void onItemClick(int position, int id);
    }

}
