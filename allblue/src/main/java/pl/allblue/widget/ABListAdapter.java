
package pl.allblue.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

abstract public class ABListAdapter<DataClass> extends BaseAdapter
    implements View.OnClickListener
{

    static private LayoutInflater inflater = null;


    private Context context = null;

    private List<DataClass> data;
    private int listItemLayoutId = -1;

    private OnItemClickListener<DataClass> listener = null;

    public ABListAdapter(Context context, int list_item_layout_id)
    {
        super();

        this.context = context;

        this.data = new ArrayList<>();
        this.listItemLayoutId = list_item_layout_id;

        this.inflater = (LayoutInflater)this.context.getSystemService(
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

    public List<DataClass> getData()
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

    public DataClass get(int position)
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

    public interface OnItemClickListener<Class>
    {
        void onItemClick(int position, int id);
    }

}
