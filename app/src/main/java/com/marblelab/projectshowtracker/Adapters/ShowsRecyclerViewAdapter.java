package com.marblelab.projectshowtracker.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marblelab.projectshowtracker.Data.Shows.Shows;
import com.marblelab.projectshowtracker.Features.Feature;
import com.marblelab.projectshowtracker.R;
import com.marblelab.projectshowtracker.UI.QuickInfoDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abid Hasan on 7/29/2017.
 */

public class ShowsRecyclerViewAdapter extends RecyclerView.Adapter<ShowsRecyclerViewAdapter.ShowsViewHolder> {

    private Context context;
    private List<Shows> showsList;
    private Activity activity;

    public ShowsRecyclerViewAdapter(Context context, Activity activity, List<Shows> showsList) {
        this.context = context;
        this.showsList = showsList;
        this.activity=activity;
    }

    @Override
    public ShowsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShowsViewHolder(LayoutInflater.from(context).inflate(R.layout.show_card_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(ShowsRecyclerViewAdapter.ShowsViewHolder holder, final int position) {
        try {
            holder.mRating.setText(String.valueOf(Feature.SHOWS_LIST.get(position).getRating().getAverage()));
            holder.mTitle.setText(Feature.SHOWS_LIST.get(position).getName());
        }catch (Exception e){
            e.printStackTrace();
        }



        try {
            Log.i("Image",Feature.SHOWS_LIST.get(position).getImage().getMedium());
            Picasso.with(context).load(Feature.SHOWS_LIST.get(position).getImage().getMedium()).into(holder.mTumbnail);
            Log.i("Genres", Feature.SHOWS_LIST.get(position).getGenres().toString());
            Log.i("Schedule?DAYS", Feature.SHOWS_LIST.get(position).getSchedule().getDays().toString());
            Log.i("Schedule?TIME", Feature.SHOWS_LIST.get(position).getSchedule().getTime());
            Log.i("Schedule?Networks", Feature.SHOWS_LIST.get(position).getNetwork().getName());
        }catch (Exception e){
            e.printStackTrace();
        }


        //TODO implemting latest episode
        holder.mLatestEpisode.setText("Latest Episode ...");
        holder.mShowCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                QuickInfoDialog quickInfoDialog=new QuickInfoDialog(activity,position);
                quickInfoDialog.setCancelable(true);
                quickInfoDialog.getWindow();
                quickInfoDialog.show();
                return false;
            }
        });
    }

    public void updateRecyclerView(List<Shows> showsList){
        this.showsList.addAll(showsList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return showsList.size();
    }

    public void applyFilter(List<Shows> filteredShows) {
        if(!filteredShows.isEmpty()){
            showsList.clear();
            notifyDataSetChanged();
            showsList.addAll(filteredShows);
            notifyDataSetChanged();
        }else {
            showsList.addAll(Feature.SHOWS_LIST);
            notifyDataSetChanged();
        }

    }

    public class ShowsViewHolder extends RecyclerView.ViewHolder {
        private ImageView mTumbnail;
        private TextView mRating,mLatestEpisode,mTitle;
        private CardView mShowCard;
        public ShowsViewHolder(View itemView) {
            super(itemView);
            mTumbnail=(ImageView)itemView.findViewById(R.id.ivTumbnail);
            mLatestEpisode=(TextView)itemView.findViewById(R.id.tvLatestEpisode);
            mRating=(TextView)itemView.findViewById(R.id.tvRating);
            mTitle=(TextView)itemView.findViewById(R.id.tvTitle);
            mShowCard=(CardView)itemView.findViewById(R.id.cvShowCard);


        }

    }
}
