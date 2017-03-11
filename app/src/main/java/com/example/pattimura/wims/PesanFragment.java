package com.example.pattimura.wims;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pattimura.wims.Adapter.AdapterListChat;
import com.example.pattimura.wims.Model.ListChat;
import com.example.pattimura.wims.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PesanFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PesanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PesanFragment extends Fragment {
    ArrayList<ListChat> daftarChat;
    ListChat lc,lcc;
    AdapterListChat adapter;
    ListView lv;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    ArrayList<String> dataGrup = new ArrayList<>();
    String coba;
    int cek;


    public PesanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        lc = new ListChat();
        final LandingPage activity = (LandingPage) getActivity();

        daftarChat = new ArrayList<>();
        final View v = inflater.inflate(R.layout.fragment_pesan, container, false);
        database.getReference("profil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        final User mUser = child.getValue(User.class);


                        if (mUser.getId().equals(mAuth.getCurrentUser().getUid())) {

                            if (!mUser.getSd().equals( "Belum di isi")) {

                                lcc = new ListChat(mUser.getSd(),"grup","");
                                lcc.setUser(activity.getNama());
                                daftarChat.add(lcc);
                                database.getReference("chat").child("grup").child(mUser.getSd()).orderByChild("displayName").equalTo(mUser.getSd()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //daftarChat.clear();
                                        if (dataSnapshot.exists()) {
                                            lc.setDisplayName(mUser.getSd());
                                            lc.setAvatar("");
                                            lc.setStatus("grup");
                                        } else {
                                            lc.setDisplayName(mUser.getSd());
                                            lc.setAvatar("");
                                            lc.setStatus("grup");
                                           // Toast.makeText(PesanFragment.this.getContext(), "sd masuk", Toast.LENGTH_SHORT).show();
                                            database.getReference("chat").child("grup").child(lc.getDisplayName()).push().setValue(lc);
                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            if (!mUser.getSmp().equals( "Belum di isi")) {

                                lcc = new ListChat(mUser.getSmp(),"grup","");
                                lcc.setUser(activity.getNama());
                                daftarChat.add(lcc);
                                database.getReference("chat").child("grup").child(mUser.getSmp()).orderByChild("displayName").equalTo(mUser.getSmp()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //daftarChat.clear();
                                        if (dataSnapshot.exists()) {

                                        } else {
                                            lc.setDisplayName(mUser.getSmp());
                                            lc.setAvatar("");
                                            lc.setStatus("grup");

                                            //Toast.makeText(PesanFragment.this.getContext(), "SMP masuk", Toast.LENGTH_SHORT).show();
                                            database.getReference("chat").child("grup").child(lc.getDisplayName()).push().setValue(lc);

                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            if (!mUser.getSma().equals( "Belum di isi")) {
                                lcc = new ListChat(mUser.getSma(),"grup","");
                                lcc.setUser(activity.getNama());
                                daftarChat.add(lcc);
                                database.getReference("chat").child("grup").child(mUser.getSma()).orderByChild("displayName").equalTo(mUser.getSma()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //daftarChat.clear();
                                        if (dataSnapshot.exists()) {

                                        } else {
                                            lc.setDisplayName(mUser.getSma());
                                            lc.setAvatar("");
                                            lc.setStatus("grup");

                                            //Toast.makeText(PesanFragment.this.getContext(), "sma masuk", Toast.LENGTH_SHORT).show();
                                            database.getReference("chat").child("grup").child(lc.getDisplayName()).push().setValue(lc);
                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            if (!mUser.getKerja().equals( "Belum di isi")) {
                                lcc = new ListChat(mUser.getKerja(),"grup","");
                                lcc.setUser(activity.getNama());
                                daftarChat.add(lcc);
                                database.getReference("chat").child("grup").child(mUser.getKerja()).orderByChild("displayName").equalTo(mUser.getKerja()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //daftarChat.clear();
                                        if (dataSnapshot.exists()) {
                                            lc.setDisplayName(mUser.getKerja());
                                            lc.setAvatar("");
                                            lc.setStatus("grup");

                                        } else {
                                            lc.setDisplayName(mUser.getKerja());
                                            lc.setAvatar("");
                                            lc.setStatus("grup");

                                            database.getReference("chat").child("grup").child(lc.getDisplayName()).push().setValue(lc);
                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                            if (!mUser.getKuliah().equals( "Belum di isi")) {
                                lcc = new ListChat(mUser.getKuliah(),"grup","");
                                lcc.setUser(activity.getNama());
                                daftarChat.add(lcc);
                                database.getReference("chat").child("grup").child(mUser.getKuliah()).orderByChild("displayName").equalTo(mUser.getKuliah()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        //daftarChat.clear();
                                        if (dataSnapshot.exists()) {
                                            lc.setDisplayName(mUser.getKuliah());
                                            lc.setAvatar("");
                                            lc.setStatus("grup");


                                        } else {
                                            lc.setDisplayName(mUser.getKuliah());
                                            lc.setAvatar("");
                                            lc.setStatus("grup");

                                            database.getReference("chat").child("grup").child(lc.getDisplayName()).push().setValue(lc);

                                        }


                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }


                            //AdapterListChat = new CustomAdapter(listchat,getApplicationContext(),user);
                            //listpesan.setAdapter(customAdapter);
                            //Toast.makeText(PesanFragment.this.getContext(), daftarChat.get(0).getDisplayName(), Toast.LENGTH_SHORT).show();


                        }
                        adapter = new AdapterListChat(daftarChat, PesanFragment.this.getContext());
                        lv = (ListView) v.findViewById(R.id.listPesan);
                        lv.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (position != daftarChat.size()) {
                                    ListChat b = daftarChat.get(position);
                                    Intent i = new Intent(PesanFragment.this.getContext(), PesanPersonal.class);
                                    i.putExtra("namauser", b.getDisplayName());
                                    i.putExtra("status", b.getStatus());
                                    i.putExtra("user", b.getUser());
                                    startActivity(i);
                                }
                            }
                        });
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        /*adapter = new AdapterListChat(daftarChat, PesanFragment.this.getContext());
        lv = (ListView) v.findViewById(R.id.listPesan);
        lv.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != daftarChat.size()) {
                    ListChat b = daftarChat.get(position);
                    Intent i = new Intent(PesanFragment.this.getContext(), PesanPersonal.class);
                    i.putExtra("namauser", b.getDisplayName());
                    startActivity(i);
                }
            }
        });*/
        return v;
    }


}
