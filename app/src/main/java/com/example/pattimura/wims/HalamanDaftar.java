package com.example.pattimura.wims;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pattimura.wims.Model.User;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import static android.R.attr.password;

public class HalamanDaftar extends AppCompatActivity {
    EditText user, pass;
    private Button daftar;
    private ProgressDialog progressdialog;
    private FirebaseAuth mAuth;
    private FirebaseUser mFuser;
    private FirebaseDatabase mDb;
    private StorageReference mStorage;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private User mUser;
    private String urlgambar = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar);

        progressdialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mDb = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance().getReference();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        TextView judul = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        judul.setText("Daftar");

        mUser = new User();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mFuser = firebaseAuth.getCurrentUser();
                if (mFuser != null) {
                    finish();
                    startActivity(new Intent(getApplicationContext(), LandingPage.class));
                }
            }
        };

        user = (EditText) findViewById(R.id.input_emaildaftar);
        pass = (EditText) findViewById(R.id.input_passworddaftar);
        daftar = (Button) findViewById(R.id.btn_daftardaftar);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().matches("")) {
                    Toast.makeText(HalamanDaftar.this, "Tolong masukkan alamat email anda", Toast.LENGTH_SHORT).show();
                    return;
                } else if (pass.getText().toString().matches("")) {
                    Toast.makeText(HalamanDaftar.this, "Tolong masukkan kata sandi email anda", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressdialog.setMessage("Mendaftarkan user...");
                    progressdialog.show();
                    String mail = user.getText().toString();
                    String passw = pass.getText().toString();
                    mAuth.createUserWithEmailAndPassword(mail, passw)
                            .addOnCompleteListener(HalamanDaftar.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        progressdialog.dismiss();
                                        finish();
                                        mFuser = mAuth.getCurrentUser();
                                        //String nama = mFuser.getDisplayName();
                                        String mail = mFuser.getEmail();
                                        String id = mFuser.getUid();
                                        mUser.setNama("Belum di isi");
                                        mUser.setEmail(mail);
                                        mUser.setId(id);
                                        mUser.setKerja("Belum di isi");
                                        mUser.setKuliah("Belum di isi");
                                        mUser.setNotel("Belum di isi");
                                        mUser.setSd("Belum di isi");
                                        mUser.setSmp("Belum di isi");
                                        mUser.setSma("Belum di isi");
                                        mUser.setAsal("Belum di isi");
                                        mUser.setStatus("Belum di isi");
                                        mUser.setUrlgambar("");
                                        mDb.getReference("profil").push().setValue(mUser);
                                        startActivity(new Intent(HalamanDaftar.this, LandingPage.class));

                                    } else {
                                        progressdialog.dismiss();
                                        Toast.makeText(HalamanDaftar.this, "Tidak dapat mendaftarkan user, tolong coba lagi !", Toast.LENGTH_SHORT).show();
                                        pass.setText("");
                                        return;
                                    }

                                }
                            });
                }
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


}
