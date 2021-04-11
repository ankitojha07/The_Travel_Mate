package com.knaas.thetravelmate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.knaas.thetravelmate.Activity.FrontActivity;
import com.knaas.thetravelmate.PlaceDetails.Agra;
import com.knaas.thetravelmate.PlaceDetails.Amritsar;
import com.knaas.thetravelmate.PlaceDetails.AndamanNikobar;
import com.knaas.thetravelmate.PlaceDetails.Darjeeling;
import com.knaas.thetravelmate.PlaceDetails.DetailsActivity;
import com.knaas.thetravelmate.PlaceDetails.Goa;
import com.knaas.thetravelmate.PlaceDetails.Kerala;
import com.knaas.thetravelmate.PlaceDetails.Kolkata;
import com.knaas.thetravelmate.PlaceDetails.Ladakh;
import com.knaas.thetravelmate.PlaceDetails.Mumbai;
import com.knaas.thetravelmate.PlaceDetails.Mysore;
import com.knaas.thetravelmate.PlaceDetails.NewDelhi;
import com.knaas.thetravelmate.PlaceDetails.Rajasthan;
import com.knaas.thetravelmate.PlaceDetails.Rishikesh;
import com.knaas.thetravelmate.PlaceDetails.Udaipur;
import com.knaas.thetravelmate.PlaceDetails.Varanasi;
import com.knaas.thetravelmate.R;
import com.knaas.thetravelmate.model.TopPlacesData;

import java.util.List;

public class TopPlacesAdapter extends RecyclerView.Adapter<com.knaas.thetravelmate.adapter.TopPlacesAdapter.TopPlacesViewHolder> {

    Context context;
    List<TopPlacesData> topPlacesDataList;

    public TopPlacesAdapter(Context context, List<TopPlacesData> topPlacesDataList) {
        this.context = context;
        this.topPlacesDataList = topPlacesDataList;
    }

    @NonNull
    @Override
    public TopPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.top_places_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new TopPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopPlacesViewHolder holder, int position) {

        holder.countryName.setText(topPlacesDataList.get(position).getCountryName());
        holder.placeName.setText(topPlacesDataList.get(position).getPlaceName());
        holder.price.setText(topPlacesDataList.get(position).getPrice());
        holder.placeImage.setImageResource(topPlacesDataList.get(position).getImageUrl());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(context, DetailsActivity.class);
//                context.startActivity(i);
                switch (position){
                    case 0:
                        Intent agra=new Intent(context, Agra.class);
                        context.startActivity(agra);
                        break;
                    case 1:
                        Intent newDelhi=new Intent(context, NewDelhi.class);
                        context.startActivity(newDelhi);
                        break;
                    case 2:
                        Intent mumbai=new Intent(context, Mumbai.class);
                        context.startActivity(mumbai);
                        break;
                    case 3:
                        Intent rajasthan=new Intent(context, Rajasthan.class);
                        context.startActivity(rajasthan);
                        break;
                    case 4:
                        Intent varanasi=new Intent(context, Varanasi.class);
                        context.startActivity(varanasi);
                        break;
                    case 5:
                        Intent amritsar=new Intent(context, Amritsar.class);
                        context.startActivity(amritsar);
                        break;

                   case 6:
                        Intent kerala=new Intent(context, Kerala.class);
                        context.startActivity(kerala);
                        break;

                   case 7:
                        Intent darjiling=new Intent(context, Darjeeling.class);
                        context.startActivity(darjiling);
                        break;

                   case 8:
                        Intent kolkata=new Intent(context, Kolkata.class);
                        context.startActivity(kolkata);
                        break;

                   case 9:
                        Intent mysore=new Intent(context, Mysore.class);
                        context.startActivity(mysore);
                        break;
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return topPlacesDataList.size();
    }

    public static final class TopPlacesViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView placeName, countryName, price;

        public TopPlacesViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
            price = itemView.findViewById(R.id.price);

        }
    }
}
