package com.book.rebo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Vector;

public class HomeActivity extends AppCompatActivity {
    Vector<User> userList = new Vector<>();
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private MenuItem logOut;
    private NavigationView navView;
    private RecyclerView recylerView;
    private MyHomeAdapter adapter;

    DatabaseReference db ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getDrawer();

//    Recyler view
//        writeNewUser("data:image/jpeg;base64,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",
//                "malin","asasasas","sassa","sasa",1);
        recylerView = (RecyclerView)findViewById(R.id.recyclerHome);
//        recylerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(HomeActivity.this);
        recylerView.setLayoutManager(layoutManager);
        db = FirebaseDatabase.getInstance().getReference().child("image");
        FirebaseRecyclerOptions<Book> options =
                new FirebaseRecyclerOptions.Builder<Book>()
                        .setQuery(db, Book.class)
                        .build();
        db.keepSynced(true);
        System.out.println("option"+options);
        adapter = new MyHomeAdapter(options);

        recylerView.setAdapter(adapter);



    }

    @Override
    protected void onStart() {

        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {

        super.onStop();
        adapter.stopListening();
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
                        System.out.println("Logout Berhasil");
                        Intent backToLogin = new Intent(getApplicationContext(),QuickStartActivity.class);
                        Toast.makeText(HomeActivity.this,"Log out Success!",Toast.LENGTH_SHORT).show();
                        startActivity(backToLogin);
                        return true;
                }
                return false;
            }
        });
        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


//    public void writeNewUser(String image_url, String name, String description,String isi,String genre,int isFavourite) {
//        Book book = new Book(image_url,name,description,isFavourite,isi,genre);
//        db.setValue("s0");
//        db.child("s0").setValue(book);
//        homeBooklist.add(book);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            System.out.println("Logout not");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}



//        db.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    System.out.println("testing");
//                    Book book = dataSnapshot.getValue(Book.class);
//                    System.out.println("testing"+book.getIsFavourite());
//                    homeBooklist.add(book);
//                }
//            adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


//        SharedPreferences prefs = getSharedPreferences("isAuthourized", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        Gson gson2 = new Gson();
//        String json2 = gson2.toJson(homeBooklist);
//        editor.putString("Data",json2);
//        editor.apply();





//
//                String username = getIntent().getStringExtra("username");
//                String address = getIntent().getStringExtra("address");
//                String phoneNumber = getIntent().getStringExtra("phoneNumber");
//                String password = getIntent().getStringExtra("password");
//                String DOB = getIntent().getStringExtra("DOB");
//                //save
//                userList.add(new User(username,phoneNumber,password,"Male",DOB,address));
//                //SAVE
//                SharedPreferences prefs = getSharedPreferences("User SignIn",Context.MODE_PRIVATE);
//                System.out.println("b");
//                SharedPreferences.Editor editor = prefs.edit();
//                Gson gson = new Gson();
//                String json = gson.toJson(userList);
//                editor.putString("Data SignIn",json);
//                editor.apply();
////                Toast.makeText(context,"Saved",Toast.LENGTH_SHORT);
//
//
//
//
////        };/
////        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,new IntentFilter("order2myorder"));
//
////        System.out.println("c");
////        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,new IntentFilter("UserData"));
//        System.out.println("defgh");
////        //lOAD
//        SharedPreferences sharedPreferences = getSharedPreferences("User SignIn",Context.MODE_PRIVATE);
////
////        Gson gson = new Gson();
////
//        String jsons =  sharedPreferences.getString("Data SignIn",null);
//        Type type = new TypeToken<Vector<User>>(){}.getType();
////        Type type = new TypeToken<ArrayList<Myorder>>(){}.getType();
//        userList = gson.fromJson(json,type);
//        for(User data: userList){
//            System.out.println("Username = "+data.getUserName());

//        }
