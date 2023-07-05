package com.example.molapp3;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


public class Frag2 extends Fragment {
    //    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
    Uri uri;
    ImageView imageView;

    static ArrayList<Uri> selectedImages = new ArrayList<>();

    private MyGridAdapter gAdapter;

    private boolean isInitialized = false;

    @Override
    public void onResume() {
        super.onResume();

        System.out.printf("onResume\n");
        Log.d("ArrayList", String.valueOf(selectedImages));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = null;
        if (!isInitialized) {
            rootView = inflater.inflate(R.layout.fragment_frag2, container, false);
            final GridView gv = rootView.findViewById(R.id.gridView);
            gAdapter = new MyGridAdapter(requireContext());
            gv.setAdapter(gAdapter);

//            Button btn_album = rootView.findViewById(R.id.btn_album);
            FloatingActionButton btn_album = rootView.findViewById(R.id.fab_add);
            imageView = rootView.findViewById(R.id.imageView);

            btn_album.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    // galleryLauncher에서 갤러리가 열리고
                    // 이미지가 gridview에 추가
                    galleryLauncher.launch(intent);
                }
            });
            isInitialized = true;
        }
        return rootView;
    }

    ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        uri = result.getData().getData();
                        selectedImages.add(uri);
                        gAdapter.notifyDataSetChanged();
                        System.out.println("클릭한 이미지의 uri: "+uri); // uri 제대로 추출됨
                        Log.d("ArrayList", String.valueOf(selectedImages)); // arraylist에 uri 제대로 저장됨

//                        // 앨범에서 선택한 이미지 아래에 띄우기
//                        try {
//                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
//                            imageView.setImageBitmap(bitmap);
//                            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
//                            imageView.setImageBitmap(thumbnail);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            }
    );

    public class MyGridAdapter extends BaseAdapter {
        Context context;
        public MyGridAdapter(Context c){
            context = c;
        }
        Integer[] picID = {
                R.drawable.water,R.drawable.lip,R.drawable.cam,R.drawable.blood,R.drawable.coding
        };

        // BaseAdapter를 상속받은 클래스가 구현해야 할 함수들은
        // { getCount(), getItem(), getItemId(), getView() }
        // Ctrl + i 를 눌러 한꺼번에 구현
        @Override
        public int getCount() {
            return selectedImages.size();
//            return picID.length + selectedImages.size();
//            return picID.length;
//            return 100;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ImageView imageView;

            if (view == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(10,10,10,10);
            } else {
                imageView = (ImageView) view;
            }

            Uri uri_2 = selectedImages.get(i);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri_2);
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
                imageView.setImageBitmap(thumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // drawable 이미지를 gridview에 띄우는 코드
//            imageView.setImageResource(picID[i]);
//            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeResource(context.getResources(), picID[i]), 300, 300);
//            imageView.setImageBitmap(thumbnail);

            // 갤러리의 이미지뷰를 눌렀을 때
            // 다이얼로그뷰를 팝업하여 큰 이미지를 출력합니다.
            final int pos = i;
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View dialogView = View.inflate(requireActivity(), R.layout.dialog, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(requireActivity());
                    ImageView ivPic = dialogView.findViewById(R.id.ivPic);
                    ivPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    ivPic.setImageResource(picID[pos]);
                    Uri uri3 = selectedImages.get(pos);
                    try {
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri3);
                        // 사진 사이즈 키우는 방법
                        Bitmap bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri3));
                        ivPic.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    ivPic.setImageResource(selectedImages.get(i));

                    dlg.setTitle("큰 이미지");
                    dlg.setIcon(R.drawable.ic_launcher_foreground);
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder subDlg = new AlertDialog.Builder(context);
                            subDlg.setMessage("삭제하시겠습니까?");
                            subDlg.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // 삭제 버튼 클릭 시 수행할 로직
                                    if (pos >= 0 && pos < selectedImages.size()) {
                                        selectedImages.remove(pos);
                                        gAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                            subDlg.setNegativeButton("취소", null);
                            subDlg.show();
                        }
                    });
                    dlg.show();
                }
            });
            return imageView;
        }
    }
}