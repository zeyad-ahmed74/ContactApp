package com.example.contacts.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.contacts.R;
import com.example.contacts.data.model.Contact;
import com.example.contacts.databinding.FragmentContactDetailsBinding;

public class ContactDetails extends Fragment {

    Contact contact;
    FragmentContactDetailsBinding binding;

    public ContactDetails() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         contact = ContactDetailsArgs.fromBundle(getArguments())
                .getContact();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=FragmentContactDetailsBinding.bind(view);

        setContactDetails();
    }

    private void setContactDetails() {
        Glide.with(requireView()).load(contact.getImage()).into(binding.contactImg);
        binding.contactName.setText(contact.getName());
        binding.contactNumber.setText(contact.getPhone());
        binding.contactNumberWhatsapp.setText(contact.getPhone());
        binding.contactNumberTelegram.setText(contact.getPhone());
        binding.contactNumberMessenger.setText(contact.getPhone());
    }
}