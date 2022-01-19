package com.book.rebo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MyHomeAdapter extends FirebaseRecyclerAdapter<Book,MyHomeAdapter.HomeViewHolder> {

    Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyHomeAdapter(@NonNull FirebaseRecyclerOptions<Book> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull HomeViewHolder holder, int position, @NonNull Book model) {
        holder.book_genre.setText(model.getGenre());
        System.out.println("hasil"+model.getName());
        holder.book_name.setText(model.getName());
        holder.book_desc.setText(model.getDescription());
        Glide.with(holder.icon.getContext()).load(model.getImage_url()).into(holder.icon);
        holder.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.unLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("model"+ model.getIsFavourite());
                if(model.getIsFavourite() == 0){
//                    Toast.makeText(context, "switch to love", Toast.LENGTH_SHORT).show();
                    Map<String,Object> map = new HashMap<>();
                    map.put("isFavourite",1);
                    FirebaseDatabase.getInstance().getReference().child("image")
                            .child(getRef(holder.getAbsoluteAdapterPosition()).getKey()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            System.out.println("Success");
                            Toast.makeText(holder.unLove.getContext(), "Succes added to Favourite", Toast.LENGTH_SHORT).show();
                        }
                    });
                    holder.unLove.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                }else{

//                    Toast.makeText(context, "switch to unlove", Toast.LENGTH_SHORT).show();
                    holder.unLove.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    Map<String,Object> map = new HashMap<>();
                    map.put("isFavourite",0);
                    FirebaseDatabase.getInstance().getReference().child("image")
                            .child(getRef(holder.getAbsoluteAdapterPosition()).getKey()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    System.out.println("unsuccess");
                                    Toast.makeText(holder.unLove.getContext(), "UnFavourite", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("unsuccess1");
                            Toast.makeText(holder.unLove.getContext(), "UnFavourite", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_card_home,parent,false);
        return new HomeViewHolder(view);
    }


    public class HomeViewHolder extends RecyclerView.ViewHolder{
        private TextView book_name,book_genre,book_desc;
        private ImageButton unLove,love,icon;

        private Vector<Book> bookList = new Vector<>();
        private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            book_name = (TextView) itemView.findViewById(R.id.book_name);
            book_desc = (TextView) itemView.findViewById(R.id.book_description);
            book_genre = (TextView) itemView.findViewById(R.id.book_genre);
            icon = (ImageButton) itemView.findViewById(R.id.book_icon);
            unLove = (ImageButton) itemView.findViewById(R.id.unLove);


            System.out.println("asahsu");
        }
    }

}
