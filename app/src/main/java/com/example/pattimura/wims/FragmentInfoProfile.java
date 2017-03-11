package com.example.pattimura.wims;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pattimura.wims.Adapter.AdapterInfoProfile;
import com.example.pattimura.wims.Model.BagianUser;
import com.example.pattimura.wims.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentInfoProfile extends Fragment {
    ArrayList<BagianUser> bu = new ArrayList<>();
    ListView listInfo;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    AdapterInfoProfile adapter;

    public FragmentInfoProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_info, container, false);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        listInfo = (ListView) v.findViewById(R.id.listInfo);

        database.getReference("profil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    if (user.getId().equals(mAuth.getCurrentUser().getUid())) {
                        bu.clear();
                        bu.add(new BagianUser("sd", user.getSd()));
                        bu.add(new BagianUser("smp", user.getSmp()));
                        bu.add(new BagianUser("sma", user.getSma()));
                        bu.add(new BagianUser("kuliah", user.getKuliah()));
                        bu.add(new BagianUser("kerja", user.getKerja()));
                        adapter = new AdapterInfoProfile(bu, FragmentInfoProfile.this.getContext());
                        listInfo.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return v;
    }

}
