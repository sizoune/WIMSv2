package com.example.pattimura.wims.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pattimura.wims.Model.ChatPersonal;
import com.example.pattimura.wims.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by desmoncode on 04/03/17.
 */

public class AdapterChatPersonal extends BaseAdapter {
    ArrayList<ChatPersonal> cp;
    Context context;
    //FirebaseUser user;
    //StorageReference mStorageRef;


    public AdapterChatPersonal(ArrayList<ChatPersonal> cp, Context context, FirebaseUser user) {
        this.cp = cp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cp.size();
    }

    @Override
    public Object getItem(int i) {
        return cp.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ChatPersonal chat = cp.get(i);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.adapter_sender, viewGroup, false);
            TextView isi = (TextView) v.findViewById(R.id.message_text);
            TextView time = (TextView) v.findViewById(R.id.message_time);
            isi.setText(chat.getMessage());
            time.setText(DateFormat.format("(HH:mm:ss)", chat.getWaktu()));
            /*mStorageRef = FirebaseStorage.getInst ance().getReference();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (chat.getId().equals(user.getUid())) {
                v = inflater.inflate(R.layout.adapter_text_mine, parent, false);
                ImageView gambar = (ImageView) v.findViewById(R.id.imageView);
                TextView isi = (TextView) v.findViewById(R.id.message_text);
                TextView time = (TextView) v.findViewById(R.id.message_time);
                isi.setText(chat.getMessage());
                time.setText(DateFormat.format("(HH:mm:ss)", chat.getWaktu()));
            } else {
                v = inflater.inflate(R.layout.adapter_text, parent, false);
                ImageView gambar = (ImageView) v.findViewById(R.id.imageView2);
                TextView isi = (TextView) v.findViewById(R.id.message_text);
                TextView time = (TextView) v.findViewById(R.id.message_time);
                TextView nama = (TextView) v.findViewById(R.id.message_user1);
                isi.setText(chat.getMessage());
                time.setText(DateFormat.format("(HH:mm:ss)", chat.getWaktu()));
                nama.setText(chat.getDisplayname());
                if (!chat.getUrlgambar().equals("")) {
                    StorageReference photoRef = mStorageRef.child(chat.getUrlgambar());
                    Glide.with(context)
                            .using(new FirebaseImageLoader())
                            .load(photoRef)
                            .override(40, 40)
                            .into(gambar);
                }
            }*/
        }
        return v;
    }
}
