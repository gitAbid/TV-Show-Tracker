package com.marblelab.projectshowtracker.UI.Shows;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marblelab.projectshowtracker.API.TvMaze.TvMazeApiClient;
import com.marblelab.projectshowtracker.API.TvMaze.TvMazeServices;
import com.marblelab.projectshowtracker.Adapters.ShowsRecyclerViewAdapter;
import com.marblelab.projectshowtracker.Data.Shows.Shows;
import com.marblelab.projectshowtracker.Features.Feature;
import com.marblelab.projectshowtracker.R;
import com.marblelab.projectshowtracker.UtilityClass.Calculate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShowsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowsFragment extends Fragment implements ShowsInterface {

    private TvMazeServices mazeServices;
    private RecyclerView showRecyclerView;
    private ShowsRecyclerViewAdapter showsRecyclerViewAdapter;
   // private List<Shows> showsList;
    //private Feature feature=Feature.getInstace();
    private static Context context;
    private static int page=0;
    private int grid_number;
    private static boolean isLoading=false;
    private SmartRefreshLayout mSmartRefreshLayout;




    public ShowsFragment() {
        // Required empty public constructor
    }

    public static ShowsFragment newInstance() {
        ShowsFragment fragment = new ShowsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_shows, container, false);

        showRecyclerView=(RecyclerView)view.findViewById(R.id.rcShows);
        showRecyclerView.setHasFixedSize(true);

        mSmartRefreshLayout= (SmartRefreshLayout) view.findViewById(R.id.smartRefreshLayout);

        mSmartRefreshLayout.setEnableLoadmore(true);
        mSmartRefreshLayout.setOnRefreshListener(new SimpleMultiPurposeListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                Log.e("Load","More");
            }

            @Override
            public void onFooterPulling(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {
                super.onFooterPulling(footer, percent, offset, footerHeight, extendHeight);
                Log.e("Load","More");



            }

            @Override
            public void onFooterReleasing(RefreshFooter footer, float percent, int offset, int footerHeight, int extendHeight) {
                super.onFooterReleasing(footer, percent, offset, footerHeight, extendHeight);
                Log.e("Load","More");

            }

            @Override
            public void onFooterStartAnimator(RefreshFooter footer, int headHeight, int extendHeight) {
                super.onFooterStartAnimator(footer, headHeight, extendHeight);
                Log.e("Load","More");

            }

            @Override
            public void onFooterFinish(RefreshFooter footer, boolean success) {
                super.onFooterFinish(footer, success);
                Log.e("Load","More");

            }
        });

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshLoadmoreListener() {
                                                     @Override
                                                     public void onLoadmore(RefreshLayout refreshlayout) {
                                                         Log.e("WORKS","OnLoad");
                                                     }

                                                     @Override
                                                     public void onRefresh(RefreshLayout refreshlayout) {
                                                         Log.e("WORKS","ONREfresh");

                                                     }
                                                 });

                showRecyclerView.setLayoutManager(new GridLayoutManager(context, Calculate.getInstance(context, 153, 228).getGrid_number()));

        getShows();

        return view;
    }

    private void getShows() {
        Feature.SHOWS_LIST=new ArrayList<>();

        mazeServices=new TvMazeApiClient().getInstance().create(TvMazeServices.class);
        Call<List<Shows>> showListCall=mazeServices.getShows();
        showListCall.enqueue(new Callback<List<Shows>>() {
            @Override
            public void onResponse(Call<List<Shows>> call, Response<List<Shows>> response) {

                Feature.SHOWS_LIST=response.body();

                showsRecyclerViewAdapter=new ShowsRecyclerViewAdapter(context,getActivity(),Feature.SHOWS_LIST);
                showRecyclerView.setAdapter(showsRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<List<Shows>> call, Throwable t) {

                t.printStackTrace();
            }
        });

        for (int i = 0; i <20 ; i++) {
           try {
               updateShows();
           }catch (Exception e){
               e.printStackTrace();
               Log.i("Page",String.valueOf(i));
           }

        }
    }

    @Override
    public List<Shows> getAllShows() {


        return  null;
    }

    public void updateShows(){
        isLoading=true;
        Call<List<Shows>> updates=mazeServices.updateShowsList(++page);
        updates.enqueue(new Callback<List<Shows>>() {
            @Override
            public void onResponse(Call<List<Shows>> call, Response<List<Shows>> response) {
               try {
                   showsRecyclerViewAdapter.updateRecyclerView(response.body());
               }catch (Exception e){
                   e.printStackTrace();
                   Log.i("Page",String.valueOf(page));

               }
                isLoading=false;
            }

            @Override
            public void onFailure(Call<List<Shows>> call, Throwable t) {

                isLoading=false;
            }
        });

    }

    public void applyFilters(List<Shows> filteredShows) {
        if (!filteredShows.isEmpty()){
           showsRecyclerViewAdapter.applyFilter(filteredShows);
        }else {
            getShows();
        }
    }
}
