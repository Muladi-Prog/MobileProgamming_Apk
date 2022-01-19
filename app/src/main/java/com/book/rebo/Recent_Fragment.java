package com.book.rebo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    TextView one,two,three;
    Vector<Book> book = new Vector<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_, container, false);
        one = (TextView)view.findViewById(R.id.nameRecent1);
        two = (TextView)view.findViewById(R.id.nameRecent2);
        three = (TextView)view.findViewById(R.id.nameRecent3);
        Gson gson = new Gson();
        SharedPreferences prefs = this.getActivity().getSharedPreferences("RECENTLY", Context.MODE_PRIVATE);
        String json =  prefs.getString("DATARECENTLY",null);

        Type type = new TypeToken<Vector<Book>>(){}.getType();
        book = gson.fromJson(json,type);
            if(book.size() >= 2){
                one.setText(book.get(book.size()-1).getName());
            }else if(book.size() >=3){
                two.setText(book.get(book.size()-2).getName());
            }else if(book.size() >=4) {
                three.setText(book.get(book.size() - 3).getName());
            }else if(book.size()<2){
                one.setText("No Data");
            }




        return view;
    }
}