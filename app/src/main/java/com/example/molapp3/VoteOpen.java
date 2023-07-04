package com.example.molapp3;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class VoteOpen extends Fragment {
    Button btn_back2;
    static ArrayList<Uri> addedImages2 = new ArrayList<>();
    static ArrayList<String> addedMenu2 = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vote_open, container, false);
        btn_back2 = rootView.findViewById(R.id.btn_back2);
        btn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().onBackPressed();
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }
}