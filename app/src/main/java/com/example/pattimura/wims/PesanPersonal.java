package com.example.pattimura.wims;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pattimura.wims.Adapter.AdapterChatGrup;
import com.example.pattimura.wims.Adapter.AdapterChatPersonal;
import com.example.pattimura.wims.Model.ChatGrup;
import com.example.pattimura.wims.Model.ChatPersonal;
import com.example.pattimura.wims.Model.ListChat;
import com.example.pattimura.wims.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class PesanPersonal extends AppCompatActivity {

    private EditText pesan;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private StorageReference mStorageRef;
    private ChatPersonal chat;
    private ChatGrup chat2;
    private ListView listpesan;
    private FirebaseUser user;
    private String urlgambar;
    private String nama, status, namauser;
    private AdapterChatPersonal customAdapter;
    private ArrayList<ChatPersonal> listchat = new ArrayList<>();
    private AdapterChatGrup customAdapter2;
    private ArrayList<ChatGrup> listchat2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_personal);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button fab = (Button) findViewById(R.id.btnSend);
        pesan = (EditText) findViewById(R.id.input);
        listpesan = (ListView) findViewById(R.id.ListChat);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        user = mAuth.getCurrentUser();

        Bundle b = getIntent().getExtras();
        final String id = user.getUid();


        if (b != null) {

            nama = (String) b.get("namauser");
            namauser = (String) b.get("user");
            final String songko = (String) b.get("status");
            status = songko;
        }
        //Toast.makeText(PesanPersonal.this, status, Toast.LENGTH_SHORT).show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarpm);
        TextView judul = (TextView) toolbar.findViewById(R.id.toolbar_title);
        judul.setText(nama);

        database.getReference("chat").child("grup").child(nama).child("pesan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listchat2.clear();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {

                        chat2 = data.getValue(ChatGrup.class);
                        //Toast.makeText(PesanPersonal.this, data.getValue().toString(), Toast.LENGTH_SHORT).show();
                        listchat2.add(chat2);
                        //Toast.makeText(PesanPersonal.this, listchat2.get(0).getNama(), Toast.LENGTH_SHORT).show();
                    }
                    customAdapter2 = new AdapterChatGrup(listchat2, getApplicationContext(), user);

                    //Toast.makeText(PesanPersonal.this, customAdapter2.getItem(0).getClass().getName(), Toast.LENGTH_SHORT).show();

                    listpesan.setAdapter(customAdapter2);
                    customAdapter2.notifyDataSetChanged();
                    listpesan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position != listchat2.size()) {
                                ChatGrup b = listchat2.get(position);
                                Intent i = new Intent(PesanPersonal.this, LandingPage.class);
                                i.putExtra("idorang", b.getId());
                                startActivity(i);
                            }
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pesan.toString().matches("")) {
                    if (status.equals("grup")) {

                        ChatGrup cg = new ChatGrup(namauser, pesan.getText().toString(), id, System.currentTimeMillis());
                        database.getReference("chat").child("grup").child(nama).child("pesan").push().setValue(cg);
                        pesan.setText("");
                    } else {
                        ChatPersonal cc = new ChatPersonal(pesan.getText().toString(), id);
                        database.getReference("chat").child("grup").child(nama).child("pesan").push().setValue(cc);
                        pesan.setText("");
                    }
                }
            }
        });
    }
}
