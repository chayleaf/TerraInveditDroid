package MarcusD.TerraInvedit;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ListInteractive<T> extends ListActivity
{
	ArrayList<T> listItems=new ArrayList<T>();
    ArrayAdapter<T> adapter;

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.activity_items);
        adapter=new ArrayAdapter<T>(this,
            android.R.layout.simple_list_item_1,
            listItems);
        setListAdapter(adapter);
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void refresh()
    {
        adapter.notifyDataSetChanged();
    }
}
