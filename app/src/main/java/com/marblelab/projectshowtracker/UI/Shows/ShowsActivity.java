package com.marblelab.projectshowtracker.UI.Shows;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.marblelab.projectshowtracker.Data.Shows.Image;
import com.marblelab.projectshowtracker.R;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.ToggleButtonGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.graphics.Color.TRANSPARENT;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class ShowsActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ShowsActivity";

    private DisplayMetrics mDisplayMetrics;
    //private ActivityMainBinding mBinding;
    private float mStartX;
    private float mStartY;
    private int mBottomY;
    private int mBottomX;

    private boolean mIsCancel;
    private float mBottomListStartY;
    private boolean resetBottomList;

    private FloatingActionButton fab;
    private View bottomListBackground;
    private View sheetTop;
    private static View reveal;
    private ImageView cancel;
    private ImageView apply_filters;
    private HorizontalScrollView scroll;
    private LinearLayout list;
    private Animator inneReveal;
    private MultiSelectToggleGroup mFilters;
    private HashMap<Integer,String> mGenresIds;
    private List<String> mFilterList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);
        setDisplayMetrics();
        fab=(FloatingActionButton)findViewById(R.id.fab);
        bottomListBackground=(View)findViewById(R.id.bottom_list_background);
        sheetTop=(View)findViewById(R.id.sheetTop);
        reveal=(View)findViewById(R.id.reveal);
        cancel=(ImageView)findViewById(R.id.cancel);
        apply_filters=(ImageView)findViewById(R.id.apply_filters);
        scroll=(HorizontalScrollView)findViewById(R.id.scroll);
        list=(LinearLayout)findViewById(R.id.list);
        mFilters=(MultiSelectToggleGroup) findViewById(R.id.filter_group);

        mGenresIds=new HashMap<>();
        mFilterList=new ArrayList<>();

        mGenresIds.put(R.id.Action,"Action");
        mGenresIds.put(R.id.Adventure,"Adventure");
        mGenresIds.put(R.id.Animation,"Animation");
        mGenresIds.put(R.id.Anime,"Anime");
        mGenresIds.put(R.id.Horror,"Horror");
        mGenresIds.put(R.id.Drama,"Drama");
        mGenresIds.put(R.id.Espionage,"Espionage");
        mGenresIds.put(R.id.Music,"Music");
        mGenresIds.put(R.id.Supernatural,"Supernatural");
        mGenresIds.put(R.id.Science_Fiction,"Science_Fiction");
        mGenresIds.put(R.id.Family,"Family");
        mGenresIds.put(R.id.Crime,"Crime");
        mGenresIds.put(R.id.Thriller,"Thriller");
        mGenresIds.put(R.id.Mystery,"Mystery");
        mGenresIds.put(R.id.Romance,"Romance");
        mGenresIds.put(R.id.Fantasy,"Fantasy");
        mGenresIds.put(R.id.Travel,"Travel");
        mGenresIds.put(R.id.Food,"Food");

        mFilters.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {
                mFilterList.clear();
                List<Integer> ids= new ArrayList<Integer>();
                ids.addAll(group.getCheckedIds());

                if (ids!=null){
                    for (int id:ids) {
                        if (!mFilterList.contains(mGenresIds.get(id))){
                            mFilterList.add(mGenresIds.get(id));
                        }
                    }
                }

                Log.i("Filters",mFilterList.toString());
            }
        });


        android.app.FragmentManager fragmentManager=getFragmentManager();
        android.app.FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        ShowsFragment showsFragment=ShowsFragment.newInstance();
        fragmentTransaction.replace(R.id.flShowsFragment,showsFragment);
        fragmentTransaction.commit();

    }

    public void animate(View view) {
        if (!mIsCancel) {
            if (mStartX == 0.0f) {
                mStartX = view.getX();
                mStartY = view.getY();

                mBottomX = getBottomFilterXPosition();
                mBottomY = getBottomFilterYPosition();

                mBottomListStartY = bottomListBackground.getY();
            }

            final int x = getFinalXPosition();
            final int y = getFinalYPosition();


            ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float v = (float) animation.getAnimatedValue();

                   fab.setX(
                            x + (mStartX - x - ((mStartX - x) * v))
                    );

                   fab.setY(
                            y + (mStartY - y - ((mStartY - y) * (v * v)))
                    );
                }
            });
            animator.addListener(new AnimatorListenerAdapter() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    removeFabBackground();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                                    fab.animate()
                                    .y(mBottomY)
                                    .setDuration(200)
                                    .start();

                        }
                    },50);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            cancel.setVisibility(VISIBLE);
                            cancel.setTranslationX(-(mBottomX - x));

                            cancel.animate()
                                    .translationXBy(mBottomX - x)
                                    .setDuration(200)
                                    .start();

                            fab.animate()
                                    .x(mBottomX)
                                    .setDuration(200)
                                    .start();

                            fab.animate()
                                    .x(mBottomX)
                                    .setDuration(200)
                                    .start();

                            sheetTop.setScaleY(0f);
                            sheetTop.setVisibility(VISIBLE);

                            sheetTop.animate()
                                    .scaleY(1f)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            scroll.setVisibility(VISIBLE);
                                        }
                                    })
                                    .setDuration(200)
                                    .start();
                        }
                    }, 200);

                    if (resetBottomList) {
                        Log.d(LOG_TAG, "onAnimationEnd: reset");
                        resetBottomListBackground();
                    }


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bottomListBackground.animate()
                                    .alpha(1f)
                                    .setDuration(500)
                                    .setListener(new AnimatorListenerAdapter() {
                                        @Override
                                        public void onAnimationEnd(Animator animation) {
                                            super.onAnimationEnd(animation);
                                            fab.setImageResource(R.drawable.cancel);
                                            fab.setVisibility(INVISIBLE);
                                            fab.setX(cancel.getX() - mDisplayMetrics.density * 4);
                                            fab.setY(getBottomFilterYPosition());
                                            apply_filters.setVisibility(VISIBLE);
                                        }
                                    })
                                    .start();
                        }
                    }, 400);

                    revealFilterSheet(y);
                }
            });

            animator.start();
        } else {
            fab.setImageResource(R.drawable.filter);
            fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.sheet)));
            mIsCancel = false;
        }
    }

    private void resetBottomListBackground() {
        resetBottomList = false;
        bottomListBackground.setVisibility(VISIBLE);
        Drawable d = bottomListBackground.getBackground();
        final GradientDrawable gd = (GradientDrawable) d;
        bottomListBackground.setAlpha(0f);
        gd.setCornerRadius(0f);


        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) bottomListBackground.getLayoutParams();
        params.width = -1;
        params.height = (int) (mDisplayMetrics.density * 64);
        bottomListBackground.setY(mBottomListStartY + mDisplayMetrics.density * 8);
        bottomListBackground.requestLayout();
    }

    private int getBottomFilterYPosition() {
        return (int) (
                apply_filters.getY()
                        + (mDisplayMetrics.heightPixels - getStatusBarHeight() - mDisplayMetrics.density * 64)
                        - mDisplayMetrics.density * 4);
    }

    private int getBottomFilterXPosition() {
        return (int) (
                apply_filters.getX()
                        + mDisplayMetrics.widthPixels / 2
                        - mDisplayMetrics.density * 4);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void removeFabBackground() {
        fab.setBackgroundTintList(ColorStateList.valueOf(TRANSPARENT));

        fab.setElevation(0f);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealFilterSheet(int y) {
        reveal.setVisibility(VISIBLE);

        Animator a = ViewAnimationUtils.createCircularReveal(
                reveal,
                mDisplayMetrics.widthPixels / 2,
                (int) (y - reveal.getY()) + getFabSize() / 2,
                getFabSize() / 2,
                reveal.getHeight() * .7f);
        a.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                list.setVisibility(VISIBLE);
            }
        });
        a.start();
    }

    public int getFinalXPosition() {
        return mDisplayMetrics.widthPixels / 2 - getFabSize() / 2;
    }

    public int getFinalYPosition() {
        int marginFromBottom = getFinalYPositionFromBottom();
        return mDisplayMetrics.heightPixels - marginFromBottom + getFabSize() / 2;
    }

    public void setDisplayMetrics() {
        mDisplayMetrics = getResources().getDisplayMetrics();
    }

    public int getFinalYPositionFromBottom() {
        return (int) (mDisplayMetrics.density * 250);
    }

    public int getFabSize() {
        return (int) (mDisplayMetrics.density * 56);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void acceptFilters(View view) {
        //TODO here get all the filter parameters and perform filter
        animateBackToOriginalPosition();

        Toast.makeText(getApplicationContext(),"Works",Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animateBackToOriginalPosition() {fab.setVisibility(VISIBLE);list.setVisibility(INVISIBLE);scroll.setVisibility(INVISIBLE);

        mIsCancel = true;
        final int x = getFinalXPosition();
        final int y = getFinalYPosition();

        apply_filters.setVisibility(INVISIBLE);
        cancel.setVisibility(INVISIBLE);

        final int startX = (int)fab.getX();
        final int startY = (int)fab.getY();
        sheetTop.setVisibility(INVISIBLE);
         inneReveal= ViewAnimationUtils.createCircularReveal(this.reveal,
                mDisplayMetrics.widthPixels / 2,
                (int) (y -this.reveal.getY()) + getFabSize() / 2, this.reveal.getHeight() * .5f,
                getFabSize() / 2);

        inneReveal.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                reveal.setVisibility(INVISIBLE);
                fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ShowsActivity.this, R.color.colorAccent)));
                fab.setElevation(mDisplayMetrics.density * 4);

            }
        });
        inneReveal.start();

        animateBottomSheet();

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();

                fab.setX(
                        x - (x - startX - ((x - startX) * v))
                );

                fab.setY(
                        y + (startY - y - ((startY - y) * (v * v)))
                );


            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                        fab.animate()
                        .rotationBy(360)
                        .setDuration(1000)
                        .start();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        returnFabToInitialPosition();
                        bottomListBackground.setVisibility(INVISIBLE);
                    }
                }, 1000);
            }
        });
        animator.start();
    }

    private void animateBottomSheet() {
        Drawable d =bottomListBackground.getBackground();
        final GradientDrawable gd = (GradientDrawable) d;


        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                bottomListBackground.getLayoutParams();

        final int startWidth = bottomListBackground.getWidth();
        final int startHeight = bottomListBackground.getHeight();
        final int startY = (int) bottomListBackground.getY();


        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();
                gd.setCornerRadius(mDisplayMetrics.density * 50 * v);

                int i = (int) (startWidth - (startWidth - getFabSize()) * v);
                params.width = i;
                params.height = (int) (startHeight - (startHeight - getFabSize()) * v);
                bottomListBackground.setY(getFinalYPosition() + (startY
                        - getFinalYPosition()) - ((startY - getFinalYPosition()) * v));

                bottomListBackground.requestLayout();
            }
        });
        animator.start();
    }

    private void returnFabToInitialPosition() {
        final int x = getFinalXPosition();
        final int y = getFinalYPosition();
        resetBottomList = true;
        fab.setImageResource(R.drawable.filter);
       fab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ShowsActivity.this, R.color.sheet)));
        mIsCancel=false;






        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float v = (float) animation.getAnimatedValue();

                fab.setX(
                        x + ((mStartX - x) * v)
                );

                fab.setY(
                        (float) (y + (mStartY - y) * (Math.pow(v, .5f)))
                );
            }
        });
        animator.start();
    }

    public int getStatusBarHeight() {
        return (int) (mDisplayMetrics.density * 24);
    }

}
