package com.example.pattimura.wims.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pattimura.wims.Model.BagianUser;
import com.example.pattimura.wims.R;

import java.util.ArrayList;

/**
 * Created by wildan on 11/03/17.
 */

public class AdapterDetailProfile extends BaseAdapter {

    private ArrayList<BagianUser> daftarisiUser;
    private Context context;

    public AdapterDetailProfile(ArrayList<BagianUser> daftarisiUser, Context context) {
        this.daftarisiUser = daftarisiUser;
        this.context = context;
    }

    @Override
    public int getCount() {
        return daftarisiUser.size();
    }

    @Override
    public Object getItem(int position) {
        return daftarisiUser.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = convertView;
        v = inflater.inflate(R.layout.listrowdetailprofil, parent, false);
        BagianUser bu = daftarisiUser.get(position);
        TextView judul = (TextView) v.findViewById(R.id.textJudulDetail);
        TextView isi = (TextView) v.findViewById(R.id.textIsidetail);
        ImageView tombol = (ImageView) v.findViewById(R.id.imageEdit);

        judul.setText(bu.getJudul());
        isi.setText(bu.getIsi());

        return v;
    }
}
