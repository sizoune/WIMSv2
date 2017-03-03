package com.example.pattimura.wims.Adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pattimura.wims.Model.ListChat;
import com.example.pattimura.wims.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by desmoncode on 03/03/17.
 */

public class AdapterListChat extends BaseAdapter{
    ArrayList<ListChat> daftarChat;
    Context context;

    public AdapterListChat(ArrayList<ListChat> daftarChat, Context context) {
        this.daftarChat = daftarChat;
        this.context = context;
    }


    @Override
    public int getCount() {
        return daftarChat.size();
    }

    @Override
    public Object getItem(int i) {
        return daftarChat.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;

        ListChat lc = daftarChat.get(i);
        int type = getItemViewType(i);

        if(lc.getStatus().equals("user")) {
            v = inflater.inflate(R.layout.listpesan_personal, viewGroup, false);
            ImageView ava = (ImageView) v.findViewById(R.id.img_avatar_personal);
            TextView nama = (TextView) v.findViewById(R.id.text_view_display_name_personal);
            TextView msg = (TextView) v.findViewById(R.id.text_view_display_chat_personal);
            TextView wkt = (TextView) v.findViewById(R.id.text_view_time_personal);
            Picasso.with(v.getContext()).load(R.drawable.avatar).into(ava);
            nama.setText(lc.getDisplayName());
            msg.setText(lc.getMessage());
            wkt.setText(DateFormat.format("HH:mm",lc.getTime()));
        }else{
            v = inflater.inflate(R.layout.listpesan_grup, viewGroup, false);
            ImageView ava = (ImageView) v.findViewById(R.id.img_avatar_grup);
            TextView nama = (TextView) v.findViewById(R.id.text_view_display_name_grup);
            TextView msg = (TextView) v.findViewById(R.id.text_view_display_chat_grup);
            TextView wkt = (TextView) v.findViewById(R.id.text_view_time_grup);
            Picasso.with(v.getContext()).load(R.drawable.avatar).into(ava);
            nama.setText(lc.getDisplayName());
            msg.setText(lc.getMessage());
            wkt.setText(DateFormat.format("HH:mm",lc.getTime()));
        }

        return v;
    }
}
