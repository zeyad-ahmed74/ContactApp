package com.example.contacts.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.contacts.data.model.Contact;
import com.example.contacts.databinding.ItemContactLayoutBinding;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder>{


    private List<Contact> contacts;
    private  ContactClickListener clickListener;

    public ContactAdapter(ContactClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void addContact(List<Contact> contacts){
        this.contacts=contacts;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContactLayoutBinding binding = ItemContactLayoutBinding.inflate(LayoutInflater.from(
                parent.getContext()),parent,false);
        return new ContactHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactHolder holder, int position) {
        Contact contact = contacts.get(position);
        Log.d("eeeee",""+contact.getName());
        holder.binding.ContactName.setText(contact.getName());
        holder.binding.ContactPhone.setText(contact.getPhone());
        Glide.with(holder.binding.ContactImg.getContext()).load(contact.getImage())
                .into(holder.binding.ContactImg);
    }

    @Override
    public int getItemCount() {
        if(contacts != null)
          return contacts.size();
        return 0;
    }

     class ContactHolder extends RecyclerView.ViewHolder{

        ItemContactLayoutBinding binding;
        public ContactHolder(@NonNull ItemContactLayoutBinding binding) {
            super(binding.getRoot());
            this.binding=binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.OnItemClick(contacts.get(getLayoutPosition()));
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickListener.OnItemLongClick(contacts.get(getLayoutPosition()));
                    return true;
                }
            });

        }
    }
}
