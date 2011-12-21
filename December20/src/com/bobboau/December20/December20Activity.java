package com.bobboau.December20;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Gallery;
import android.widget.Toast;

public class December20Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        int count = Contact.getContacts(this).size();
        
        if(count < 2)
        {
        	Toast.makeText(this, "you need some contacts for this to do anything\npreferably with pictures", 3000).show();
        }
        Gallery gallery = (Gallery) findViewById(R.id.contacts_gallery);
        gallery.setAdapter(new ContactAdapter(this));
    }
}