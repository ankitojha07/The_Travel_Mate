package com.knaas.thetravelmate.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.knaas.thetravelmate.R;
import com.knaas.thetravelmate.Userinformation;

import java.io.IOException;

public class EditProfile extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = EditProfile.class.getSimpleName();
    Button btnsave;
    private FirebaseAuth firebaseAuth;
    private TextView textViewemailname;
    private DatabaseReference databaseReference;
    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextPhoneNo;
    private ImageView profileImageView;
    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private StorageReference storageReference;

    public EditProfile() {
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextName = (EditText)findViewById(R.id.EditTextName);
        editTextSurname = (EditText)findViewById(R.id.EditTextSurname);
        editTextPhoneNo = (EditText)findViewById(R.id.EditTextPhoneNo);
        btnsave=(Button)findViewById(R.id.btnSaveButton);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        btnsave.setOnClickListener(this);
        textViewemailname=(TextView)findViewById(R.id.textViewEmailAdress);
        textViewemailname.setText(user.getEmail());
        profileImageView = findViewById(R.id.update_imageView);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent();
                profileIntent.setType("image/*");
                profileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(profileIntent, "Select Image."), PICK_IMAGE);
            }
        });
    }
    private void userInformation(){
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String phoneno = editTextPhoneNo.getText().toString().trim();
        Userinformation userinformation = new Userinformation(name,surname,phoneno);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userinformation);
        Toast.makeText(getApplicationContext(),"User information updated", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View view) {
        if (view==btnsave){
            if (imagePath == null) {

                Drawable drawable = this.getResources().getDrawable(R.drawable.defavatar);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.defavatar);
                // openSelectProfilePictureDialog();
                userInformation();
                // sendUserData();
                finish();
                startActivity(new Intent(EditProfile.this, Dashboard.class));
            }
            else {
                userInformation();
                sendUserData();
                finish();
                startActivity(new Intent(EditProfile.this, Dashboard.class));
            }
        }
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get "User UID" from Firebase > Authentification > Users.
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic"); //User id/Images/Profile Pic.jpg
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this, "Error: Uploading profile picture", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditProfile.this, "Profile picture uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openSelectProfilePictureDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        TextView title = new TextView(this);
        title.setText("Profile Picture");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);
        TextView msg = new TextView(this);
        msg.setText("Please select a profile picture \n Tap the sample avatar logo");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });
        new Dialog(getApplicationContext());
        alertDialog.show();
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);
    }
}