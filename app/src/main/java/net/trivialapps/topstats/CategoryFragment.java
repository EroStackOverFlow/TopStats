package net.trivialapps.topstats;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.trivialapps.topstats.Utils.CategoryAdapter;

import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private final CategoryAdapter adaptater = new CategoryAdapter();
    private List<Pair<String, String>> top = null;



    public CategoryFragment() {
        // Required empty public constructor
    }

    public static CategoryFragment newInstance() {
        return (new CategoryFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        adaptater.setTopList(top);

        final RecyclerView rv = view.findViewById(R.id.category_recycler_view);
        rv.setLayoutManager(new GridLayoutManager(getContext(),2));
        rv.setAdapter(adaptater);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        top = Arrays.asList(

                Pair.create(""+R.mipmap.category_anime_bd+"", context.getResources().getString(R.string.Category_animes_bd) ),
                Pair.create(""+R.mipmap.category_cinema_serie+"", context.getResources().getString(R.string.Category_cinema_series)),
                Pair.create(""+R.mipmap.category_finance+"", context.getResources().getString(R.string.Category_finance)),
                Pair.create(""+R.mipmap.category_it+"", context.getResources().getString(R.string.Category_IT)),
                Pair.create(""+R.mipmap.category_jeux_videos+"", context.getResources().getString(R.string.Category_jeux_video)),
                Pair.create(""+R.mipmap.category_music+"", context.getResources().getString(R.string.Category_music)),
                Pair.create(""+R.mipmap.category_religion+"", context.getResources().getString(R.string.Category_religion)),
                Pair.create(""+R.mipmap.category_sante+"", context.getResources().getString(R.string.Category_sante)),
                Pair.create(""+R.mipmap.category_sport+"", context.getResources().getString(R.string.Category_sport)),
                Pair.create(""+R.mipmap.category_universite+"", context.getResources().getString(R.string.Category_universite))
        );

        adaptater.setActivity(getActivity());

    }

}
