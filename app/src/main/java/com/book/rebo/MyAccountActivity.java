package com.book.rebo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.Vector;

public class MyAccountActivity extends AppCompatActivity {
    TextView username,number;

    Vector<User>userList = new Vector<>();
    Vector<String>bookList = new Vector<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
//        Gson gson = new Gson();
//        SharedPreferences prefs =getSharedPreferences("isAuthourized", Context.MODE_PRIVATE);
//        String json =  prefs.getString("Data",null);
//
//        SharedPreferences prefss =getSharedPreferences("BOOK", Context.MODE_PRIVATE);
//        String jsons =  prefss.getString("DATABOOK",null);
//        Type types= new TypeToken<Vector<String>>(){}.getType();
//        Type type= new TypeToken<Vector<User>>(){}.getType();
//        userList = gson.fromJson(json,type);
//        bookList = gson.fromJson(jsons,types);
//        System.out.println("listbook"+bookList);
//        username = (TextView) findViewById(R.id.userName);
//        number = (TextView) findViewById(R.id.number);
//        username.setText(userList.get(userList.size()-1).getUserName());
//        if(bookList!=null){
//            number.setText(""+bookList.size());
//
//        }







    }
}
