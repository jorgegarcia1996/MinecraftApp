package com.jgm.minecraftapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.jgm.minecraftapp.R;
import com.jgm.minecraftapp.model.User;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseStorage storage;

    private ImageView profileImage;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        storage = FirebaseStorage.getInstance();

        profileImage = findViewById(R.id.profilePicture);

        Bundle bundle = getIntent().getExtras();

        user = (User) bundle.getSerializable("userData");

        storage.getReference().child(user.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Picasso.get().load(R.drawable.profile).into(profileImage);
                Toast.makeText(getApplicationContext(), R.string.profile_load_iamge_fail, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
