package com.example.molapp3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class VoteClose extends Fragment {
    ImageView imageView1;
    private ArrayList<ListItem> arrayList;
    private Uri uriImg;
//    private String text;
    private ListViewAdapter adapter;
    private EditText editText;
    private ListView listView;
//    static private ArrayList<String> itemList = new ArrayList<>();
    Button btn_back1;
//    static ArrayList<Uri> addedImages1 = new ArrayList<>();
//    static ArrayList<String> addedMenu1 = new ArrayList<>();
//    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        arrayList = new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_vote_close, container, false);
        // 뒤로가기 버튼 클릭 시
//        btn_back1 = rootView.findViewById(R.id.btn_back1);
//        btn_back1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                requireActivity().onBackPressed();
//            }
//        });

        editText = rootView.findViewById(R.id.et_1);
        listView = rootView.findViewById(R.id.lv_1);
        Button addButton = rootView.findViewById(R.id.btn_add1);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, itemList);
//        adapter = new ListViewAdapter(requireContext(), arrayList);
//        listView.setAdapter(adapter);

        Button galleryButton = rootView.findViewById(R.id.btn_gallery1);
        imageView1 = rootView.findViewById(R.id.iv_close1);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                galleryLauncher1.launch(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString().trim();
//                text = input;
                if(!input.isEmpty()) {
                    arrayList.add(new ListItem(input, uriImg.toString()));
//                    itemList.add(input);
                    adapter.notifyDataSetChanged();
//                    text = String.valueOf(editText.getText());
                    editText.getText().clear();
                    imageView1.setImageDrawable(null);
                }
            }
        });
        Button btn_done = rootView.findViewById(R.id.btn_done1);
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new VoteStartClose();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("arrayList", (ArrayList<? extends Parcelable>) arrayList);
                newFragment.setArguments(bundle);

                transaction.replace(R.id.fragmentContainerView, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        adapter = new ListViewAdapter(requireContext(), arrayList);
        listView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    ActivityResultLauncher<Intent> galleryLauncher1 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri uri = result.getData().getData();
                        uriImg = uri;
//                        addedImages1.add(uri);
                        adapter.notifyDataSetChanged();
                        System.out.println("클릭한 이미지의 uri: "+uri); // uri 제대로 추출됨
//                        Log.d("ArrayList", String.valueOf(addedImages1)); // arraylist에 uri 제대로 저장됨

//                        // 앨범에서 선택한 이미지 아래에 띄우기
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
                            imageView1.setImageBitmap(bitmap);
                            Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
                            imageView1.setImageBitmap(thumbnail);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
    );

    public class ListViewAdapter extends BaseAdapter {
        private ArrayList<ListItem> list;
        private Context context;

        public  ListViewAdapter(Context context, ArrayList<ListItem> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder")
            View view = LayoutInflater.from(context).inflate(R.layout.listview_close_item,  null);
            ImageView iv = view.findViewById(R.id.iv_lv_close);
//            Glide.with(view).load(list.get(position).getImgUri()).into(iv);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriImg);
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
                iv.setImageBitmap(thumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }
            TextView tv = view.findViewById(R.id.tv_lv_close);
//            tv.setText((CharSequence) arrayList.get(position));
//            tv.setText("Index"+position);
            tv.setText(arrayList.get(position).text);
//            arrayList.get(position).text
            //ListView의 텍스트를 setText해야 한다
//            tv.setText();
            return view;
        }


    }

    public class ListItem implements Parcelable{
        private String text;
        private String imgUri;
        public ListItem(String text, String imgUri) {
            this.text = text;
            this.imgUri = imgUri;
        }

        protected ListItem(Parcel in) {
            text = in.readString();
            imgUri = in.readParcelable(Uri.class.getClassLoader());
        }
        public final Creator<ListItem> CREATOR = new Creator<ListItem>() {
            @Override
            public ListItem createFromParcel(Parcel in) {
                return new ListItem(in);
            }

            @Override
            public ListItem[] newArray(int size) {
                return new ListItem[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(text);
            dest.writeParcelable(uriImg, flags);
        }
        public String getText() {
            return this.text;
        }
        public String getImgUri() {
            return this.imgUri;
        }
    }
}