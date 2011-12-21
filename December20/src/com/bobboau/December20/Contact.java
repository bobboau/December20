package com.bobboau.December20;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;

/*
 * class that abstracts the contacts database
 */

public class Contact {
	int cursor_position;
	Activity activity;
	private Contact(int _cursor_position, Activity _activity)
	{
		cursor_position = _cursor_position;
		activity = _activity;
	}
	
	static Cursor cursor = null;
	
	public static ArrayList<Contact> getContacts(Activity activity)
	{
		if(cursor == null)
		{
			//we only want to grab this once
			Uri uri = ContactsContract.Contacts.CONTENT_URI;

			String[] projection = {
				ContactsContract.Contacts._ID,
				ContactsContract.Contacts.PHOTO_ID,
				ContactsContract.Contacts.DISPLAY_NAME
			};
			
			String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";
			cursor = activity.managedQuery(uri, projection, null, null, sortOrder);
		}
		
		//get what we will return
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		
		//if there is anything to return...
		if(cursor.moveToFirst())
		{
			//...add everything to return
			do
			{
				contacts.add(
						new Contact(cursor.getPosition(), activity)
				);
			}
			while (cursor.moveToNext());
		}
		
		return contacts;
	}
	
	private String name = null;
	public String getName()
	{
		if(name == null)
		{
			cursor.moveToPosition(cursor_position);
			name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
		}
		
		return name;
	}
	
	private Bitmap picture = null;
	public Bitmap getPicture()
	{
		if(picture == null)
		{
			cursor.moveToPosition(cursor_position);
			long photo_id = cursor.getLong(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_ID));
			
			//got to grab it out of a different table
			Uri data_uri = ContactsContract.Data.CONTENT_URI;
			    
			String[] projection = {
				ContactsContract.Contacts.Data._ID,
				ContactsContract.Contacts.Data.DATA15
			};
			
			String where = "_ID = "+photo_id;
			
			Cursor picture_cursor = activity.managedQuery(data_uri, projection, where, null, null);
			if(picture_cursor.moveToFirst())
			{
				picture = BitmapFactory.decodeStream(
						new ByteArrayInputStream(
								picture_cursor.getBlob(
										picture_cursor.getColumnIndex(
												ContactsContract.Contacts.Data.DATA15
										)
								)
						)
				);
			}
			//it seems returning null does what we want
		}
		return picture;
	}
}
