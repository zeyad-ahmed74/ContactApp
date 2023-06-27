package com.example.contacts.ui.adapter;

import com.example.contacts.data.model.Contact;

public interface ContactClickListener {

    public void OnItemClick(Contact contact);
    public void OnItemLongClick(Contact contact);

}
