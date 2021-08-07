package com.sundram.developertest.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sundram.developertest.R;
import com.sundram.developertest.datamodel.Movie;
import com.sundram.developertest.utils.ConstantUtils;

import java.util.List;
import java.util.Map;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> movieList;
    private Context context;
    Map<String, Integer> colorsMap;


    public void setData(Context context, List<Movie> movieList, Map<String, Integer> colors) {
        this.context=context;
        this.movieList = movieList;
        this.colorsMap =colors;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movie_item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_movie_item,parent,false);
        return new ViewHolder(movie_item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {

            String[] movieTitleArr = movieList.get(position).getOriginal_title().replaceAll("[^a-zA-Z0-9\\s]", "").split(" ");

            for(String colorName :movieTitleArr){
                if (colorsMap.containsKey(colorName.toLowerCase().replace(",", ""))){
                    holder.container.setBackgroundColor(colorsMap.get(colorName.toLowerCase()));
                    break;
                }
            }

            holder.title_tv.setText(movieList.get(position).getOriginal_title());
            holder.year_tv.setText(movieList.get(position).getRelease_date());
            holder.runtime_tv.setText(movieList.get(position).getVote_average());
            String image_path = ConstantUtils.IMAGE_URL + movieList.get(position).getPoster_path();
            Glide.with(context)
                    .load(image_path)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.movie_image_view);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView title_tv,runtime_tv,year_tv;
         ImageView movie_image_view;
        ConstraintLayout container;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title_tv=itemView.findViewById(R.id.title);
            runtime_tv = itemView.findViewById(R.id.runtime);
            year_tv = itemView.findViewById(R.id.year);
            movie_image_view = itemView.findViewById(R.id.image);
            container = itemView.findViewById(R.id.container);
        }
    }
}
