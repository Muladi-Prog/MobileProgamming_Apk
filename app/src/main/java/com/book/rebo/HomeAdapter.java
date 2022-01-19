//package com.book.rebo;
//
//import android.content.Context;
//import android.text.Layout;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.bumptech.glide.Glide;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.HashMap;
//import java.util.Vector;
//
//public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{
//
//    private Vector<Book> bookList;
//    private Context context;
//
//    public HomeAdapter(Vector<Book> bookList, Context context) {
//        this.bookList = bookList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.activity_card_home,parent,false);
//        return new HomeViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
//        holder.book_genre.setText(bookList.get(position).getGenre());
//        holder.book_name.setText(bookList.get(position).getName());
//        holder.book_desc.setText(bookList.get(position).getDescription());
//        Glide.with(context).load(bookList.get(position).getImage_url()).into(holder.icon);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return bookList.size();
//    }
//
//
//    public class HomeViewHolder extends RecyclerView.ViewHolder{
//        private TextView book_name,book_genre,book_desc;
//        private ImageButton unLove,love;
//        private ImageView icon;
//        private Vector<Book> bookList = new Vector<>();
//        private DatabaseReference root = FirebaseDatabase.getInstance().getReference();
//        public HomeViewHolder(@NonNull View itemView) {
//            super(itemView);
//            book_name = (TextView) itemView.findViewById(R.id.book_name);
////            System.out.println("namee==" + itemView.findViewById(R.id.DrinksName));
//            book_desc = (TextView) itemView.findViewById(R.id.book_description);
////            btnClick = (Button) itemView.findViewById((R.id.btnClickDrinks));
//            book_genre = (TextView) itemView.findViewById(R.id.book_genre);
//            icon = (ImageView) itemView.findViewById(R.id.book_icon);
//            unLove = (ImageButton) itemView.findViewById(R.id.unLove);
//            loadData();
//            unLove.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view) {
//                    int position = getAbsoluteAdapterPosition();
//                    if(bookList.get(position).getIsFavourite() == 0){
//                        Toast.makeText(context, "switch to love", Toast.LENGTH_SHORT).show();
//                        unLove.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
//                        HashMap hashMap = new HashMap();
//                        hashMap.put("isFavourite",1);
//                        root.child("s"+(position)).updateChildren(hashMap);
//                    }else{
//                        Toast.makeText(context, "switch to unlove", Toast.LENGTH_SHORT).show();
//                        unLove.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
//                        HashMap hashMap = new HashMap();
//                        hashMap.put("isFavourite",0);
//                        root.child("s"+(position)).updateChildren(hashMap);
//                    }
//
//                    Toast.makeText(context, ""+position, Toast.LENGTH_SHORT).show();
//
//                }
//            });
////            love = (ImageButton) itemView.findViewById(R.id.love);
//
//        }
//        public void loadData(){
//            root.addValueEventListener(new ValueEventListener() {
//
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for(DataSnapshot datasnapshot:snapshot.getChildren()){
//                        Book book =  datasnapshot.getValue(Book.class);
//                        System.out.println("auahgelahwkwkwk");
//                        System.out.println("anjing"+book.getIsFavourite());
//                        bookList.add(book);
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//
//
//    }
//
//
//}
