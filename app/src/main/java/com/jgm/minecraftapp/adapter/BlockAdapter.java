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
import java.util.List;

public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockHolder>{

    private List<Block> data;
    private int index;
    private BlocksListenerInterface blockListenerInterface;
    private Context context;



    public BlockAdapter(Context context, BlocksListenerInterface blockListener) {
        this.data = new ArrayList<Block>();
        this.blockListenerInterface = blockListener;
        this.context = context;
    }


    public void setData(List<Block> sdata) {
        this.data = sdata;
        notifyDataSetChanged();
    }

    public int getIndex() {
        return this.index;
    }

    @NonNull
    @Override
    public BlockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(context)
                .inflate(R.layout.layout_item, parent, false);

        BlockHolder bh = new BlockHolder(vista);

        return bh;
    }

    @Override
    public void onBindViewHolder(@NonNull BlockHolder holder, int position) {
        holder.BindHolder(data.get(position));
    }


    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class BlockHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView namespace;
        //private ImageView image;

        public BlockHolder(@NonNull View itemView) {
            super(itemView);
            this.namespace = itemView.findViewById(R.id.itemName);
            //this.image = itemView.findViewById(R.id.itemImage);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }

        public void BindHolder(Block block) {

            //Pulsación simple
            itemView.setOnClickListener(v -> blockListenerInterface.onBlockClickListener(block));
            namespace.setText(block.getNameSpace());
            //Cargar la imagen
            //Picasso.get().load(block.getImage()).into(image);

            itemView.setOnLongClickListener( (view) ->{
                index = getAdapterPosition();
                return false;
            });


        }

    }

    public interface BlocksListenerInterface {
        void onBlockClickListener(Block item);
    }
}
