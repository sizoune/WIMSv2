package com.example.pattimura.wims;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.constraint.solver.SolverVariable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.pattimura.wims.Adapter.AdapterListChat;
import com.example.pattimura.wims.Model.ChatPersonal;
import com.example.pattimura.wims.Model.ListChat;

import com.bumptech.glide.Glide;

import com.example.pattimura.wims.Model.User;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDetailProfile extends Fragment implements View.OnClickListener {
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;
    final LandingPage activity;
    ImageView tombol, gambarprof;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    ProgressDialog mProgressDialog;
    String namaorang;
    ListChat lc;
    TextView nama;

    String idorang;
    private StorageReference mStorageRef;


    public FragmentDetailProfile() {
        // Required empty public constructor
        idorang = null;

        namaorang = null;
        activity = (LandingPage) getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detail_profile, container, false);

        tombol = (ImageView) v.findViewById(R.id.btnEditProfile);
        gambarprof = (ImageView) v.findViewById(R.id.imageViewProfile);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        nama = (TextView) v.findViewById(R.id.textnamaProfile);
        final TextView asal = (TextView) v.findViewById(R.id.textasalProfile);
        final TextView status = (TextView) v.findViewById(R.id.textstatusProfile);


        try {
            if (!getArguments().getString("idorang").equals(null)) {
                idorang = getArguments().getString("idorang");
                namaorang = getArguments().getString("namaorang");
            }
        } catch (Exception e) {

        }

        if (idorang != null) {
            tombol.setImageResource(R.drawable.message);
        }


        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        tombol.setOnClickListener(this);
        database.getReference("profil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    if (idorang == null) {
                        if (user.getId().equals(mAuth.getCurrentUser().getUid())) {
                            nama.setText(user.getNama());
                            asal.setText(user.getAsal());
                            status.setText(user.getStatus());
                        }
                    } else if (user.getId().equals(idorang)) {
                        nama.setText(user.getNama());
                        asal.setText(user.getAsal());
                        status.setText(user.getStatus());

                    }
                    if (!user.getUrlgambar().equals("")) {
                        Glide.with(FragmentDetailProfile.this.getContext())
                                .using(new FirebaseImageLoader())
                                .load(mStorageRef.child(user.getUrlgambar()))
                                .dontAnimate()
                                .into(gambarprof);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v == tombol) {
            showProgressDialog();
            if (idorang != null) {
                database.getReference("profil").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            final User user = data.getValue(User.class);
                            if (user.getId().equals(mAuth.getCurrentUser().getUid())) {
                                database.getReference("chat").child("personal").child(idorang).child(user.getNama()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        lc = new ListChat();
                                        if (dataSnapshot.exists()) {
                                            lc.setDisplayName(user.getNama());
                                            lc.setAvatar("");
                                            lc.setStatus("personal");

                                        } else {
                                            lc.setDisplayName(user.getNama());
                                            lc.setAvatar("");
                                            lc.setStatus("personal");
                                            database.getReference("chat").child("personal").child(idorang).child(user.getNama()).push().setValue(lc);

                                        }
                                        if (!user.getUrlgambar().equals("")) {
                                            Glide.with(FragmentDetailProfile.this.getContext())
                                                    .using(new FirebaseImageLoader())
                                                    .load(mStorageRef.child(user.getUrlgambar()))
                                                    .dontAnimate()
                                                    .into(gambarprof);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                database.getReference("chat").child("personal").child(mAuth.getCurrentUser().getUid()).child(namaorang).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        lc = new ListChat();
                        if (dataSnapshot.exists()) {
                            lc.setDisplayName(namaorang);
                            lc.setAvatar("");
                            lc.setStatus("personal");
                        } else {
                            lc.setDisplayName(namaorang);
                            lc.setAvatar("");
                            lc.setStatus("personal");
                            database.getReference("chat").child("personal").child(mAuth.getCurrentUser().getUid()).child(namaorang).push().setValue(lc);
                        }
                        database.getReference("profil").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    User user = data.getValue(User.class);
                                    if (user.getId().equals(mAuth.getCurrentUser().getUid())) {
                                        Intent i = new Intent(FragmentDetailProfile.this.getContext(), PesanPersonal.class);
                                        i.putExtra("namauser", namaorang);
                                        i.putExtra("idorang", idorang);
                                        i.putExtra("status", lc.getStatus());
                                        i.putExtra("user", user.getNama());
                                        startActivity(i);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            } else {
                database.getReference("profil").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            User user = data.getValue(User.class);
                            if (user.getId().equals(mAuth.getCurrentUser().getUid())) {
                                Intent i = new Intent(FragmentDetailProfile.this.getContext(), HalamanUbahProfil.class)
                                        .putExtra("nama", user.getNama())
                                        .putExtra("status", user.getStatus())
                                        .putExtra("asal", user.getAsal())
                                        .putExtra("sd", user.getSd())
                                        .putExtra("smp", user.getSmp())
                                        .putExtra("sma", user.getSma())
                                        .putExtra("email", user.getEmail())
                                        .putExtra("kuliah", user.getKuliah())
                                        .putExtra("kerja", user.getKerja())
                                        .putExtra("telpon", user.getNotel())
                                        .putExtra("gambar", user.getUrlgambar());
                                hideProgressDialog();
                                startActivity(i);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(FragmentDetailProfile.this.getContext());
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentInfoProfile();
                case 1:
                    return new FragmentKontakProfile();

            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "INFO";
                case 1:
                    return "KONTAK";
            }
            return null;
        }
    }

}
