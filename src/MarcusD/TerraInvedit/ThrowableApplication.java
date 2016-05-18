package MarcusD.TerraInvedit;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ThrowableApplication extends Application
{
	Boolean running = true;
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks()
        {
            
            @Override
            public void onActivityStopped(Activity arg0)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onActivityStarted(Activity arg0)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onActivitySaveInstanceState(Activity arg0, Bundle arg1)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onActivityResumed(Activity arg0)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onActivityPaused(Activity arg0)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onActivityDestroyed(Activity arg0)
            {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onActivityCreated(Activity arg0, Bundle arg1)
            {
                arg0.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                
            }
        });
		
		while(running)
		{
			try
			{
				Looper.loop();
				Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler()
				{
					@Override
					public void uncaughtException(Thread thread, Throwable ex)
					{
						showex(ex);
					}
				});
			}
			catch(Throwable t)
			{
				showex(t);
			}
		}
	}
	
	public void showex(Throwable t)
	{
		t.printStackTrace();
		StringWriter ejjwri = new StringWriter();
		t.printStackTrace(new PrintWriter(ejjwri));
		final AlertDialog ad = new AlertDialog.Builder(this).setTitle("Well, Dalvik threw an error at me").create();
		ad.setButton(AlertDialog.BUTTON_POSITIVE, "Copy texte", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				((CopyText)ad.findViewById(0x7FFF)).performLongClick();
			}
		});
		ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Nah, m8", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				ThrowableApplication.this.running = false;
				System.runFinalization();
				System.gc();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		ad.setCancelable(false);
		CopyText ct = new CopyText(ad.getContext());
		ct.setId(0x7FFF);
		ct.setText(ejjwri.toString());
		ct.setHorizontallyScrolling(true);
		ct.setHorizontalScrollBarEnabled(true);
		ct.setMovementMethod(ScrollingMovementMethod.getInstance());
		ad.setView(ct);
		ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		ad.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		ad.show();
	}
	
	private class CopyText extends TextView
	{

		public CopyText(Context context)
		{
			super(context);
			setOnLongClickListener(new OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View v)
				{
					ClipboardManager clippy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
					clippy.setText(((TextView)v).getText());
					Toast t = Toast.makeText(getContext(), "", Toast.LENGTH_LONG);
					t.setText("Text copied to the clipboard");
					t.show();
					return true;
				}
			});
		}
	}
}
