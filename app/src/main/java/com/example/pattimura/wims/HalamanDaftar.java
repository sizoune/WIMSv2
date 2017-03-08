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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.R.attr.password;

public class HalamanDaftar extends AppCompatActivity {
    EditText user, pass;
    private Button daftar;
    private ProgressDialog progressdialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar);

        progressdialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardaftar);
        TextView judul = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        judul.setText("DAFTAR");

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
                                        startActivity(new Intent(HalamanDaftar.this, LandingPage.class));
                                        finish();
                                    } else {
                                        progressdialog.dismiss();
                                        Toast.makeText(HalamanDaftar.this, "tidak dapat mendaftarkan user, tolong coba lagi !", Toast.LENGTH_SHORT).show();
                                        user.setText("");
                                        pass.setText("");
                                        return;
                                    }

                                }
                            });
                }
            }
        });
    }


}
