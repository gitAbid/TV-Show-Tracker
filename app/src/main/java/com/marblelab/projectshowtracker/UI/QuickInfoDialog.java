package com.marblelab.projectshowtracker.UI;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marblelab.projectshowtracker.Features.Feature;
import com.marblelab.projectshowtracker.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Abid Hasan on 8/1/2017.
 */

public class QuickInfoDialog extends Dialog implements View.OnLongClickListener {

    private Activity activity;
    private int position;
    private TextView mShowTitle,mPrimierYear,mSeasons,mSummary,mEpisodes,mAirDate,mAirTime,mGenres;
    private ImageView mTumbnail;
    private RatingBar mRatingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.quick_info_view_dialog_layout);

        mTumbnail=(ImageView)findViewById(R.id.ivTumbnail);
        mShowTitle=(TextView)findViewById(R.id.tvShowName);
        mPrimierYear=(TextView)findViewById(R.id.tvYear);
        mSeasons=(TextView)findViewById(R.id.tvSeasons);
        mEpisodes=(TextView)findViewById(R.id.tvEpisode);
        mAirDate=(TextView)findViewById(R.id.tvAirDate);
        mAirTime=(TextView)findViewById(R.id.tvAiredTime);
        mSummary=(TextView)findViewById(R.id.tvSummary);
        mGenres=(TextView)findViewById(R.id.tvGenres);
        mRatingBar=(RatingBar) findViewById(R.id.rtRating);

        Picasso.with(activity.getApplicationContext()).load(Feature.SHOWS_LIST.get(position).getImage().getMedium()).into(mTumbnail);
        mShowTitle.setText(Feature.SHOWS_LIST.get(position).getName());
        mSummary.setText(Feature.SHOWS_LIST.get(position).getSummary().replaceAll("<p>|</p>|<b>|</b>|<i>|</i>",""));
        mAirTime.setText("Time: "+Feature.SHOWS_LIST.get(position).getSchedule().getTime());
        mAirDate.setText(" Days: "+Feature.SHOWS_LIST.get(position).getSchedule().getDays().toString());
        mGenres.setText("Genres: "+Feature.SHOWS_LIST.get(position).getGenres().toString());
        mPrimierYear.setText("Premiered: "+Feature.SHOWS_LIST.get(position).getPremiered());
        mRatingBar.setRating(Feature.SHOWS_LIST.get(position).getRating().getAverage()/2);



    }

    public QuickInfoDialog(@NonNull Activity activity, int position) {
        super(activity);
        this.activity=activity;
        this.position=position;
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
