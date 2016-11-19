package MarcusD.TerraInvedit;

import java.io.File;

import eu.chainfire.libsuperuser.Shell;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

	public Boolean beroken = false;
	ProgressDialog pd;
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //File f = new File("/data/data/com.and.games505.TerrariaPaid/files/");
        //if(!f.exists() || !f.isDirectory())
        Boolean haz = false;
        PackageManager pm = getPackageManager();
        PackageInfo pi = null;
        try
        {
            pi = pm.getPackageInfo("com.and.games505.TerrariaPaid", 0);
            haz = true;
        }
        catch(PackageManager.NameNotFoundException e) {}
        
        if(!haz)
        {
            //Log.d("trace", "FAK");
            //Log.d("trace", f.exists() + " " + f.canRead() + " " + f.canWrite() + " " + f.setReadOnly());
            Button btn = (Button)findViewById(R.id.button1);
            btn.setText(getString(R.string.javatrans_noterr));
            btn.setEnabled(false);
            return;
        }
    }
    
    public void ackroot(View w)
    {
    	//Toast t = Toast.makeText(getApplicationContext(), getString(R.string.javatrans_rootprogress), Toast.LENGTH_LONG);
        //t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        //t.show();
        
        pd = ProgressDialog.show(this, "ROOT", getString(R.string.javatrans_rootprogress), true);
        
        pd.setCancelable(true);
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					beroken = !Shell.SU.available();
					
					
					
					Log.d("roottest", "is root beroken? " + beroken);
					
					if(beroken)
					{
						runOnUiThread(new Runnable()
						{
							@Override
							public void run()
							{
								Toast tempast = Toast.makeText(MainActivity.this.getApplicationContext(), getString(R.string.javatrans_rootejj), Toast.LENGTH_LONG);
								//tempast.setGravity(android.view.Gravity.CENTER, 0, 0);
								tempast.show();
							}
						});
					}
					else
					{
						startActivity(new Intent(MainActivity.this, ActivityPlayers.class));
					}
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				pd.dismiss();
			}
		}).start();
    }
    
    public void showkey(View v)
    {
        startActivity(new Intent(MainActivity.this, ActivityKeyedit.class));
    }
    
    
}
