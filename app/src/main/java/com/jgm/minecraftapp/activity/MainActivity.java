package com.jgm.minecraftapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jgm.minecraftapp.fragment.BlocksFragment;
import com.jgm.minecraftapp.fragment.MobsFragment;
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

    private FirebaseAuth fbAuth;

    private BottomNavigationView bottomNav;

    private Fragment blocksFrgment = new BlocksFragment();
    private Fragment mobsFragment = new MobsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Editar Barra de accion
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.app_icon_round);

        //FB Auth
        fbAuth = FirebaseAuth.getInstance();

        //BottonNav
        bottomNav = findViewById(R.id.bottomNavigationMenu);

        //Cargar primer fragmento
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerLayout, new BlocksFragment())
                .commit();

        //Acciones del bottomNav
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.bottomMenuBlocks:
                        if (!isLoaded(blocksFrgment.getClass().getSimpleName())) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragmentContainerLayout, blocksFrgment)
                                    .commit();
                        }
                        break;

                    case R.id.bottomMenuMobs:
                        if (!isLoaded(mobsFragment.getClass().getSimpleName())) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.fragmentContainerLayout, mobsFragment)
                                    .commit();
                        }
                        break;
                }
                return true;
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

    public boolean isLoaded(String fClass) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerLayout);
        return (f != null) && (f.getClass().getSimpleName().equals(fClass));
    }
}
