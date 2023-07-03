package com.example.molapp3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Frag1 extends Fragment implements ContactAdapter.OnItemClickListener {

    private static final int REQUEST_PERMISSION = 1;
    private static final String FILE_NAME = "contacts.json";

    private EditText nameEditText;
    private EditText phoneEditText;
    private Button addButton;
    private Button updateButton;
    private Button deleteButton;
    private RecyclerView recyclerView;

    private List<Contact> contactList;
    private ContactAdapter contactAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag1, container, false);

        nameEditText = view.findViewById(R.id.nameEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        addButton = view.findViewById(R.id.addButton);
        updateButton = view.findViewById(R.id.modifyButton);
        deleteButton = view.findViewById(R.id.deleteButton);
        recyclerView = view.findViewById(R.id.recyclerView);

        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contactList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(contactAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                if (!name.isEmpty() && !phone.isEmpty()) {
                    Contact contact = new Contact(name, phone);
                    contactList.add(contact);
                    saveContactsToJson();
                    contactAdapter.notifyDataSetChanged();
                    clearInputFields();
                    showToast("Contact Added");
                } else {
                    showToast("Please enter name and phone number");
                }
            }
        });

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {
            performFileOperations();
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = contactAdapter.getSelectedPosition();
                if (position != RecyclerView.NO_POSITION) {
                    String name = nameEditText.getText().toString();
                    String phone = phoneEditText.getText().toString();
                    if (!name.isEmpty() && !phone.isEmpty()) {
                        Contact contact = contactList.get(position);
                        contact.setName(name);
                        contact.setPhone(phone);
                        saveContactsToJson();
                        contactAdapter.notifyDataSetChanged();
                        clearInputFields();
                        showToast("Contact Updated");
                    } else {
                        showToast("Please enter name and phone number");
                    }
                } else {
                    showToast("Please select a contact to update");
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = contactAdapter.getSelectedPosition();
                if (position != RecyclerView.NO_POSITION) {
                    contactList.remove(position);
                    saveContactsToJson();
                    contactAdapter.notifyDataSetChanged();
                    clearInputFields();
                    showToast("Contact Deleted");
                } else {
                    showToast("Please select a contact to delete");
                }
            }
        });

        return view;
    }

    private void performFileOperations() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File file = new File(getContext().getExternalFilesDir(null), FILE_NAME);
            if (file.exists()) {
                readContactsFromJson();
            } else {
                createInitialContacts();
            }
        } else {
            showToast("External storage not available");
        }
    }

    private void readContactsFromJson() {
        try {
            File file = new File(getContext().getExternalFilesDir(null), FILE_NAME);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            String json = new String(data, "UTF-8");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Contact>>() {}.getType();
            contactList = gson.fromJson(json, listType);
            contactAdapter.setContacts(contactList);
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Error reading contacts from file");
        }
    }

    private void createInitialContacts() {
        contactList.add(new Contact("이현수", "1234567890"));
        contactList.add(new Contact("조재원", "9876543210"));
        saveContactsToJson();
        contactAdapter.setContacts(contactList);
    }

    private void saveContactsToJson() {
        try {
            File file = new File(getContext().getExternalFilesDir(null), FILE_NAME);
            FileOutputStream fos = new FileOutputStream(file);
            Gson gson = new Gson();
            String json = gson.toJson(contactList);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Error saving contacts to file");
        }
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void clearInputFields() {
        nameEditText.setText("");
        phoneEditText.setText("");
    }

    @Override
    public void onItemClick(int position) {
        Contact contact = contactList.get(position);
        nameEditText.setText(contact.getName());
        phoneEditText.setText(contact.getPhone());
    }

    @Override
    public void onItemLongClick(int position) {
        // Handle long click event if needed
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                performFileOperations();
            } else {
                showToast("Permission denied");
            }
        }
    }
}
