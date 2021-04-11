package com.knaas.thetravelmate.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.knaas.thetravelmate.Activity.FrontActivity;
import com.knaas.thetravelmate.PlaceDetails.AndamanNikobar;
import com.knaas.thetravelmate.PlaceDetails.DetailsActivity;
import com.knaas.thetravelmate.PlaceDetails.Goa;
import com.knaas.thetravelmate.PlaceDetails.Ladakh;
import com.knaas.thetravelmate.PlaceDetails.Rishikesh;
import com.knaas.thetravelmate.PlaceDetails.Udaipur;
import com.knaas.thetravelmate.R;
import com.knaas.thetravelmate.model.RecentsData;

import java.util.List;

public class RecentsAdapter extends RecyclerView.Adapter<com.knaas.thetravelmate.adapter.RecentsAdapter.RecentsViewHolder> {

    Context context;
    List<RecentsData> recentsDataList;

    public RecentsAdapter(Context context, List<RecentsData> recentsDataList) {
        this.context = context;
        this.recentsDataList = recentsDataList;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.recents_row_item, parent, false);

        // here we create a recyclerview row item layout file
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, int position) {

        holder.countryName.setText(recentsDataList.get(position).getCountryName());
        holder.placeName.setText(recentsDataList.get(position).getPlaceName());
        holder.price.setText(recentsDataList.get(position).getPrice());
        holder.placeImage.setImageResource(recentsDataList.get(position).getImageUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i=new Intent(context, DetailsActivity.class);
//                context.startActivity(i);
                switch (position){
                    case 0:
                        Intent manali=new Intent(context, DetailsActivity.class);
                        context.startActivity(manali);
                        break;
                    case 1:
                        Intent udaipur=new Intent(context, Udaipur.class);
                        context.startActivity(udaipur);
                        break;
                    case 2:
                        Intent rishikesh=new Intent(context, Rishikesh.class);
                        context.startActivity(rishikesh);
                        break;
                    case 3:
                        Intent andaman=new Intent(context, AndamanNikobar.class);
                        context.startActivity(andaman);
                        break;
                    case 4:
                        Intent goa=new Intent(context, Goa.class);
                        context.startActivity(goa);
                        break;
                    case 5:
                        Intent ladakh=new Intent(context, Ladakh.class);
                        context.startActivity(ladakh);
                        break;

                    default:
                        Intent default1 = new Intent(context, FrontActivity.class);
                        context.startActivity(default1);
                        break;

                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return recentsDataList.size();
    }

    public static final class RecentsViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView placeName, countryName, price;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.place_image);
            placeName = itemView.findViewById(R.id.place_name);
            countryName = itemView.findViewById(R.id.country_name);
            price = itemView.findViewById(R.id.price);

        }
    }
}
