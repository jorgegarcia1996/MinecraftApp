package com.jgm.minecraftapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.jgm.minecraftapp.R;
import com.jgm.minecraftapp.adapter.BlockAdapter;
import com.jgm.minecraftapp.fragment.BlocksFragment;
import com.jgm.minecraftapp.fragment.HomeFragment;
import com.jgm.minecraftapp.fragment.MobsFragment;
import com.jgm.minecraftapp.model.Block;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private FirebaseAuth fbAuth;

    private BottomNavigationView bottomNav;
    private Fragment homeFragment = new HomeFragment();
    private Fragment blocksFragment = new BlocksFragment();
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
                .replace(R.id.mainContainerLayout, homeFragment)
                .commit();
        Log.i("RECYCLER", "Primer fragmento cargado");
        //Acciones del bottomNav
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.bottomMenuHome:
                        if (!isLoaded(homeFragment.getClass().getSimpleName())) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.mainContainerLayout, homeFragment)
                                    .commit();
                        }

                        break;

                    case R.id.bottomMenuBlocks:
                        if (!isLoaded(blocksFragment.getClass().getSimpleName())) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.mainContainerLayout, blocksFragment)
                                    .commit();
                        }
                        break;

                    case R.id.bottomMenuMobs:
                        if (!isLoaded(mobsFragment.getClass().getSimpleName())) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.mainContainerLayout, mobsFragment)
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
            case R.id.mainMenuProfile:
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtras(getIntent().getExtras());
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isLoaded(String fClass) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.mainContainerLayout);
        return (f != null) && (f.getClass().getSimpleName().equals(fClass));
    }
}
