package com.book.rebo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private ImageView mImageView;
    private BroadcastReceiver MyReceiver = null;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private NavigationView navView;
    BottomNavigationView  bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDrawer();
        SharedPreferences prefs = getSharedPreferences("RECENTLY", Context.MODE_PRIVATE);
        SharedPreferences prefs2 = getSharedPreferences("BOOK", Context.MODE_PRIVATE);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()){
                    case R.id.home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.favourite:
                        selectedFragment = new FavouriteFragment();
                        break;
                    case R.id.genre:
                        selectedFragment = new GenreFragment();
                        break;
                    case R.id.recent:
                        selectedFragment = new Recent_Fragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment,selectedFragment).commit();
                return true;
            }
        });

    }



    public void getDrawer(){
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navView = findViewById(R.id.nav_view);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_logout:
//                delete all shared pref
                        SharedPreferences prefs = getSharedPreferences("isAuthourized", Context.MODE_PRIVATE);
                        prefs.edit().remove("Data").commit();
                        SharedPreferences prefs1 = getSharedPreferences("RECENTLY", Context.MODE_PRIVATE);
                        prefs1.edit().remove("DATARECENTLY").commit();
                        System.out.println("Logout Berhasil");
                        Intent backToLogin = new Intent(getApplicationContext(),QuickStartActivity.class);
                        Toast.makeText(MainActivity.this,"Log out Success!",Toast.LENGTH_SHORT).show();
                        startActivity(backToLogin);
                        return true;

                    case R.id.nav_account:
                            Intent gotoAcc = new Intent(getApplicationContext(),MyAccountActivity.class);
                            startActivity(gotoAcc);

                        break;
                    case R.id.nav_aboutus:

                        break;
                }
                return false;
            }
        });
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            System.out.println("Logout not");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}