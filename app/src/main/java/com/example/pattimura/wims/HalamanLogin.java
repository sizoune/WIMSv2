package com.example.pattimura.wims;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HalamanLogin extends AppCompatActivity {
    private EditText user, pass;
    private Button masuk;
    private String tok;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarlogin);
        TextView judul = (TextView) toolbar.findViewById(R.id.toolbar_title);
        judul.setText("MASUK");
        TextView textView = (TextView) findViewById(R.id.textView3);
        SpannableString content = new SpannableString("lupa kata sandi?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

        mAuth = FirebaseAuth.getInstance();

        user = (EditText) findViewById(R.id.input_email);
        pass = (EditText) findViewById(R.id.input_password);
        masuk = (Button) findViewById(R.id.btn_login);

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!user.getText().equals("") && !pass.getText().equals("")) {
                    masukcuy(user.getText().toString(), pass.getText().toString());
                }
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent i = new Intent(HalamanLogin.this,LandingPage.class);
                    startActivity(i);
                    finish();
                }
            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void masukcuy(final String userr, final String Pass) {

        //Creating a string request
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://scripthink.com/wims/api/Login/create",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject token = new JSONObject(response);
                            JSONObject object = token.getJSONObject("data");
                            tok = object.getString("token");
                            mAuth.signInWithCustomToken(tok).addOnCompleteListener(HalamanLogin.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(HalamanLogin.this, "Authentication failed : "+task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent i = new Intent(HalamanLogin.this,LandingPage.class);
                                        startActivity(i);
                                        finish();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            Toast.makeText(HalamanLogin.this, "error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
//tempat response di dapatkan
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        NetworkResponse netres = error.networkResponse;
                        final int httpStatus = error.networkResponse.statusCode;
                        if (netres != null && netres.statusCode == 401) {
                            Toast.makeText(HalamanLogin.this, "invalid credentials", Toast.LENGTH_SHORT).show();
                        } else {
                            error.printStackTrace();
                            Toast.makeText(HalamanLogin.this, "erroring: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                try {
                    //Adding parameters to request
                    params.put("username",userr);
                    params.put("password",Pass);
                    //returning parameter
                    return params;
                } catch (Exception e) {
                    Toast.makeText(HalamanLogin.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    return params;
                }
            }


        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
