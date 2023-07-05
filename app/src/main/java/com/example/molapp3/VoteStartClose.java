package com.example.molapp3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class VoteStartClose extends Fragment {
    ArrayList<VoteClose.ListItem> arrayList = null;
    private Uri uriImg;
    private ListViewAdapter_start adapter;
    private ListView listView;
//    private VoteClose.ListViewAdapter adapter;
    private int[] clickCount;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_vote_start_close, container, false);
        Bundle bundle = getArguments();

        if (bundle != null) {
            arrayList = bundle.getParcelableArrayList("arrayList");
        }

        clickCount = new int[arrayList.size()];

        Log.d("ArrayListText",arrayList.get(0).getText());
        Log.d("ArrayListImageUri", arrayList.get(0).getImgUri());
        Log.d("ArrayList", String.valueOf(arrayList));
        adapter = new ListViewAdapter_start(requireContext(), arrayList);
        listView = rootView.findViewById(R.id.lv_cand1);
        listView.setAdapter(adapter);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("투표 확인")
                    .setMessage("투표하시겠습니까?")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            clickCount[position]++;
                            Toast.makeText(requireContext(), "투표되었습니다", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(requireContext(), "클릭 횟수: " + clickCount[position], Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 변화 x
                        }
                    })
                        .show();
            }
        });

        Button btn_end = rootView.findViewById(R.id.btn_endVote1);
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new VoteResultClose();
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("arrayList", (ArrayList<? extends Parcelable>) arrayList);
                bundle.putIntArray("clickCountArray", clickCount);
                newFragment.setArguments(bundle);

                transaction.replace(R.id.fragmentContainerView, newFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }
    public class ListViewAdapter_start extends BaseAdapter {
        private ArrayList<VoteClose.ListItem> list;
        private Context context;

        public ListViewAdapter_start(Context context, ArrayList<VoteClose.ListItem> list) {
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
            View view = LayoutInflater.from(context).inflate(R.layout.listview_close_item, null);
            ImageView iv = view.findViewById(R.id.iv_lv_close);
//            Glide.with(view).load(list.get(position).getImgUri()).into(iv);
            try {
                uriImg = Uri.parse(arrayList.get(position).getImgUri());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriImg);
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
                iv.setImageBitmap(thumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }
            TextView tv = view.findViewById(R.id.tv_lv_close);
//            tv.setText((CharSequence) arrayList.get(position));
//            tv.setText("Index"+position);
            tv.setText(arrayList.get(position).getText());
//            arrayList.get(position).text
            //ListView의 텍스트를 setText해야 한다
//            tv.setText();
            return view;
        }


    }
}