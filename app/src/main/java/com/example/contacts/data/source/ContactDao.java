package com.example.contacts.data.source;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.contacts.data.model.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    long insert(Contact contact);

    @Query("select * from CONTACTS")
    List<Contact> getContacts();

    @Query("DELETE FROM CONTACTS WHERE id = :contactID")
    void deleteContact(long contactID);

    @Query("Update contacts Set name = :name , phone = :phone , Image = :img Where id =:id ")
    void updateContact(int id ,String name,String phone , String img);

    @Query("Update contacts Set name = :name , phone = :phone , Image = :img  Where id =:id ")
    void updateContact(int id ,String name,String phone , String img ,String jobTitle ,String email);


}
