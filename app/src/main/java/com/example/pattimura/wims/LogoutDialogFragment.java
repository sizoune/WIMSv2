package com.example.pattimura.wims;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.google.firebase.auth.FirebaseAuth;


public class LogoutDialogFragment extends DialogFragment {

    private FirebaseAuth mAuth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon

                // Set Dialog Title
                .setTitle("Keluar")
                // Set Dialog Message
                .setMessage("Apakah anda ingin keluar dari akun ini?")

                // Positive button
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        mAuth.signOut();
                        startActivity(new Intent(LogoutDialogFragment.this.getContext(), HalamanAwal.class));
                        //finish();
                    }
                })

                // Negative Button
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        // Do something else
                    }
                }).create();
    }
}
