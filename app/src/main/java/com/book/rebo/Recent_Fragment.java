package com.book.rebo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Recent_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Recent_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Recent_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Recent_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Recent_Fragment newInstance(String param1, String param2) {
        Recent_Fragment fragment = new Recent_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    Button one,two,three;
    Vector<Book> book = new Vector<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_, container, false);
        one = (Button)view.findViewById(R.id.nameRecent1);
        two = (Button)view.findViewById(R.id.nameRecent2);
        three = (Button)view.findViewById(R.id.nameRecent3);
        Gson gson = new Gson();
        SharedPreferences prefs = this.getActivity().getSharedPreferences("RECENTLY", Context.MODE_PRIVATE);
        String json =  prefs.getString("DATARECENTLY",null);

        Type type = new TypeToken<Vector<Book>>(){}.getType();
        book = gson.fromJson(json,type);
        if(book!=null){

            if(book.size() == 1){
                one.setText(book.get(0).getName());
                one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getInfoBook(book.get(0).getImage_url(),book.get(0).getName(),book.get(0).getIsi(),view);
                    }
                });
            }else if(book.size() ==2){
                one.setText(book.get(1).getName());
                one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getInfoBook(book.get(1).getImage_url(),book.get(1).getName(),book.get(1).getIsi(),view);
                    }
                });
                two.setText(book.get(0).getName());
                two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getInfoBook(book.get(0).getImage_url(),book.get(0).getName(),book.get(0).getIsi(),view);
                    }
                });
            }else if(book.size() ==3) {
                one.setText(book.get(2).getName());
                one.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getInfoBook(book.get(2).getImage_url(),book.get(2).getName(),book.get(2).getIsi(),view);
                    }
                });
                two.setText(book.get(1).getName());
                two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getInfoBook(book.get(1).getImage_url(),book.get(1).getName(),book.get(1).getIsi(),view);
                    }
                });
                three.setText(book.get(0).getName());
                three.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getInfoBook(book.get(0).getImage_url(),book.get(0).getName(),book.get(0).getIsi(),view);
                    }
                });
            }else if(book.size()<1){
                one.setText("No Data");
            }

        }





        return view;
    }

    public void getInfoBook(String image_url,String name,String isi,View view){
        Intent i = new Intent(view.getContext(),BookDetailActivity.class);
        i.putExtra("image_url",image_url);
        i.putExtra("name",name);
        i.putExtra("isi",isi);
        view.getContext().startActivity(i);
        System.out.println("startdetail");
    }
}