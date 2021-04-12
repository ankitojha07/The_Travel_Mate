package com.knaas.thetravelmate.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.knaas.thetravelmate.ModelClass.Messages;
import com.knaas.thetravelmate.R;
import com.knaas.thetravelmate.adapter.MessagesAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    String ReciverImage,ReciverUID,ReciverName,senderUID;
    CircleImageView profileImage;
    TextView recievername;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    public static String sImage;
    public static String rImage;
    CardView sendbtn;
    EditText edmessage;
    String senderRoom,recieverRoom;
    RecyclerView messageAdapter;
    ArrayList<Messages> messagesArrayList;
    MessagesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        database=FirebaseDatabase.getInstance();
        firebaseAuth= FirebaseAuth.getInstance();
        ReciverName=getIntent().getStringExtra("name");
        ReciverImage=getIntent().getStringExtra("RecieverImage");
        ReciverUID=getIntent().getStringExtra("uid");

        messagesArrayList=new ArrayList<>();

        profileImage=findViewById(R.id.profile_image);
        recievername=findViewById(R.id.recievername);
        messageAdapter=findViewById(R.id.messageAdapter);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messageAdapter.setLayoutManager(linearLayoutManager);
        adapter=new MessagesAdapter(ChatActivity.this,messagesArrayList);
        messageAdapter.setAdapter(adapter);
        sendbtn=findViewById(R.id.send_btn);
        edmessage=findViewById(R.id.editMessage);


        Picasso.get().load(ReciverImage).into(profileImage);
        recievername.setText(""+ReciverName);
        senderUID=firebaseAuth.getUid();

        senderRoom=senderUID+ReciverUID;
        recieverRoom=ReciverUID+senderUID;
        DatabaseReference reference= database.getReference("user").child(firebaseAuth.getUid());
        DatabaseReference chatrefrence=database.getReference("chat").child(senderRoom).child("messages");
        chatrefrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Messages messages=dataSnapshot.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sImage= snapshot.child("imageUri").getValue().toString();
                rImage=ReciverImage;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message=edmessage.getText().toString();
                if (message.isEmpty())
                {
                    Toast.makeText(ChatActivity.this,"Please enter valid message",Toast.LENGTH_SHORT).show();
                    return;
                }
                edmessage.setText("");
                Date date=new Date();
                Messages messages=new Messages(message,senderUID,date.getTime());

                database=FirebaseDatabase.getInstance();
                database.getReference().child("chats")
                        .child(senderRoom)
                        .child("messages")
                        .push()
                        .setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats")
                                .child(recieverRoom)
                                .child("messages")
                                .push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });

                    }
                });

            }
        });

    }
}