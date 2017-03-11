package com.example.pattimura.wims;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pattimura.wims.Model.User;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.pattimura.wims.R.layout.nav_header_landing_page;

public class LandingPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    FragmentManager fm = getSupportFragmentManager();
    TextView judul, namaUser;
    private FirebaseAuth mAuth;
    private User mUser = new User();
    private FirebaseUser user;
    private FirebaseDatabase database;
    private ProgressDialog mProgressDialog;
    String idorang,namaorang;
    StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idorang=null;
        namaorang=null;

        setContentView(R.layout.activity_landing_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final View header = navigationView.getHeaderView(0);
        ImageView logout = (ImageView) header.findViewById(R.id.imageViewlogout);
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LogoutDialogFragment alertdFragment = new LogoutDialogFragment();
                // Show Alert DialogFragment
                alertdFragment.show(fm, "Alert Dialog Fragment");
            }
        });
        final ImageView gambarprof = (ImageView) header.findViewById(R.id.imageView2);
        judul = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        judul.setText("Pesan");
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        showProgressDialog();
        database.getReference("profil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Iterable<DataSnapshot> children =   dataSnapshot.getChildren();
                for (DataSnapshot isi : dataSnapshot.getChildren()) {
                    User mUser = isi.getValue(User.class);
                    if (mUser.getId().equals(mAuth.getCurrentUser().getUid())) {
                        namaUser = (TextView) header.findViewById(R.id.username);
                        //masih kada kawa wil, kd paham kenapa null tarus wkwk
                        if (mUser != null) {
                            if (mUser.getNama() == "Belum di isi") {
                                namaUser.setText(mUser.getEmail());
                            } else {
                                namaUser.setText(mUser.getNama());
                            }
                        }
                        if (!mUser.getUrlgambar().equals("")) {
                            Glide.with(getApplicationContext())
                                    .using(new FirebaseImageLoader())
                                    .load(mStorageRef.child(mUser.getUrlgambar()))
                                    .dontAnimate()
                                    .into(gambarprof);
                        }

                    }

                }
                hideProgressDialog();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Bundle b = getIntent().getExtras();
        if(b!=null){
            if(b.get("idorang")!=null){
                idorang=(String) b.get("idorang");
                namaorang=(String) b.get("namaorang");
            }
        }
        fragment = new FragmentDetailProfile();
        judul.setText("");
        if(idorang!=null){
            Bundle args = new Bundle();
            args.putString("idorang",idorang);
            args.putString("namaorang",namaorang);
            args.putString("namabener",mUser.getNama());
            fragment.setArguments(args);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, fragment);
        ft.commit();




        /*Bundle b = getIntent().getExtras();
        if (b != null) {
            mUser = new User();

            mUser.setNama((String) b.get("nama"));
            mUser.setNotel((String) b.get("nohp"));
            mUser.setAngkatan((String) b.get("angkatan"));
            mUser.setAsal((String) b.get("asal"));
            mUser.setUrlgambar((String) b.get("gambar"));


        }*/


//        Picasso.with(this)
//                .load(R.drawable.bgnavigasi)
//                .into(new Target() {
//                          @Override
//                          public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                              relativeLayout.setBackground(new BitmapDrawable(getApplicationContext().getResources(), bitmap));
//
//                          }
//
//                          @Override
//                          public void onBitmapFailed(Drawable errorDrawable) {
//                              Log.d("TAG", "FAILED");
//                          }
//
//                          @Override
//                          public void onPrepareLoad(Drawable placeHolderDrawable) {
//                              Log.d("TAG", "FAILED");
//                          }
//                      });
        //ImageView tes = (ImageView) navigationView.findViewById(R.id.bgnav);
        //Picasso.with(this).load(R.drawable.bgnavigasi).fit().centerCrop().into(tes);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        return super.onOptionsItemSelected(item);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {
            fragment = new FragmentDetailProfile();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainframe, fragment);
            judul.setText("Beranda");
            ft.commit();
        } else if (id == R.id.nav_pesan) {
            fragment = new PesanFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainframe, fragment);
            judul.setText("Pesan");
            ft.commit();
        } else if (id == R.id.nav_timeline) {
            fragment = new PesanFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainframe, fragment);
            judul.setText("Timeline");
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getNama() {
        return namaUser.getText().toString();
    }

}