package com.example.molapp3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class VoteStartOpen extends Fragment {
    ArrayList<VoteClose.ListItem> arrayList = null;
    private Uri uriImg;
    private ListViewAdapter_start adapter;
    private ListView listView;
    //    private VoteClose.ListViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_vote_start_open, container, false);
        Bundle bundle = getArguments();

        if (bundle != null) {
            arrayList = bundle.getParcelableArrayList("arrayList");
        }
        Log.d("ArrayListText",arrayList.get(0).getText());
        Log.d("ArrayListImageUri", arrayList.get(0).getImgUri());
        Log.d("ArrayList", String.valueOf(arrayList));
        adapter = new ListViewAdapter_start(requireContext(), arrayList);
        listView = rootView.findViewById(R.id.lv_cand2);
        listView.setAdapter(adapter);
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
            View view = LayoutInflater.from(context).inflate(R.layout.listview_open_item, null);
            ImageView iv = view.findViewById(R.id.iv_lv_open);
//            Glide.with(view).load(list.get(position).getImgUri()).into(iv);
            try {
                uriImg = Uri.parse(arrayList.get(position).getImgUri());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriImg);
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
                iv.setImageBitmap(thumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }
            TextView tv = view.findViewById(R.id.tv_lv_open);
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