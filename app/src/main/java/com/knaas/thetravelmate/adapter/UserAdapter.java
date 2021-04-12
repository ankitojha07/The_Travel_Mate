package com.knaas.thetravelmate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.knaas.thetravelmate.Activity.ChatActivity;
import com.knaas.thetravelmate.Activity.MainActivity;
import com.knaas.thetravelmate.ModelClass.Users;
import com.knaas.thetravelmate.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<com.knaas.thetravelmate.adapter.UserAdapter.Viewholder> {
    Context mainActivity;
    ArrayList<Users> usersArrayList;
    public UserAdapter(MainActivity mainActivity, ArrayList<Users> usersArrayList) {
        this.mainActivity=mainActivity;
        this.usersArrayList=usersArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.item_user_row,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Users users=usersArrayList.get(position);
        holder.user_name.setText(users.getName());
        holder.user_status.setText(users.getStatus());

        Picasso.get().load(users.getImageUri()).into(holder.user_image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( mainActivity, ChatActivity.class);
                intent.putExtra("name",users.getName());
                intent.putExtra("RecieverImage",users.getImageUri());
                intent.putExtra("uid",users.getUid());
                mainActivity.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        CircleImageView user_image;

        TextView user_name;
        TextView user_status;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            user_image=itemView.findViewById(R.id.user_image);
            user_name=itemView.findViewById(R.id.user_name);
            user_status=itemView.findViewById(R.id.user_status);

        }
    }
}
