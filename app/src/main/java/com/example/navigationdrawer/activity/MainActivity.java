package com.example.navigationdrawer.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.navigationdrawer.R;
import com.example.navigationdrawer.fragment.ProfileFragment;
import com.example.navigationdrawer.fragment.RatingsFragment;
import com.example.navigationdrawer.fragment.RentHouseFragment;
import com.example.navigationdrawer.fragment.ViewHousesFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        loadFragment(new ViewHousesFragment());
    }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.nav_bus){
            Log.d("LOG", "onNavigationItemSelected: Nav View House");
            drawerLayout.closeDrawer(GravityCompat.START);
            loadFragment(new RentHouseFragment());
        }
        if (id==R.id.nav_cycle){
            Log.d("LOG", "onNavigationItemSelected: Nav View House");
            drawerLayout.closeDrawer(GravityCompat.START);
            loadFragment(new ViewHousesFragment());
        }
        if (id==R.id.nav_plane){
            Log.d("LOG", "onNavigationItemSelected: Nav View House");
            drawerLayout.closeDrawer(GravityCompat.START);
            loadFragment(new RatingsFragment());
        }
        if (id == R.id.nav_share){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
        if(id == R.id.nav_profile){
            Log.d("LOG", "onNavigationItemSelected: Nav View House");
            drawerLayout.closeDrawer(GravityCompat.START);
            loadFragment(new ProfileFragment());

        }
        if(id == R.id.nav_logout){
            SharedPreferences sharedPref = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("uname", "");
            editor.apply();
            Intent intent = new Intent(this,MainPage.class);
            startActivity(intent);
            finish();
        }


        return true;
    }
    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }
}