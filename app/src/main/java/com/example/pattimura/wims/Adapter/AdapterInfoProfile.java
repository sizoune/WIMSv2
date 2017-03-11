package com.example.pattimura.wims.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.pattimura.wims.Model.BagianUser;
import com.example.pattimura.wims.R;

import java.util.ArrayList;

/**
 * Created by wildan on 11/03/17.
 */

public class AdapterInfoProfile extends BaseAdapter {
    private ArrayList<BagianUser> bu;
    private Context context;

    public AdapterInfoProfile(ArrayList<BagianUser> bu, Context context) {
        this.bu = bu;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bu.size();
    }

    @Override
    public Object getItem(int position) {
        return bu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = convertView;
        v = inflater.inflate(R.layout.listrowinfo, parent, false);

        BagianUser user = bu.get(position);

        TextView isi = (TextView) v.findViewById(R.id.textisiInfo);
        isi.setText(user.getIsi());

        return v;
    }
}
