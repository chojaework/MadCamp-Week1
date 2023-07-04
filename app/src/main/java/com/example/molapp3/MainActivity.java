package com.example.molapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack(); // 이전 Fragment로 이동
        } else {
            super.onBackPressed(); // 기본 뒤로가기 동작 수행
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        Button btn_contact = findViewById(R.id.btn_contact);
        Button btn_image = findViewById(R.id.btn_image);
        Button btn_vote = findViewById(R.id.btn_vote);
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_contact

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, Frag1.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });


        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, Frag2.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });


        btn_vote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, Frag3.class, null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
    }
}