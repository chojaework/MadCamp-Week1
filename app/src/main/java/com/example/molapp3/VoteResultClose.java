package com.example.molapp3;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class VoteResultClose extends Fragment {

    ArrayList<VoteClose.ListItem> arrayList = null;
    private int[] clickCount;
    int sum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vote_result_close, container, false);
        Bundle bundle = getArguments();

        if (bundle != null) {
            arrayList = bundle.getParcelableArrayList("arrayList");
            clickCount = bundle.getIntArray("clickCountArray");
        }
        ImageView imageView = rootView.findViewById(R.id.iv_result_close);
        TextView textView = rootView.findViewById(R.id.tv_result_close);
        TextView textView_count = rootView.findViewById(R.id.tv_num_close);
//        Button button = rootView.findViewById(R.id.btn_return_close);


        int top = 0;
        for(int i = 0; i < clickCount.length; i++) {
            sum = sum + clickCount[i];
            if(clickCount[top] < clickCount[i]) top = i;
        }

        Uri uri = Uri.parse(arrayList.get(top).getImgUri());
        String str = arrayList.get(top).getText();

        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), uri);
            imageView.setImageBitmap(bitmap);
//            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
//            imageView.setImageBitmap(thumbnail);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textView.setText(str);
        textView_count.setText(String.valueOf(clickCount[top]) + "표");
        TextView textView_total = rootView.findViewById(R.id.tv_sum_close);
        textView_total.setText("(전체: "+ String.valueOf(sum) + " 표)");
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 첫 화면으로 돌아가기
//            }
//        });

        // Inflate the layout for this fragment
        return rootView;
    }
}