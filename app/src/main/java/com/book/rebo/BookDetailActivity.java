package com.book.rebo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Vector;

public class BookDetailActivity extends AppCompatActivity {
    ImageView image;
    TextView name,isi;
    Vector<Book> book = new Vector<>();

    Vector<String> bookData = new Vector<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_book);
        getRecently(getIntent().getStringExtra("image_url"),getIntent().getStringExtra("name"),getIntent().getStringExtra("isi"));
        System.out.println("masuk detail"+getIntent().getStringExtra("name"));
        image = (ImageView)findViewById(R.id.image);
        name = (TextView)findViewById(R.id.name);
        isi  = (TextView)findViewById(R.id.isi);
        name.setText(getIntent().getStringExtra("name"));
        isi.setText(getIntent().getStringExtra("isi"));
        Glide.with(image.getContext()).load(getIntent().getStringExtra("image_url")).into(image);
    }

    public void getRecently(String image_url,String name,String isi){
        Gson gson = new Gson();
        SharedPreferences prefs = getSharedPreferences("RECENTLY", Context.MODE_PRIVATE);
        String json =  prefs.getString("DATARECENTLY",null);
        SharedPreferences prefs3 = getSharedPreferences("BOOK", Context.MODE_PRIVATE);
        String json3 =  prefs3.getString("DATABOOK",null);

        Type type = new TypeToken<Vector<Book>>(){}.getType();
        Type types = new TypeToken<Vector<String>>(){}.getType();
        bookData = gson.fromJson(json3,types);
        book = gson.fromJson(json,type);

            if(book!=null ||bookData!=null){


                if(book.size()>=3){
                    book.remove(0);
                }

                Boolean flag = false;
                for(Book item:book ){
                    System.out.println("book"+item.getName());
                    if(item.getName().equalsIgnoreCase(name)){

                        flag = true;
                    }
                }
                if(flag==false){
                    System.out.println("masuk book");
                    book.add(new Book(image_url,name,"d",0,isi,"a"));

                    SharedPreferences.Editor editor = prefs.edit();
                    Gson gson2 = new Gson();
                    String json2 = gson2.toJson(book);
                    editor.putString("DATARECENTLY",json2);
                    editor.apply();
                    if(bookData!=null){
                        SharedPreferences.Editor editors = prefs3.edit();
                        Gson gson3 = new Gson();
                        bookData.add("A");

                        String json4 = gson3.toJson(bookData);
                        editors.putString("DATABOOK",json4);
                        editors.apply();
                    }


                }


            }else{
                book.add(new Book(image_url,name,"d",0,isi,"a"));

                SharedPreferences.Editor editor = prefs.edit();
                Gson gson2 = new Gson();
                String json2 = gson2.toJson(book);
                editor.putString("DATARECENTLY",json2);
                editor.apply();

                SharedPreferences.Editor editors = prefs3.edit();
                Gson gson3 = new Gson();
                bookData.add("A");

                String json4 = gson3.toJson(bookData);
                editors.putString("DATABOOK",json4);
                editors.apply();


            }







    }

}
