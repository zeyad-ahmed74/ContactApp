package com.example.contacts.data.source;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.contacts.data.model.Contact;

@Database(entities = {Contact.class} ,version = 2 ,exportSchema = false)
@TypeConverters(converts.class)
public abstract class ContactDataBase extends RoomDatabase {

    private final static String Contact_DB="Contact_DB";

    public abstract ContactDao contactDao();
    private static ContactDataBase contactDataBase;

    public static synchronized ContactDataBase getContactDataBase(Context context){
        if(contactDataBase==null){
            contactDataBase= Room.databaseBuilder(context.getApplicationContext(),ContactDataBase.class,Contact_DB)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return contactDataBase;
    }

}
