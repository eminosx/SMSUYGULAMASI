package com.example.smsuygulama.ui.grupolustur;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smsuygulama.GrupModel;
import com.example.smsuygulama.R;
import com.google.firestore.v1.AggregationResultOrBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GrupAdaptor extends RecyclerView.Adapter<GrupAdaptor.GrupViewHolder> {

    List<GrupModel> grupModelList;
    public GrupAdaptor(List<GrupModel> grupModelList){
        this.grupModelList = grupModelList;
    }

    @NonNull
    @Override
    public GrupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GrupViewHolder grupViewHolder = new GrupViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupolustur_gruplar, parent, false));
        return grupViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GrupViewHolder holder, int position) {
        GrupModel grupModel = grupModelList.get(position);
        holder.setData(grupModel);
    }

    @Override
    public int getItemCount() {
        return grupModelList.size();
    }

    public class GrupViewHolder extends RecyclerView.ViewHolder{
        ImageView grupImageView;
        TextView grupaditextview, grupaciklamatextview;
        public GrupViewHolder(View itemView){
            super(itemView);

            grupaditextview = itemView.findViewById(R.id.item_grupolustur_grupadi);
            grupaciklamatextview = itemView.findViewById(R.id.item_grupolustur_grupaciklama);
            grupImageView = itemView.findViewById(R.id.item_grupolustur_image);
        }

        public  void setData(GrupModel grupModel){
            grupaditextview.setText(grupModel.getName());
            grupaciklamatextview.setText(grupModel.getDescription());
            if (grupModel.getImage() != null){
                Picasso.get().load(grupModel.getImage()).into(grupImageView);
            }
        }
    }
}
