package com.example.pattimura.wims.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pattimura.wims.Model.ChatGrup;
import com.example.pattimura.wims.Model.ChatPersonal;
import com.example.pattimura.wims.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by desmoncode on 11/03/17.
 */

public class AdapterChatGrup extends BaseAdapter {
    ArrayList<ChatGrup> cp;
    Context context;
    FirebaseUser user;
    StorageReference mStorageRef;


    public AdapterChatGrup(ArrayList<ChatGrup> cp, Context context, FirebaseUser user) {
        this.cp = cp;
        this.context = context;
        this.user = user;
        //Toast.makeText(context, "masuk ayam goreng enak songko", Toast.LENGTH_SHORT).show();
        //Toast.makeText(context, cp.get(0).getNama(), Toast.LENGTH_SHORT).show();

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
        View v;
        ChatGrup chat = cp.get(i);

        //Toast.makeText(context, "masuk ayam goreng", Toast.LENGTH_SHORT).show();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (chat.getId().equals(user.getUid())) {
            v = inflater.inflate(R.layout.adapter_sender, viewGroup, false);
            //ImageView gambar = (ImageView) v.findViewById(R.id.imageView);
            TextView isi = (TextView) v.findViewById(R.id.message_text);
            TextView time = (TextView) v.findViewById(R.id.message_time);
            isi.setText(chat.getMessage());
            time.setText(chat.getFormattedTime());
        } else {
            v = inflater.inflate(R.layout.adapter_recipient_grup, viewGroup, false);
            ImageView gambar = (ImageView) v.findViewById(R.id.imageView2);
            TextView isi = (TextView) v.findViewById(R.id.message_text);
            TextView time = (TextView) v.findViewById(R.id.message_time);
            TextView nama = (TextView) v.findViewById(R.id.message_user);
            isi.setText(chat.getMessage());
            time.setText(chat.getFormattedTime());
            nama.setText(chat.getNama());

        }

        return v;
    }
/*
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
    public View getView(int i, View view, ViewGroup parent) {
        View v = view;
        ChatGrup chat = cp.get(i);
        Toast.makeText(context, "masuk ayam goreng enak", Toast.LENGTH_SHORT).show();
        if (v == null) {
            Toast.makeText(context, "masuk ayam goreng", Toast.LENGTH_SHORT).show();
            mStorageRef = FirebaseStorage.getInstance().getReference();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (chat.getId().equals(user.getUid())) {
                v = inflater.inflate(R.layout.adapter_sender, parent, false);
                //ImageView gambar = (ImageView) v.findViewById(R.id.imageView);
                TextView isi = (TextView) v.findViewById(R.id.message_text);
                TextView time = (TextView) v.findViewById(R.id.message_time);
                isi.setText(chat.getMessage());
                time.setText(chat.getFormattedTime());
            } else {
                v = inflater.inflate(R.layout.adapter_recipient_grup, parent, false);
                ImageView gambar = (ImageView) v.findViewById(R.id.imageView2);
                TextView isi = (TextView) v.findViewById(R.id.message_text);
                TextView time = (TextView) v.findViewById(R.id.message_time);
                TextView nama = (TextView) v.findViewById(R.id.message_user);
                isi.setText(chat.getMessage());
                time.setText(chat.getFormattedTime());
                nama.setText(chat.getNama());

            }
        }
        return v;
    }*/
}
