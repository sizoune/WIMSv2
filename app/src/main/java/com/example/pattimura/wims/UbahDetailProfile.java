package com.example.pattimura.wims;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pattimura.wims.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UbahDetailProfile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_detail_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarubahp1);
        TextView judul = (TextView) toolbar.findViewById(R.id.toolbar_title1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Button ubah = (Button) findViewById(R.id.buttonUbah);

        Intent i = getIntent();
        final Bundle b = i.getExtras();
        final EditText ed = (EditText) findViewById(R.id.input_ubah);
        ed.setHint((String) b.get("judul"));
        if (b != null) {
            judul.setText("Ubah " + (String) b.get("judul"));
        }
        ed.setText((String) b.get("isi"));

        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getReference("profil").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            User user = data.getValue(User.class);
                            if (user.getId().equals(mAuth.getCurrentUser().getUid())) {
                                database.getReference("profil").child(data.getKey()).child((String) b.get("judul")).setValue(ed.getText().toString());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
