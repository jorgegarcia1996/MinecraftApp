package com.jgm.minecraftapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import com.squareup.picasso.Picasso;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.jgm.minecraftapp.R;
import com.jgm.minecraftapp.model.User;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView welcome;
    private ImageView dirt;

    private FirebaseAuth fbAuth;

    //Variable Storage de firebase storage
    private StorageReference storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fbAuth = FirebaseAuth.getInstance();

        //Coger la referencia de fireebase Storage
        storage = FirebaseStorage.getInstance().getReference();

        welcome = findViewById(R.id.mainWelcomeText);
        dirt = findViewById(R.id.dirtTest);

        Bundle bundle = getIntent().getExtras();
        User user = (User) bundle.getSerializable("userData");

        welcome.setText(user.toString());

        //Establecer la imagen en storage en un imageview
        storage.child("blocks/Dirt.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(dirt);
            }
        });


    }
    //Menú principal
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //Opciones del menú principal
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mainMenuLogOut:
                //Hacer el logout en firebase y la API
                fbAuth.signOut();

                //Volver a la pantalla de login
                setResult(RESULT_OK);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
