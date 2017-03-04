package com.example.pattimura.wims;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LandingPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    TextView judul;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        judul = (TextView) toolbar.findViewById(R.id.toolbar_title);
        judul.setText("Pesan");
        mAuth = FirebaseAuth.getInstance();


        fragment = new PesanFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainframe, fragment);
        ft.commit();


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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {
            fragment = new PesanFragment();
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
        } else if (id == R.id.nav_keluar) {
            mAuth.signOut();
            startActivity(new Intent(LandingPage.this, HalamanAwal.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
