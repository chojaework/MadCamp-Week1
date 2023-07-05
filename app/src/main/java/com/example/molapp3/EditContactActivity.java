package com.example.molapp3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class EditContactActivity extends AppCompatActivity {

    private static final String FILE_NAME = "contacts.json";
    int add = 1;
    int id = 0;
    private EditText nameEditText;
    private EditText phoneEditText;
    private Button saveButton;
    private TextView et_title;
    private List<Contact> contactList;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        nameEditText = findViewById(R.id.nameEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        saveButton = findViewById(R.id.saveButton);
        et_title = findViewById(R.id.et_title);
        contactList = new ArrayList<>();
        try {
            readContactsFromJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (getIntent().hasExtra("contact")) {
            contact = (Contact) getIntent().getSerializableExtra("contact");
            if (contact != null) {
                add = 0;
                nameEditText.setText(contact.getName());
                phoneEditText.setText(contact.getPhone());
                id = contact.getId();
                et_title.setText("연락처 수정");
            } else { et_title.setText("연락처 추가"); }
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveContact();
            }
        });
    }
    private void saveContact() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "이름을 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
        } else if (phone.isEmpty()) {
            Toast.makeText(this, "전화번호를 올바르게 입력하세요.", Toast.LENGTH_SHORT).show();
        } else {
            if (contact == null) {
                id = contactList.size();
                contact = new Contact(id, name, phone);
                Log.d("add_id value", String.valueOf(id));
                contactList.add(contact);
            } else {
                contact.setId(id);
                contact.setName(name);
                contact.setPhone(phone);
                contactList.set(id, contact);
                Log.d("edit_id value", String.valueOf(id));
            }
            //contacts.json 파일에 저장.
            saveContactsToJson(this);
            finish();
        }
    }
    private void saveContactsToJson(Context context) {
        try {
            File file = new File(context.getFilesDir(), FILE_NAME);
            FileOutputStream fos = new FileOutputStream(file);
            Gson gson = new Gson();
            String json = gson.toJson(contactList);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "연락처 데이터를 파일에 저장하는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    private void readContactsFromJson() throws IOException {
        File file = new File(this.getFilesDir(), FILE_NAME);

        FileInputStream fis = new FileInputStream(file);

        int size = fis.available();
        byte[] buffer = new byte[size];
        fis.read(buffer);
        fis.close();
        String json = new String(buffer, StandardCharsets.UTF_8);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Contact>>() {
        }.getType();
        contactList.clear();
        contactList.addAll(gson.fromJson(json, listType));
    }
}