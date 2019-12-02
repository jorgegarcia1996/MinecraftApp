package com.jgm.minecraftapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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



    public BlockAdapter(Context context) {
        this.data = new ArrayList<>();
        this.context = context;
    }


    public void setData(ArrayList<Block> sdata) {
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
        Log.i("POSITION", "" + position);
    }


    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class BlockHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        private TextView namespace;
        private ImageView image;

        public BlockHolder(@NonNull View itemView) {
            super(itemView);
            namespace = itemView.findViewById(R.id.itemName);
            image = itemView.findViewById(R.id.itemImage);
        }

        public void BindHolder(Block block) {
            namespace.setText(block.getNameSpace());
            Log.i("IMAGE", "" + block.toString());
            Picasso.get().load(block.getImg()).into(image);



        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }


    }
}
