package com.example.molapp3;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//public class VoteClose extends Fragment {
//    ImageView imageView;
//    Button btn_back1;
//    Button btn_gallery1;
//    Button btn_done1;
//    static ArrayList<Uri> addedImages1 = new ArrayList<>();
//    static ArrayList<String> addedMenu1 = new ArrayList<>();
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_vote_close, container, false);
//        btn_back1 = rootView.findViewById(R.id.btn_back1);
//        btn_back1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requireActivity().onBackPressed();
//            }
//        });
//
//        btn_done1 = rootView.findViewById(R.id.btn_done1);
////        btn_done1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////            }
////        });
//        imageView = rootView.findViewById(R.id.iv_1);
////        btn_gallery1 = rootView.findViewById(R.id.btn_gallery1);
////        btn_gallery1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(Intent.ACTION_PICK);
////                intent.setType("image/*");
////                galleryLauncher.launch(intent);
////            }
////        });
//
//        // Inflate the layout for this fragment
//        return rootView;
//    }
//
////    ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
////            new ActivityResultContracts.StartActivityForResult(),
////            new ActivityResultCallback<ActivityResult>() {
////                @Override
////                public void onActivityResult(ActivityResult result) {
////                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
////                        Uri uri = result.getData().getData();
////                        addedImages1.add(uri);
//////                        MyAdapter1.notifyItemInserted(int position);
////                        System.out.println("클릭한 이미지의 uri: "+uri); // uri 제대로 추출됨
////                        Log.d("ArrayList", String.valueOf(addedImages1)); // arraylist에 uri 제대로 저장됨
////
////                        // 앨범에서 선택한 이미지 아래에 띄우기
////                        try {
////                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
////                            imageView.setImageBitmap(bitmap);
////                            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
////                            imageView.setImageBitmap(thumbnail);
////                        } catch (FileNotFoundException e) {
////                            e.printStackTrace();
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                    }
////                }
////            }
////    );
//
////    public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder1> {
////
////        @Override
////        public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
////            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
////            View view = inflater.inflate(R.layout.fragment_vote_close, parent, false);
////            return new MyViewHolder1(view);
////        }
////
////        @Override
////        public void onBindViewHolder(MyViewHolder1 holder, int position) {
////            String data = addedMenu1.get(position);
////            holder.bindData(data);
////        }
////
////        @Override
////        public int getItemCount() {
////            return addedMenu1.size();
////        }
////
////        public class MyViewHolder1 extends RecyclerView.ViewHolder {
////            private TextView textView;
////            public MyViewHolder1(View itemView) {
////                super(itemView);
////                textView = itemView.findViewById(R.id.tv1);
////            }
////            public void bindData(String data) {
////                textView.setText(data);
////            }
////        }
////    }
//}
public class VoteClose extends Fragment {
    Button btn_back2;
    static ArrayList<Uri> addedImages2 = new ArrayList<>();
    static ArrayList<String> addedMenu2 = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vote_close, container, false);
        btn_back2 = rootView.findViewById(R.id.btn_back1);
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