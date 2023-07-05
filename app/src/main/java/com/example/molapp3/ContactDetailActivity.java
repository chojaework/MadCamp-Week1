package com.example.molapp3;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
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

public class ContactDetailActivity extends AppCompatActivity {
    private static final String FILE_NAME = "contacts.json";

    private TextView nameTextView;
    private TextView phoneTextView;
    private Button editButton, deleteButton, backButton, callButton, msgButton;


    private Contact contact;
    private List<Contact> contactList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        nameTextView = findViewById(R.id.nameTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);
        backButton = findViewById(R.id.backButton);
        callButton = findViewById(R.id.callButton);
        msgButton = findViewById(R.id.msgButton);

        contactList = new ArrayList<>();
        try {
            readContactsFromJson();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("contact")) {
            contact = (Contact) intent.getSerializableExtra("contact");
            if (contact != null) {
                nameTextView.setText(contact.getName());
                phoneTextView.setText(contact.getPhone());
            }
        }
        editButton.setOnClickListener(v -> openEditContactActivity(contact));
        deleteButton.setOnClickListener(v -> showDeleteConfirmationDialog());
        backButton.setOnClickListener(v -> { finish();});
        callButton.setOnClickListener(v -> {
            String phoneNumber = phoneTextView.getText().toString();
            Intent tIntent = new Intent(Intent.ACTION_DIAL);
            tIntent.setData(Uri.parse("tel:" + phoneNumber));
            startActivity(tIntent);
        });
        msgButton.setOnClickListener(v -> {
            String phoneNumber = phoneTextView.getText().toString();
            Uri uri = Uri.parse("smsto:" + phoneNumber);
            Intent mIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(mIntent);
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        try {
            readContactsFromJson(); // contacts.json 파일 읽기
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // contactList에서 현재 contact의 정보를 다시 가져옴
        int contactId = contact.getId();
        for (Contact c : contactList) {
            if (c.getId() == contactId) {
                contact = c;
                break;
            }
        }

        // 변경된 contact 정보로 TextView 갱신
        if (contact != null) {
            nameTextView.setText(contact.getName());
            phoneTextView.setText(contact.getPhone());
        }
    }

    private void openEditContactActivity(Contact contact) {
        Intent intent = new Intent(ContactDetailActivity.this, EditContactActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
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
    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("연락처 삭제");
        builder.setMessage("정말로 연락처를 삭제하시겠습니까?");
        builder.setPositiveButton("삭제", (dialog, which) -> deleteContact());
        builder.setNegativeButton("취소", null);
        builder.show();
    }

    private void deleteContact() {
        // 연락처를 삭제하는 작업을 수행합니다.
        int start = contact.getId();
        Log.d("delete_id value", String.valueOf(start));
        contactList.remove(contact);
        for (int i = start; i < contactList.size(); i++) {
            Contact contact = contactList.get(i);
            contact.setId(contact.getId() - 1);
        }
        // 변경된 연락처 목록을 파일에 저장합니다.
        saveContactsToJson(this);

        // 삭제 완료 메시지를 표시합니다.
        Toast.makeText(this, "연락처가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

        // 현재 액티비티를 종료합니다.
        finish();
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
}