package com.bobboau.December20;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter {
	Activity activity = null;
	int background_style;
	ArrayList<Contact> _contacts = null;//always use the method to access this
	private ArrayList<Contact> getContacts()
	{
		if(_contacts == null)
		{
			_contacts = Contact.getContacts(activity);
		}
		return _contacts;
	}
	
	public ContactAdapter(Activity a)
	{
		activity = a;
		TypedArray attr = a.obtainStyledAttributes(R.styleable.ContactCard);
		background_style = attr.getResourceId(R.styleable.ContactCard_android_galleryItemBackground, 0);
		attr.recycle();
	}
	
	public int getCount()
	{
		return getContacts().size();
	}
	
    public Object getItem(int position) {
    	ArrayList<Contact> contacts = getContacts();
        return contacts.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        Contact contact = getContacts().get(position);
         
        if(convertView == null)
        {
	        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.contact, null, false);
        }
        
        TextView name = (TextView)convertView.findViewById(R.id.contact_name);
        name.setText(contact.getName());
        
        ImageView picture = (ImageView)convertView.findViewById(R.id.contact_picture);
        picture.setImageBitmap(contact.getPicture());
        
        convertView.setLayoutParams(new Gallery.LayoutParams(256, 256));
        convertView.setBackgroundResource(background_style);

		return convertView;
    }
}
