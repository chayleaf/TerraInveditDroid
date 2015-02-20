package MarcusD.TerraInvedit;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.chainfire.libsuperuser.Debug;
import eu.chainfire.libsuperuser.Shell;
import MarcusD.TerraInvedit.ActivityPlayers.Playerdata;
import MarcusD.TerraInvedit.ItemRegistry.ItemEntry;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ActivityPlayers extends ListInteractive<Playerdata> {

	public boolean bad = false;
	Bitmap wotimg;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items);
        
        
        Log.d("trace", "Loading files");
        
        //Toast t2 = Toast.makeText(getApplicationContext(), "Loading files...", Toast.LENGTH_LONG);
        //t2.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        //t2.show();
        
        File f = new File("/data/data/com.and.games505.TerrariaPaid/files/");
        if(!f.exists() || !f.isDirectory())
        {
        	Log.d("trace", "FAK");
        	Log.d("trace", f.exists() + " " + f.canRead() + " " + f.canWrite() + " " + f.setReadOnly());
        	ejjoj(getString(R.string.javatrans_noterr), true);
        	return;
        }
        
        //Toast.makeText(getApplicationContext(), "Loading files...", Toast.LENGTH_LONG).show();
        
        Log.d("trace", "bad...");
        
        Debug.setSanityChecksEnabled(false);
        List<String> str = Shell.SU.run("ls /data/data/com.and.games505.TerrariaPaid/files/*.player");
        Debug.setSanityChecksEnabled(true);
        
        for(String s : str)
        {
        	listItems.add(new Playerdata(new File(s), s.substring(s.lastIndexOf("/") + 1, s.length() - 18)));
        }
        
        refresh();
    }
    
    private void ejjoj(String txt, Boolean top)
    {
    	Toast t = Toast.makeText(this, txt, Toast.LENGTH_LONG);
    	if(top) t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
    	t.show();
		finish();
		android.os.Process.killProcess(android.os.Process.myPid());
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_players, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id)
	{
		final Playerdata pd = (Playerdata)getListView().getItemAtPosition(position);
		
		File data = getApplicationContext().getFilesDir();
		File items = new File(data, "Items.txt");
		extractifnex(items, "Items.txt", false);
		final File wotfeil = new File(data, "uwot.bmp");
		extractifnex(wotfeil, "uwot.bmp", false);
		Log.i("itemreg", "Pos "+ position + ", id:"+id);
		final ProgressDialog pdi = ProgressDialog.show(this, getString(R.string.javatrans_dialog_loader), getString(R.string.javatrans_werk), true);
        
        pdi.setCancelable(false);
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					boolean bad2 = Shell.SU.available();
					
					Log.d("roottest2", "is root bad? " + bad2);
					
					if(bad2)
					{
						Log.d("roottest2", "loading...");
						wotimg = BitmapFactory.decodeFile(wotfeil.getAbsolutePath());
						if(ItemRegistry.instance == null)
						{
							ItemRegistry.instance = new ItemRegistry(getApplicationContext());
							Map<Short, ItemEntry> itemmap = new HashMap<Short, ItemEntry>();
							itemmap.put((short)0, ItemRegistry.instance.new ItemEntry((short)0, getString(R.string.javatrans_item_missingno), wotimg));
							Log.i("itemreg", "no itemreg, new");
						
							try
							{
								Log.i("itemreg", "reading");
								BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(getApplicationContext().getFilesDir(), "Items.txt")));
								Log.i("itemreg", "fak?");
								String line = null;
								long dumm = 0;
								while ((line = bufferedReader.readLine()) != null)
								{
								String[] tmpp = line.split("=");
								short s = Short.parseShort(tmpp[0]);
								itemmap.put(s, ItemRegistry.instance.new ItemEntry(s, tmpp[1], wotimg));
								//Log.i("itemreg", tmpp[0] + "=" + tmpp[1]);
								dumm++;
								}
								bufferedReader.close();
								Log.i("itemreg2", "Num: "+dumm);
							}
							catch(IOException ioe)
							{
								Log.i("itemreg", "fuck ioex");
								ioe.printStackTrace();
							}
							catch(Throwable t)
							{
								Log.i("itemreg", "other ejjoj");
								t.printStackTrace();
							}
							
							ItemRegistry.instance.basemap.putAll(new MapSorter<Short, ItemEntry>(itemmap)
							{
								@Override
								public int compare(Short key1, ItemEntry val1, Short key2, ItemEntry val2)
								{
									return key1.compareTo(key2);
								};
							}.sort());
						}
						
						List<String> ret = Shell.SU.run("cat " + pd.file.getAbsolutePath() + " | base64");
						String b64 = "";
						for(String s : ret)
						{
							b64 += s;
						}
						
						byte[] pdata = Base64.decode(b64, Base64.NO_WRAP);
						
						Intent it = new Intent(getApplicationContext(), InveditActivity.class);
						it.putExtra("INV", pdata);
						it.putExtra("ABSINV", pd.file.getAbsolutePath());
						Log.d("wut", "start teh goddamn activity");
						startActivityForResult(it, 56);
					}
					else
					{
						finish();
					}
					
				}
				catch (Throwable t)
				{
					Log.w("itemreg", "fakejjojouter");
					t.printStackTrace();
				}
				pdi.dismiss();
			}
		}).start();
	}
	
	@Override
	protected void onActivityResult(int req, int res, final Intent dat)
	{
		if(req == 56)
		{
			if(res == 69)
			{
				final ProgressDialog pdi = ProgressDialog.show(this, getString(R.string.javatrans_dialog_save), getString(R.string.javatrans_werk), true);
		        
		        pdi.setCancelable(false);
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							boolean bad2 = Shell.SU.available();
							
							Log.d("roottest2", "is root bad? " + bad2);
							
							if(bad2)
							{
								File f = new File(getApplicationContext().getFilesDir(), "saev.bin");
								if(f.exists()) f.delete();
								BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
								bos.write(dat.getByteArrayExtra("INV"));
								bos.flush();
								bos.close();
								//Shell.SU.run("rm " + dat.getStringExtra("ABSINV"));
								Shell.SU.run("cp " + f.getAbsolutePath() + " " + dat.getStringExtra("ABSINV"));
							}
							else
							{
								finish();
							}
							
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
						pdi.dismiss();
					}
				}).start();
			}
		}
		else
		{
			super.onActivityResult(req, res, dat);
		}
	}
	
	private void extractifnex(File f, String wot, boolean force)
	{
		if(!f.exists() || force)
		{
			try
			{
				AssetManager asm = getApplicationContext().getAssets();
			
				InputStream fis = asm.open(wot);
				DataOutputStream fos = new DataOutputStream(new FileOutputStream(f.getAbsoluteFile()));
			
			
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = fis.read(buf)) > 0)
				{
					fos.write(buf, 0, len);
				}
				fos.close();
				fis.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class Playerdata
    {
    	public Playerdata(File f, String c)
    	{
    		this.file = f;
    		this.caption = c;
    	}
    	public File file;
    	public String caption;
    	
    	@Override
    	public String toString()
    	{
			return caption + "\n" + file.toString();
    		
    	}
    }
}
