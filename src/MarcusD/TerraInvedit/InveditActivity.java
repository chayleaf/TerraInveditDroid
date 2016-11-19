package MarcusD.TerraInvedit;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import org.apache.http.util.ByteArrayBuffer;

import MarcusD.TerraInvedit.ItemRegistry.Buff;
import MarcusD.TerraInvedit.ItemRegistry.ItemEntry;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class InveditActivity extends ListActivity {

	ArrayList<Item> listItems=new ArrayList<Item>();
	
	byte[] buf;
	String abs;
	ItemRegistry ir;
	public int offset = 0;
	boolean corrupt = false;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<Item> adapter;

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.activity_items);
        adapter=new ItemArrayAdapter(this, listItems);
        setListAdapter(adapter);
        Intent it = getIntent();
        buf = it.getByteArrayExtra("INV");
        abs = it.getStringExtra("ABSINV");
        ir = ItemRegistry.instance;	
        refresh();
    }

    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
    public void refresh()
    {
    	corrupt = false;
    	listItems.clear();
    	do
    	{
    	    ByteBuffer bb = ByteBuffer.allocate(4);
    	    bb.order(ByteOrder.LITTLE_ENDIAN);
    	    for(int i = 2; i != 6; i++) { Log.e("len", buf[i] + ""); bb.put(buf[i]); }
    	    offset = 77 + (bb.getInt(0) * 2);
    	    
    	    bb.clear();
    	    bb.put(0, buf[0]);
    	    bb.put(1, buf[1]);
    	    short gamever = bb.getShort();
    	    
    	    if(gamever > 0x16) offset++;
    	}
    	while(false);
    	for(int i = 0; i != 48; i++)
        {
    	    int iof = i * 5;
    		ByteBuffer bb = ByteBuffer.allocate(2);
    		bb.order(ByteOrder.LITTLE_ENDIAN);
    		bb.put(buf[iof + 0 + offset]);
    		bb.put(buf[iof + 1 + offset]);
    		short iid = bb.getShort(0);
    		bb.clear();
    		bb.put(buf[iof + 2 + offset]);
    		bb.put(buf[iof + 3 + offset]);
    		short cnt = bb.getShort(0);
    		ItemEntry ier = ir.basemap.get(iid);
    		if(ier.iid != iid)
    		{
    			corrupt = true;
    			Log.e("disc", "loaded "+ String.format("%04X", iid) + ", got " + String.format("%04X", ier.iid) + ", plus iid is "+ String.format("%02X", buf[iof + 1 + offset]) + String.format("%02X", buf[iof + 0 + offset]));
    		}
            listItems.add(new Item(ier, cnt, Buff.n(buf[iof + 4 + offset])));
        }
        adapter.notifyDataSetChanged();
        if(corrupt) Toast.makeText(getApplicationContext(), "The inventory may be corrupted, please watch out when editing!", Toast.LENGTH_LONG).show();
    }
    
    @Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		final Item i = (Item)getListView().getItemAtPosition(position);
		
		Intent it = new Intent(getApplicationContext(), ActivityItemedit.class);
		it.putExtra("ID", i.item.iid);
		it.putExtra("POS", position);
		it.putExtra("CNT", i.cnt);
		it.putExtra("BUF", (byte)i.buffs.ordinal());
		
		startActivityForResult(it, 666);
	}
    
    @Override
    protected void onActivityResult(int req, int res, final Intent dat)
    {
    	if(req == 666)
    	{
    		if(res == 420)
    		{
    			int pos = dat.getIntExtra("POS", 0);
    			ByteBuffer bb = ByteBuffer.allocate(2);
    			bb.order(ByteOrder.LITTLE_ENDIAN);
    			bb.putShort(dat.getShortExtra("ID", (short)0));
    			buf[(pos * 5) + 0 + offset] = bb.get(0);
    			buf[(pos * 5) + 1 + offset] = bb.get(1);
    			bb.clear();
    			bb.putShort(dat.getShortExtra("CNT", (short)0));
    			buf[(pos * 5) + 2 + offset] = bb.get(0);
    			buf[(pos * 5) + 3 + offset] = bb.get(1);
    			buf[(pos * 5) + 4 + offset] = dat.getByteExtra("BUF", (byte)0);
    			refresh();
    		}
    	}
    	else if(req == 33)
    	{
    		if(res == 3)
    		{
    			this.offset = dat.getIntExtra("PAD", 0);
    		}
    	}
    	else
    	{
    		super.onActivityResult(req, res, dat);
    	}
    }
	
	public class Item
	{
		public Item(ItemEntry iem, short c, Buff b)
		{
			this.item = iem;
			this.cnt = c;
			this.buffs = b;
		}
		public ItemEntry item;
		public short cnt;
		public Buff buffs;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_invedit, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		Log.d("option", item.getItemId() + " " + item.getTitle());
		 switch (item.getItemId())
		 {
	        case R.id.item1:
	        	Intent i = new Intent();
	        	i.putExtra("INV", buf);
	        	i.putExtra("ABSINV", getIntent().getStringExtra("ABSINV"));
	        	setResult(69, i);
	        	finish();
	            return true;
	        case R.id.item2:
	        	setResult(-1);
	        	finish();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public class ItemArrayAdapter extends ArrayAdapter<Item> {
		private final Context context;
		private final ArrayList<Item> values;
	 
		public ItemArrayAdapter(Context context, ArrayList<Item> values) {
			super(context, R.layout.list_items, values);
			this.context = context;
			this.values = values;
		}


		@SuppressLint("ViewHolder")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
			View rowView = inflater.inflate(R.layout.list_items, parent, false);
			TextView textView = (TextView) rowView.findViewById(R.id.label);
			ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
			Item it = values.get(position);
			if(it == null) return rowView;
			if(it.item == null)
			{
				textView.setText((it.buffs == Buff._ ? "" : it.buffs.toString()) + " " + getString(R.string.javatrans_missingno) + " " + it.cnt + " (???)");
				return rowView;
			}
			textView.setText((it.buffs == Buff._ ? "" : it.buffs.toString()) + " " + it.item.iname + " " + it.cnt + " (" + it.item.iid + ")");	 
			if(it.item.img != null) imageView.setImageBitmap(it.item.img);
	 
			return rowView;
		}
	}
}
