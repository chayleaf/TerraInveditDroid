package MarcusD.TerraInvedit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import MarcusD.TerraInvedit.ItemRegistry.Buff;
import MarcusD.TerraInvedit.ItemRegistry.ItemEntry;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnScrollListener;
import android.widget.NumberPicker.OnValueChangeListener;

public class ActivityItemedit extends Activity
{
	short iid;
	short cnt;
	byte buf;
	List<Map.Entry<Short, ItemEntry>> s;
	
	@Override
	public void onCreate(Bundle b)
	{
	    long milis = System.currentTimeMillis();
	    
		super.onCreate(b);
		setContentView(R.layout.layout_itemedit);
		
		milis = System.currentTimeMillis() - milis;
		Log.w("timing", "create: " + milis);
		milis = System.currentTimeMillis();
		
		Intent it = getIntent();
		this.iid = it.getShortExtra("ID", (short)0);
		this.cnt = it.getShortExtra("CNT", (short)0);
		this.buf = it.getByteExtra("BUF", (byte)0);
		
		milis = System.currentTimeMillis() - milis;
        Log.w("timing", "intent: " + milis);
        milis = System.currentTimeMillis();
		
		//CNT
		NumberPicker et = (NumberPicker)findViewById(R.id.numItemnum);
		et.setMinValue(0);
		et.setMaxValue(999);
		et.setOrientation(NumberPicker.VERTICAL);
		et.setOnValueChangedListener(new OnValueChangeListener()
		{
		    @Override
		    public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		    {
		        ActivityItemedit.this.cnt = (short)newVal;
		    }
		});
		et.setValue(ActivityItemedit.this.cnt);
		
		milis = System.currentTimeMillis() - milis;
        Log.w("timing", "   cnt: " + milis);
        milis = System.currentTimeMillis();
		
		//ITEM
		s = new ArrayList<Map.Entry<Short, ItemEntry>>();
		s.addAll(ItemRegistry.instance.basemap.entrySet());
		
		milis = System.currentTimeMillis() - milis;
        Log.w("timing", "  item: " + milis);
        milis = System.currentTimeMillis();
        
		et = (NumberPicker)findViewById(R.id.numItemname);
		et.setMinValue(0);
		et.setMaxValue(s.size() - 1);
		String[] caps = new String[s.size()];
		
		milis = System.currentTimeMillis() - milis;
        Log.w("timing", "capini: " + milis);
        milis = System.currentTimeMillis();
        
		for(int i = 0; i != s.size(); i++)
		{
		    Map.Entry<Short, ItemEntry> va = s.get(i);
		    caps[i] = va.getValue().iname;
		    if(va.getValue().iid == iid) et.setValue(i);
		}
		et.setDisplayedValues(caps);
		
		milis = System.currentTimeMillis() - milis;
        Log.w("timing", "idfill: " + milis);
        milis = System.currentTimeMillis();
		/*et.setOnValueChangedListener(new OnValueChangeListener()
		{
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2)
			{
			    if(arg1 == arg2) return;
				iid = s.get(arg2).getValue().iid;
			}
		});*/
		et.setOnScrollListener(new OnScrollListener()
		{
		    @Override
		    public void onScrollStateChange(NumberPicker arg0, int arg1)
		    {
		        if(arg1 != OnScrollListener.SCROLL_STATE_IDLE) return;
		        
		        iid = s.get(arg0.getValue()).getValue().iid;
		        ((NumberPicker)findViewById(R.id.numItemID)).setValue(iid);
		        if(iid != 0 && cnt == 0)
		        {
		            cnt = 1;
		            ((NumberPicker)findViewById(R.id.numItemnum)).setValue(1);
		        }
		    }
		});
		
		//ITEMID
		et = (NumberPicker)findViewById(R.id.numItemID);
		et.setMinValue(0);
		et.setMaxValue(0xFFFF);
		et.setValue(iid);
		et.setOnScrollListener(new OnScrollListener()
		{
		    @Override
		    public void onScrollStateChange(NumberPicker arg0, int arg1)
		    {
		        if(arg1 != OnScrollListener.SCROLL_STATE_IDLE) return;
		        
		        iid = (short)arg0.getValue();
		        
		        Boolean fnd = false;
		        for(int i = 0; i != s.size(); i++)
		        {
		            Map.Entry<Short, ItemEntry> va = s.get(i);
		            if(va.getValue().iid == iid)
		            {
		                ((NumberPicker)findViewById(R.id.numItemname)).setValue(i);
		                fnd = true;
		                break;
		            }
		        }
		        
		        if(!fnd) ((NumberPicker)findViewById(R.id.numItemname)).setValue(0);
		    }
		});
		
		milis = System.currentTimeMillis() - milis;
        Log.w("timing", "2init2: " + milis);
        milis = System.currentTimeMillis();
		
		//BUFF
		Buff[] bufs = Buff.values();
		caps = new String[Buff.values().length];
		et = (NumberPicker)findViewById(R.id.numBuff);
		et.setMinValue(0);
		et.setMaxValue(caps.length - 1);
		et.setValue(buf);
		for(int i = 0; i != bufs.length; i++)
		{
		    caps[i] = bufs[i].toString();
		}
		et.setDisplayedValues(caps);
		et.setOnValueChangedListener(new OnValueChangeListener()
		{
		    @Override
		    public void onValueChange(NumberPicker arg0, int arg1, int arg2)
		    {
		        buf = (byte)arg2;
		    }
		});
		
		milis = System.currentTimeMillis() - milis;
        Log.w("timing", "end: " + milis);
	}
	
	public void fak(View v)
	{
		setResult(-1);
		finish();
	}
    
    public void saev(View v)
    {
    	Intent i = new Intent();
    	i.putExtra("POS", getIntent().getIntExtra("POS", -1));
    	if(iid == 0 || cnt == 0)
    	{
    		i.putExtra("ID", (short)0);
    		i.putExtra("CNT", (short)0);
    		i.putExtra("BUF", (byte)0);
    	}
    	else
    	{
    		i.putExtra("ID", iid);
    		i.putExtra("CNT", cnt);
    		i.putExtra("BUF", buf);
    	}
    	setResult(420, i);
    	finish();
    }
}
