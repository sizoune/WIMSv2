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

public class HalamanUbahProfil extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks {
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

        gambarprof = (ImageView) findViewById(R.id.gambarProfile);
        ubahgambar = (ImageView) findViewById(R.id.ubahGambar);

        ednama = (EditText) findViewById(R.id.edNama);
        edsd = (EditText) findViewById(R.id.edSD);
        edsmp = (EditText) findViewById(R.id.edSMP);
        edsma = (EditText) findViewById(R.id.edSMA);
        edkuliah = (EditText) findViewById(R.id.edKuliah);
        edkerja = (EditText) findViewById(R.id.edKerja);
        ednohp = (EditText) findViewById(R.id.edNotel);

        disableInput(edsd);
        disableInput(ednama);
        disableInput(ednohp);
        disableInput(edsmp);
        disableInput(edsma);
        disableInput(edkuliah);
        disableInput(edkerja);

        txtNama = (TextView) findViewById(R.id.txtUbahNama);
        txtSd = (TextView) findViewById(R.id.txtUbahSD);
        txtSmp = (TextView) findViewById(R.id.txtUbahSMP);
        txtSma = (TextView) findViewById(R.id.txtUbahSMA);
        txtKuliah = (TextView) findViewById(R.id.txtUbahKuliah);
        txtKerja = (TextView) findViewById(R.id.txtUbahKerja);
        txtNohp = (TextView) findViewById(R.id.txtUbahnotel);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        if (savedInstanceState != null) {
            mFileUri = savedInstanceState.getParcelable(KEY_FILE_URI);
            mDownloadUrl = savedInstanceState.getParcelable(KEY_DOWNLOAD_URL);
        }

        mDownloadReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (MyDownloadService.ACTION_COMPLETED.equals(intent.getAction())) {
                    String path = intent.getStringExtra(MyDownloadService.EXTRA_DOWNLOAD_PATH);
                    long numBytes = intent.getLongExtra(MyDownloadService.EXTRA_BYTES_DOWNLOADED, 0);

                    // Alert success
                    showMessageDialog("Success", String.format(Locale.getDefault(),
                            "%d bytes downloaded from %s", numBytes, path));
                }

                if (MyDownloadService.ACTION_ERROR.equals(intent.getAction())) {
                    String path = intent.getStringExtra(MyDownloadService.EXTRA_DOWNLOAD_PATH);

                    // Alert failure
                    showMessageDialog("Error", String.format(Locale.getDefault(),
                            "Failed to download from %s", path));
                }
            }
        };

        Bundle b = getIntent().getExtras();
        String urlgambar = (String) b.get("gambar");
        if (!urlgambar.equals("")) {
            StorageReference photoRef = mStorageRef.child(urlgambar);
            Glide.with(getApplicationContext())
                    .using(new FirebaseImageLoader())
                    .load(photoRef)
                    .override(100,100)
                    .into(gambarprof);

        }
        ednama.setText((String) b.get("nama"));
        edsd.setText((String) b.get("sd"));
        edsmp.setText((String) b.get("smp"));
        edsma.setText((String) b.get("sma"));
        edkuliah.setText((String) b.get("kuliah"));
        edkerja.setText((String) b.get("kerja"));
        ednohp.setText((String) b.get("nohp"));

        ednama.setOnClickListener(this);
        edsd.setOnClickListener(this);
        edsmp.setOnClickListener(this);
        edsma.setOnClickListener(this);
        edkerja.setOnClickListener(this);
        edkuliah.setOnClickListener(this);
        ednohp.setOnClickListener(this);
        txtNama.setOnClickListener(this);
        txtSd.setOnClickListener(this);
        txtNohp.setOnClickListener(this);
        txtSmp.setOnClickListener(this);
        txtSma.setOnClickListener(this);
        txtKuliah.setOnClickListener(this);
        txtKerja.setOnClickListener(this);
        ubahgambar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == edsd || view == txtSd) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("SD");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Query query = database.getReference("profil").orderByChild("id").equalTo(mAuth.getCurrentUser().getUid());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    database.getReference("profil").child(data.getKey()).child("sd").setValue(input.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    edsd.setText(input.getText().toString());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else if (view == ednohp || view == txtNohp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("No HP");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Query query = database.getReference("profil").orderByChild("id").equalTo(mAuth.getCurrentUser().getUid());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    database.getReference("profil").child(data.getKey()).child("notel").setValue(input.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    ednohp.setText(input.getText().toString());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else if (view == ednama || view == txtNama) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Nama");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Query query = database.getReference("prpofil").orderByChild("id").equalTo(mAuth.getCurrentUser().getUid());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    database.getReference("profil").child(data.getKey()).child("nama").setValue(input.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    ednama.setText(input.getText().toString());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else if (view == edsmp || view == txtSmp) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("SMP");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Query query = database.getReference("profil").orderByChild("id").equalTo(mAuth.getCurrentUser().getUid());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    database.getReference("profil").child(data.getKey()).child("smp").setValue(input.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    edsmp.setText(input.getText().toString());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else if (view == edsma || view == txtSma) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("SMA");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Query query = database.getReference("profil").orderByChild("id").equalTo(mAuth.getCurrentUser().getUid());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    database.getReference("profil").child(data.getKey()).child("sma").setValue(input.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    edsma.setText(input.getText().toString());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        } else if (view == edkuliah || view == txtKuliah) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Universitas");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Query query = database.getReference("profil").orderByChild("id").equalTo(mAuth.getCurrentUser().getUid());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    database.getReference("profil").child(data.getKey()).child("kuliah").setValue(input.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    edkuliah.setText(input.getText().toString());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }else if (view == edkerja || view == txtKerja) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Kerja");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Query query = database.getReference("profil").orderByChild("id").equalTo(mAuth.getCurrentUser().getUid());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    database.getReference("profil").child(data.getKey()).child("kerja").setValue(input.getText().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    edkerja.setText(input.getText().toString());
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        else if (view == ubahgambar) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setPositiveButton("Take Photo", new DialogInterface.OnClickListener() {
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
    }

    public void disableInput(EditText ed) {
        ed.setInputType(InputType.TYPE_NULL);
        ed.setTextIsSelectable(true);
        ed.setKeyListener(null);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(mDownloadReceiver, MyDownloadService.getIntentFilter());
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mDownloadReceiver);
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
                        final String urlgambar = taskSnapshot.getMetadata().getPath();
                        Glide.with(getApplicationContext())
                                .using(new FirebaseImageLoader())
                                .load(photoRef)
                                .into(gambarprof);
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
