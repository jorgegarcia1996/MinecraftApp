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
import com.jgm.minecraftapp.client.Client;
import com.jgm.minecraftapp.fragment.BlocksFragment;
import com.jgm.minecraftapp.fragment.HomeFragment;
import com.jgm.minecraftapp.fragment.MobsFragment;
import com.jgm.minecraftapp.model.Block;
import com.jgm.minecraftapp.service.BlockService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements BlockAdapter.BlocksListenerInterface {

    private FirebaseAuth fbAuth;
    private FirebaseDatabase fbDatabase;

    private BottomNavigationView bottomNav;
    private RecyclerView recycler;
    private Fragment homeFragment = new HomeFragment();
    private Fragment blocksFragment = new BlocksFragment();
    private Fragment mobsFragment = new MobsFragment();

    private String urlBase;
    private String urlBlocks;
    private BlockService blockService;
    private List<Block> blocksData;
    private BlockAdapter blockAdapter;

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

        fbDatabase = FirebaseDatabase.getInstance();

        urlBase = fbDatabase.getReference().toString();
        urlBlocks = urlBase + "/blocks/";

        //BottonNav
        bottomNav = findViewById(R.id.bottomNavigationMenu);

        blocksData = new ArrayList<>();
        blockAdapter = new BlockAdapter(this, new BlockAdapter.BlocksListenerInterface() {
            @Override
            public void onBlockClickListener(Block item) {
                Toast.makeText(getApplicationContext(), item.getNameSpace(), Toast.LENGTH_SHORT).show();
            }
        });

        //Recycler View

        Log.i("RECYCLER", "Enlazando la vista");
        recycler = findViewById(R.id.mainRecyclerLayout);

        Log.i("RECYCLER", "Estableciento el layout manager");
        recycler.setLayoutManager(new LinearLayoutManager(this));

        Log.i("RECYCLER", "Asignando el adaptador");
        recycler.setAdapter(blockAdapter);

        Log.i("RECYCLER", "Instanciando el servicio");
        blockService = Client.getService(urlBlocks);

        Log.i("RECYCLER", "Iniciando el recycler");
        registerForContextMenu(recycler);

        Log.i("RECYCLER", "Cargando los datos");
        loadBlocks();

        Log.i("RECYCLER", "Datos cargados");
        //Cargar primer fragmento
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainRecyclerLayout, homeFragment)
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
                                    .replace(R.id.mainRecyclerLayout, homeFragment)
                                    .commit();
                        }

                        break;

                    case R.id.bottomMenuBlocks:
                        if (!isLoaded(blocksFragment.getClass().getSimpleName())) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.mainRecyclerLayout, blocksFragment)
                                    .commit();
                        }
                        break;

                    case R.id.bottomMenuMobs:
                        if (!isLoaded(mobsFragment.getClass().getSimpleName())) {
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.mainRecyclerLayout, mobsFragment)
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

    private void loadBlocks() {

        //Una forma de acceder a los datos de la api de forma asíncrona
        blockService.getBlocks().enqueue(new Callback<List<Block>>() {
            @Override
            public void onResponse(Call<List<Block>> call, Response<List<Block>> response) {
                if (response.isSuccessful()) {
                    blocksData = response.body();
                    //Proceso para mostrar los datos
                    Log.i("APP", "Los bloques se han cargado");

                    //Notificar los cambios en los datos
                    blockAdapter.setData(blocksData);
                }
            }

            @Override
            public void onFailure(Call<List<Block>> call, Throwable t) {
                Log.i("APP", "Fallo al cargar los bloques");
            }
        });
    }

    public boolean isLoaded(String fClass) {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.mainRecyclerLayout);
        return (f != null) && (f.getClass().getSimpleName().equals(fClass));
    }

    @Override
    public void onBlockClickListener(Block item) {

    }
}
