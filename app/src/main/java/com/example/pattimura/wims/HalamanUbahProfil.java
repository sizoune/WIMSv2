package com.example.pattimura.wims;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import pub.devrel.easypermissions.EasyPermissions;

public class HalamanUbahProfil extends AppCompatActivity implements View.OnClickListener{
    private EditText ednama, edsd, edsmp, edsma, edkuliah, edkerja, ednohp;
    private TextView txtNama, txtSd, txtSmp, txtSma, txtKuliah, txtKerja, txtNohp;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog progressdialog, mProgressDialog;
    private ImageView gambarprof, ubahgambar;

    private static final int RC_TAKE_PICTURE = 101;
    private static final int RC_STORAGE_PERMS = 102;

    private static final String KEY_FILE_URI = "key_file_uri";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";

    private BroadcastReceiver mDownloadReceiver;

    private Uri mDownloadUrl = null;
    private Uri mFileUri = null;

    private StorageReference mStorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_ubah_profil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarubahp);
        TextView judul = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        judul.setText("Ubah Profil");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressdialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


    }

    @Override
    public void onClick(View view) {
    }

    public void disableInput(EditText ed) {
        ed.setInputType(InputType.TYPE_NULL);
        ed.setTextIsSelectable(true);
        ed.setKeyListener(null);
    }



    private void showMessageDialog(String title, String message) {
        AlertDialog ad = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .create();
        ad.show();
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
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

    @Override
    public void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        out.putParcelable(KEY_FILE_URI, mFileUri);
        out.putParcelable(KEY_DOWNLOAD_URL, mDownloadUrl);
    }

    private void launchCamera() {

        // Create intent
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Choose file storage location
        File file = new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString() + ".jpg");
        mFileUri = Uri.fromFile(file);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mFileUri);

        // Launch intent
        startActivityForResult(takePictureIntent, 0);
    }

    private void pickdariGalery() {
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 0) {
//            if (resultCode == RESULT_OK) {
//                if (mFileUri != null) {
//                    uploadFromUri(mFileUri);
//                } else {
//                    Toast.makeText(this, "File tidak ditemukan", Toast.LENGTH_SHORT).show();
//                }
//            } else {
//                //Toast.makeText(this, "Taking picture failed.", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String filePath = cursor.getString(columnIndex);
//                cursor.close();
//                File file = new File(filePath);
//                mFileUri = Uri.fromFile(file);
//                uploadFromUri(mFileUri);
//            } else {
//                //Toast.makeText(this, "Taking picture failed.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }

//    private void uploadFromUri(Uri fileUri) {
//        final StorageReference photoRef = mStorageRef.child("images").child(fileUri.getLastPathSegment());
//        showProgressDialog();
//        photoRef.putFile(fileUri)
//                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        final String urlgambar = taskSnapshot.getMetadata().getPath();
//                        Glide.with(getApplicationContext())
//                                .using(new FirebaseImageLoader())
//                                .load(photoRef)
//                                .into(gambarprof);
//                        hideProgressDialog();
//                        Query query = database.getReference("profil").orderByChild("id").equalTo(mAuth.getCurrentUser().getUid());
//                        query.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                if (dataSnapshot.exists()) {
//                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
//                                        database.getReference("profil").child(data.getKey()).child("urlgambar").setValue(urlgambar);
//                                    }
//                                    Query query1 = database.getReference("chat").orderByChild("id").equalTo(mAuth.getCurrentUser().getUid());
//                                    query1.addValueEventListener(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(DataSnapshot dataSnapshot) {
//                                            if (dataSnapshot.exists()) {
//                                                for (DataSnapshot data : dataSnapshot.getChildren()) {
//                                                    database.getReference("chat").child(data.getKey()).child("urlgambar").setValue(urlgambar);
//                                                }
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(DatabaseError databaseError) {
//
//                                        }
//                                    });
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                    }
//                })
//                .addOnFailureListener(this, new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        hideProgressDialog();
//                        Toast.makeText(HalamanUbahProfil.this, "Error: upload failed",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}
