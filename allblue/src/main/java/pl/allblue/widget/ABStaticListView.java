package pl.allblue.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by SfTd on 21/07/2015.
 */

public class ABStaticListView<CLASS> extends LinearLayout
{

    private ABListAdapter<CLASS> adapter = null;
    private DataSetObserver dataSetObserver = null;

    private LayoutInflater inflater = null;

    public ABStaticListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        this.dataSetObserver = new DataSetObserver(this);
        this.inflater = LayoutInflater.from(context);
    }

    public void setAdapter(ABListAdapter<CLASS> adapter)
    {
        this.adapter = adapter;
        this.adapter.registerDataSetObserver(this.dataSetObserver);
        this.refreshItems();
    }

    private void refreshItems()
    {
        this.removeAllViews();

        int items_length = this.adapter.getCount();

        for (int i = 0; i < items_length; i++) {
            View view = this.adapter.getView(i, null, this);
            this.addView(view);
        }
    }

    private class DataSetObserver extends android.database.DataSetObserver
    {

        private ABStaticListView staticListView = null;

        public DataSetObserver(ABStaticListView ab_static_list_view)
        {
            this.staticListView = ab_static_list_view;
        }

        @Override
        public void onChanged()
        {
            this.staticListView.refreshItems();
        }

        @Override
        public void onInvalidated()
        {
            this.staticListView.refreshItems();
        }

    }

}
