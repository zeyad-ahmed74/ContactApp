package com.example.contacts.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.contacts.R;
import com.example.contacts.data.model.Contact;
import com.example.contacts.data.source.ContactDataBase;
import com.example.contacts.databinding.FragmentContactsBinding;
import com.example.contacts.ui.adapter.ContactAdapter;
import com.example.contacts.ui.adapter.ContactClickListener;

import java.util.List;


public class Contacts extends Fragment implements ContactClickListener {

    FragmentContactsBinding binding;
    ContactAdapter contactAdapter;
    ActionMode actionMode;
    Contact contact;
    List<Contact> contacts;

    public Contacts() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentContactsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    private void InitRecycler() {
        contactAdapter = new ContactAdapter(this);
        binding.ContactRecycler.setAdapter(contactAdapter);
        binding.ContactRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InitRecycler();
        setListeners();
        fetchContacts();


    }

    private void setListeners() {
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.main_container,new AddContact())
//                        .addToBackStack("ss")
//                        .commit();

                Navigation.findNavController(v)
                        .navigate(R.id.action_contacts_to_addContact);
            }
        });
    }

    private void fetchContacts() {
        contacts = ContactDataBase.getContactDataBase(requireContext()).contactDao()
                .getContacts();
        Log.d("qqqqq",""+contacts);
        if(contacts !=null && !contacts.isEmpty()){
            contactAdapter.addContact(contacts);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    @Override
    public void OnItemClick(Contact contact) {
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new ContactDetails())
//                .addToBackStack("aa")
//                .commit();
//        Navigation.findNavController(requireView())
//                .navigate(R.id.action_contacts_to_contactDetails);

        Navigation.findNavController(requireView())
                .navigate(ContactsDirections.actionContactsToContactDetails(contact));
    }

    @Override
    public void OnItemLongClick(Contact contact) {
        this.contact=contact;

        if(actionMode==null){
            actionMode= getActivity().startActionMode(callback);
        }

    }

    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getActivity().getMenuInflater().inflate(R.menu.context_menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.delete:
                    ContactDataBase.getContactDataBase(requireContext())
                            .contactDao().deleteContact(contact.getId());
                    mode.finish();
                    RenderView();
                    return true;
                case R.id.update:
                    Navigation.findNavController(requireView())
                                    .navigate(ContactsDirections.actionContactsToUpdateContact(contact));
                    mode.finish();
                    return true;
            }
            return false;
        }


        @Override
        public void onDestroyActionMode(ActionMode mode) {
             actionMode=null;
        }
    };

    private void RenderView() {
        contacts = ContactDataBase.getContactDataBase(requireContext()).contactDao()
                .getContacts();
        if(contacts !=null && !contacts.isEmpty()){
            contactAdapter.addContact(contacts);
        }
    }
}