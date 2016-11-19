package MarcusD.TerraInvedit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.CRC32;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ActivityKeyedit extends Activity
{
    File f;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyedit);
        
        f = new File(getApplicationContext().getFilesDir(), "debugkey.txt");
        
        if(f.exists())
        {
            EditText text = (EditText)findViewById(R.id.text_bfkey);
            try
            {
                DataInputStream dis = new DataInputStream(new FileInputStream(f));
                text.setText(dis.readLine(), EditText.BufferType.EDITABLE);
                dis.close();
            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void savekey(View v)
    {
        EditText text = (EditText)findViewById(R.id.text_bfkey);
        
        String str =  text.getText().toString().trim();
        
        if(str.length() == 0)
        {
            if(f.exists()) f.delete();
            Toast.makeText(getApplicationContext(), "Blowfish key deleted", Toast.LENGTH_LONG).show();
        }
        else
        {
            try
            {
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
                dos.write(str.getBytes("UTF-8"));
                dos.flush();
                dos.close();
            }
            catch (FileNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            finish();
        }
    }
    
    public void checkkey(View v) throws UnsupportedEncodingException
    {
        EditText text = (EditText)findViewById(R.id.text_bfkey);
        String str =  text.getText().toString().trim();
        
        if(str.length() != 32)
        {
            Toast.makeText(getApplicationContext(), "Key length is invalid (" + str.length() + " != 32)", Toast.LENGTH_LONG).show();
            return;
        }
        
        CRC32 crc = new CRC32();
        
        crc.update(str.getBytes("UTF-8"));
        long som = crc.getValue();
        
        Toast.makeText(getApplicationContext(), "Encryption key " + ((som == 1881093564) ? "matches! \\o/" : "doesn't match :/"), Toast.LENGTH_LONG).show();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_keyedit, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }
}
