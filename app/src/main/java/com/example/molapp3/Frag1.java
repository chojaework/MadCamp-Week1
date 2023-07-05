package com.example.molapp3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Frag1 extends Fragment implements ContactAdapter.OnItemClickListener {
    private static final String FILE_NAME = "contacts.json";

    private Button addButton;
    private RecyclerView recyclerView;

    private List<Contact> contactList;
    private ContactAdapter contactAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_frag1, container, false);

        addButton = fragmentView.findViewById(R.id.addButton);
        recyclerView = fragmentView.findViewById(R.id.recyclerView);

        contactList = new ArrayList<>();
        readContactsFromJson();
        contactAdapter = new ContactAdapter(contactList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(contactAdapter);

        addButton.setOnClickListener(v -> openEditContactActivity(null));

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        readContactsFromJson(); // contacts.json 파일 읽기
        contactAdapter.notifyDataSetChanged(); // 어댑터에 변경 알림
    }

    private void openEditContactActivity(Contact contact) {
        Intent intent = new Intent(requireContext(), EditContactActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }

    private void readContactsFromJson() {
        try {
            File file = new File(requireContext().getFilesDir(), FILE_NAME);

            if (!file.exists()) {
                // 파일이 존재하지 않으면 새로운 파일 생성
                boolean created = file.createNewFile();
                if (!created) {
                    showToast(requireContext(), "연락처 데이터를 저장할 파일을 생성하는 중 오류가 발생했습니다.");
                    return;
                }
            }

            FileInputStream fis = new FileInputStream(file);

            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            contactList.clear();

            if (buffer.length == 0) {
                // 파일이 비어있는 경우 연락처 데이터를 추가하여 파일에 저장

                contactList.add(new Contact(0, "이현수", "1234567890"));
                contactList.add(new Contact(1, "조재원", "9876543210"));
                saveContactsToJson(requireContext());
            } else {
                String json = new String(buffer, StandardCharsets.UTF_8);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Contact>>() {}.getType();
                contactList.addAll(gson.fromJson(json, listType));
            }
        } catch (IOException e) {
            e.printStackTrace();
            showToast(requireContext(), "연락처 데이터를 파일에서 읽어오는 중 오류가 발생했습니다.");
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
            showToast(context, "연락처 데이터를 파일에 저장하는 중 오류가 발생했습니다.");
        }
    }

    private void openContactDetailActivity(Contact contact) {
        Intent intent = new Intent(requireContext(), ContactDetailActivity.class);
        intent.putExtra("contact", contact);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Contact contact = contactList.get(position);
        openContactDetailActivity(contact);
    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}