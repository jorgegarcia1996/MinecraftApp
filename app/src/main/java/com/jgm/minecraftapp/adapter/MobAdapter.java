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
import com.jgm.minecraftapp.model.Mob;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MobAdapter extends RecyclerView.Adapter<MobAdapter.MobHolder>{

    private ArrayList<Mob> data;
    private int index;
    private Context context;

    //Constructor del adaptador
    public MobAdapter(Context context) {
        data = new ArrayList<>();
        this.context = context;
    }

    //Setter del array de datos
    public void setData(ArrayList<Mob> sdata) {
        data = sdata;
        notifyDataSetChanged();
    }

    //Getter del index
    public int getIndex() {
        return index;
    }

    //Inflar el layout del item en la vista
    @NonNull
    @Override
    public MobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context)
                .inflate(R.layout.layout_mobs_item, parent, false);

        MobHolder mh = new MobHolder(vista);

        return mh;
    }

    //Enlazar el holder al elemento de la vista
    @Override
    public void onBindViewHolder(@NonNull MobHolder holder, int position) {
        holder.BindHolder(data.get(position));
    }

    //Numero total de elementos
    @Override
    public int getItemCount() {
        return data.size();
    }

    //Clase del holder
    public class MobHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView name;
        private ImageView image;

        //Canstructor del Holder
        public MobHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.mobItemName);
            image = itemView.findViewById(R.id.mobItemImage);
        }

        //Mostrar los datos de cada elemento de la vista
        public void BindHolder(Mob mob) {
            name.setText(mob.getNombre());
            Picasso.get().load(mob.getImg()).into(image);
        }

        //Menú contextual no implementado
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }

}
