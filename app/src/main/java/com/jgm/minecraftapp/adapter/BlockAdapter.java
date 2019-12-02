package com.jgm.minecraftapp.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jgm.minecraftapp.R;
import com.jgm.minecraftapp.model.Block;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockHolder>{

    private ArrayList<Block> data;
    private int index;
    private Context context;



    //Constructor del adaptador
    public BlockAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
    }


    //Setter del Array de datos
    public void setData(ArrayList<Block> sdata) {
        this.data = sdata;
        notifyDataSetChanged();
    }

    //Getter de index
    public int getIndex() {
        return this.index;
    }

    //Inflar el layout del item de la lista
    @NonNull
    @Override
    public BlockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context)
                .inflate(R.layout.layout_block_item, parent, false);

        BlockHolder bh = new BlockHolder(vista);

        return bh;
    }

    //Enlazar el holder al elemento de la lista
    @Override
    public void onBindViewHolder(@NonNull BlockHolder holder, int position) {
        holder.BindHolder(data.get(position));
    }

    //Numero total de elementos
    @Override
    public int getItemCount() {
        return this.data.size();
    }

    //Clase holder
    public class BlockHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView name;
        private ImageView image;

        //Constructor del holder
        public BlockHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.blockItemName);
            image = itemView.findViewById(R.id.blockItemImage);
        }

        //Mostrar los datos de cada objeto de la lista
        public void BindHolder(Block block) {
            name.setText(block.getNombre());
            Picasso.get().load(block.getImg()).into(image);
        }

        //Menu contextual no implementado
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }


    }
}
