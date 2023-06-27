package com.example.contacts.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavArgument;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.contacts.R;
import com.example.contacts.data.model.Contact;
import com.example.contacts.data.source.ContactDataBase;
import com.example.contacts.databinding.FragmentUpdateContactBinding;


public class UpdateContact extends Fragment {


     FragmentUpdateContactBinding binding;
     Contact contact;
    private String img_uri;

    public UpdateContact() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contact=UpdateContactArgs.fromBundle(getArguments())
                .getContact();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update__contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding=FragmentUpdateContactBinding.bind(view);

        Glide.with(requireView()).load(contact.getImage()).into(binding.ContactImg);
        binding.AddNameTxt.getEditText().setText(contact.getName());
        binding.AddPhoneTxt.getEditText().setText(contact.getPhone());
        binding.AddJobTitleTxt.getEditText().setText(contact.getEmp().getJobTitle());
        binding.AddMailTxt.getEditText().setText(contact.getEmp().getE_mail());
        onClick();

    }

    private void updateContact() {

        if(!isImageValid(img_uri)) {
            Toast.makeText(requireContext(), "Select Image", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isNameValid(binding.AddNameTxt.getEditText())){
            binding.AddNameTxt.getEditText().setError("Name is Not Valid");
            return;
        }
        if(!isPhoneValid(binding.AddPhoneTxt.getEditText())) {
            binding.AddPhoneTxt.getEditText().setError("Phone is Not Valid");
            return;
        }

        ContactDataBase.getContactDataBase(requireContext())
                .contactDao().updateContact(contact.getId(),binding.AddNameTxt.getEditText().getText().toString()
                        ,binding.AddPhoneTxt.getEditText().getText().toString(),img_uri);

        Navigation.findNavController(requireView())
                .navigate(R.id.action_updateContact_to_contacts);
        Toast.makeText(requireContext(),"Contact Updated",Toast.LENGTH_LONG).show();

    }

    private void onClick() {
        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact();
            }
        });
        binding.ContactImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkStoragePermission();
            }
        });
    }

    private ActivityResultLauncher<String> storagePermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission()
            , isGranted ->{
                if(isGranted){
                    getImageFromGallery();
                }else{
                    Toast.makeText(requireContext(), "Need This Permission", Toast.LENGTH_SHORT).show();
                }
            });
    private ActivityResultLauncher<String> ImageLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    img_uri=uri.toString();
                    binding.ContactImg.setImageURI(uri);
                }
            });

    private boolean isImageValid(String uri){
        if(uri==null){
            return false;
        }
        return true;
    }

    private void checkStoragePermission(){
        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED)
            getImageFromGallery();
        else if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE))
            showUIDialogue("Need This Permission");
        else
            storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void showUIDialogue(String Msg) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
        alert.setMessage(Msg);
        alert.setIcon(R.drawable.ic_storage);
        alert.setTitle("Storage Permission");
        alert.setCancelable(false);
        alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                storagePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        });
        alert.show();
    }

    private void getImageFromGallery() {
        ImageLauncher.launch("image/*");
    }

    private boolean isPhoneValid(EditText editText){
        if(!isNotEmpty(editText))
            return false;
        if(editText.getText().toString().length() != 11)
            return false;
        return true;
    }

    private boolean isNameValid(EditText editText){
        if(!isNotEmpty(editText))
            return false;
        return true;
    }

    private boolean isNotEmpty(EditText editText){
        if(editText.getText().toString().isEmpty())
            return false;
        return true;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }


}