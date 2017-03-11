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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pattimura.wims.Adapter.AdapterDetailProfile;
import com.example.pattimura.wims.Model.BagianUser;
import com.example.pattimura.wims.Model.User;
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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import pub.devrel.easypermissions.EasyPermissions;

public class HalamanUbahProfil extends AppCompatActivity {
    private static final int RC_TAKE_PICTURE = 101;
    private static final int RC_STORAGE_PERMS = 102;
    private static final String KEY_FILE_URI = "key_file_uri";
    private static final String KEY_DOWNLOAD_URL = "key_download_url";
    ArrayList<BagianUser> isiUser = new ArrayList<>();
    ListView lv;
    AdapterDetailProfile adapter;
    private EditText ednama, edsd, edsmp, edsma, edkuliah, edkerja, ednohp;
    private TextView txtNama, txtSd, txtSmp, txtSma, txtKuliah, txtKerja, txtNohp;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog progressdialog, mProgressDialog;
    private ImageView gambarprof, ubahgambar;
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

        lv = (ListView) findViewById(R.id.listprofile);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        ubahgambar = (ImageView) findViewById(R.id.ubahGambar);
        gambarprof = (ImageView) findViewById(R.id.gambarProfile);

        Intent i = getIntent();
        Bundle b = i.getExtras();

        database.getReference("profil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    if (user.getId().equals(mAuth.getCurrentUser().getUid())) {
                        isiUser = new ArrayList<>();
                        isiUser.add(new BagianUser("Nama", user.getNama()));
                        isiUser.add(new BagianUser("Status", user.getStatus()));
                        isiUser.add(new BagianUser("Asal", user.getAsal()));
                        isiUser.add(new BagianUser("No Telepon", user.getNotel()));
                        isiUser.add(new BagianUser("Sekolah Dasar", user.getSd()));
                        isiUser.add(new BagianUser("Sekolah Menengah Pertama", user.getSmp()));
                        isiUser.add(new BagianUser("Sekolah Menengah Atas", user.getSma()));
                        isiUser.add(new BagianUser("Kuliah", user.getKuliah()));
                        isiUser.add(new BagianUser("Kerja", user.getKerja()));
                        adapter = new AdapterDetailProfile(isiUser, HalamanUbahProfil.this);
                        lv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        if (!user.getUrlgambar().equals("")) {
                            Glide.with(getApplicationContext())
                                    .using(new FirebaseImageLoader())
                                    .load(mStorageRef.child(user.getUrlgambar()))
                                    .dontAnimate()
                                    .into(gambarprof);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (b != null) {
            isiUser = new ArrayList<>();
            isiUser.add(new BagianUser("Nama", (String) b.get("nama")));
            isiUser.add(new BagianUser("Status", (String) b.get("status")));
            isiUser.add(new BagianUser("Asal", (String) b.get("asal")));
            isiUser.add(new BagianUser("No Telepon", (String) b.get("telpon")));
            isiUser.add(new BagianUser("Sekolah Dasar", (String) b.get("sd")));
            isiUser.add(new BagianUser("Sekolah Menengah Pertama", (String) b.get("smp")));
            isiUser.add(new BagianUser("Sekolah Menengah Atas", (String) b.get("sma")));
            isiUser.add(new BagianUser("Kuliah", (String) b.get("kuliah")));
            isiUser.add(new BagianUser("Kerja", (String) b.get("kerja")));
            adapter = new AdapterDetailProfile(isiUser, this);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            if (!((String) b.get("gambar")).equals("")) {
                Glide.with(getApplicationContext())
                        .using(new FirebaseImageLoader())
                        .load(mStorageRef.child((String) b.get("gambar")))
                        .dontAnimate()
                        .into(gambarprof);
            }
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != isiUser.size()) {
                    BagianUser bu = isiUser.get(position);
                    Intent i = new Intent(HalamanUbahProfil.this, UbahDetailProfile.class);
                    if (bu.getJudul().equals("No Telepon")) {
                        i.putExtra("judul", "notel");
                    } else if (bu.getJudul().equals("Sekolah Dasar")) {
                        i.putExtra("judul", "sd");
                    } else if (bu.getJudul().equals("Sekolah Menengah Pertama")) {
                        i.putExtra("judul", "smp");
                    } else if (bu.getJudul().equals("Sekolah Menengah Atas")) {
                        i.putExtra("judul", "sma");
                    } else {
                        i.putExtra("judul", bu.getJudul().toLowerCase());
                        //Toast.makeText(HalamanUbahProfil.this, bu.getJudul().toLowerCase(), Toast.LENGTH_SHORT).show();
                    }
                    i.putExtra("isi", bu.getIsi());
                    startActivity(i);
                }
            }
        });
        ubahgambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HalamanUbahProfil.this);
                builder.setPositiveButton("Ambil Gambar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        launchCamera();
                    }
                });
                builder.setNegativeButton("Pilih dari Galery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        pickdariGalery();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                if (mFileUri != null) {
                    uploadFromUri(mFileUri);
                } else {
                    Toast.makeText(this, "File tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
            } else {
                //Toast.makeText(this, "Taking picture failed.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(filePath);
                mFileUri = Uri.fromFile(file);
                uploadFromUri(mFileUri);
            } else {
                //Toast.makeText(this, "Taking picture failed.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFromUri(Uri fileUri) {
        final StorageReference photoRef = mStorageRef.child("images").child(fileUri.getLastPathSegment());
        showProgressDialog();
        photoRef.putFile(fileUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        @SuppressWarnings("VisibleForTests")
                        final String urlgambar = taskSnapshot.getMetadata().getPath();
                        Toast.makeText(HalamanUbahProfil.this, urlgambar, Toast.LENGTH_SHORT).show();
                        Glide.with(getApplicationContext())
                                .using(new FirebaseImageLoader())
                                .load(photoRef)
                                .dontAnimate()
                                .into(gambarprof);
                        //Picasso.with(getApplicationContext()).load(urlgambar).into(gambarprof);
                        hideProgressDialog();
                        Query query = database.getReference("profil").orderByChild("id").equalTo(mAuth.getCurrentUser().getUid());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        database.getReference("profil").child(data.getKey()).child("urlgambar").setValue(urlgambar);
                                    }
                                    Query query1 = database.getReference("chat").orderByChild("id").equalTo(mAuth.getCurrentUser().getUid());
                                    query1.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                    database.getReference("chat").child(data.getKey()).child("urlgambar").setValue(urlgambar);
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        Toast.makeText(HalamanUbahProfil.this, "Error: upload failed",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
