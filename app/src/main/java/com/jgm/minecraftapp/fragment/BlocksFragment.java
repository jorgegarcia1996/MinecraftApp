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
import com.jgm.minecraftapp.adapter.BlockAdapter;
import com.jgm.minecraftapp.model.Block;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlocksFragment extends Fragment {

    private FirebaseDatabase fbDb;

    private ArrayList<Block> blocksList;
    private BlockAdapter blockAdapter;
    private RecyclerView blocksRecycler;


    public BlocksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragmento en el container
        View blocksView = inflater.inflate(R.layout.fragment_blocks, container, false);

        //Adaptador
        blockAdapter = new BlockAdapter(getActivity());

        //Recycler
        blocksRecycler = blocksView.findViewById(R.id.fragmentBlocksRecycler);
        blocksRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        blocksRecycler.setAdapter(blockAdapter);

        //Inicializar el array
        blocksList = new ArrayList<>();

        //Recoger los datos de la base de datos
        fbDb = FirebaseDatabase.getInstance();
        DatabaseReference blocksRef = fbDb.getReference("bloques/");
        blocksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for( DataSnapshot dSnap: dataSnapshot.getChildren()) {
                        Block block = dSnap.getValue(Block.class);
                        blocksList.add(block);
                    }
                    //Enviar los datos al adaptador
                    blockAdapter.setData(blocksList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("FIREBASE", databaseError.getMessage()) ;

            }
        });

        //Mostrar la vista
        return blocksView;

    }

}
