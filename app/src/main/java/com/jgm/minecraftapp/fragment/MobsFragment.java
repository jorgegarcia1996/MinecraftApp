package com.jgm.minecraftapp.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jgm.minecraftapp.R;
import com.jgm.minecraftapp.adapter.MobAdapter;
import com.jgm.minecraftapp.model.Mob;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MobsFragment extends Fragment {

    private FirebaseDatabase fbDb;

    private ArrayList<Mob> mobsList;
    private MobAdapter mobAdapter;
    private RecyclerView mobsRecycler;

    public MobsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragmento en el contenedor
        View mobsView = inflater.inflate(R.layout.fragment_mobs, container, false);

        //Adaptador
        mobAdapter = new MobAdapter(getActivity());

        //Recycler
        mobsRecycler = mobsView.findViewById(R.id.fragmentMobsRecycler);
        mobsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mobsRecycler.setAdapter(mobAdapter);

        //Inicializar el array
        mobsList = new ArrayList<>();

        //Coger los datos de la base de datos
        fbDb = FirebaseDatabase.getInstance();
        DatabaseReference mobsReference = fbDb.getReference("mobs/");
        mobsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for( DataSnapshot dSnap: dataSnapshot.getChildren()) {
                    Mob mob = dSnap.getValue(Mob.class);
                    mobsList.add(mob);
                }
                //Enviar los datos al adaptador
                mobAdapter.setData(mobsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("FIREBASE", databaseError.getMessage());
            }
        });

        //Mostra la vista
        return mobsView;
    }

}
