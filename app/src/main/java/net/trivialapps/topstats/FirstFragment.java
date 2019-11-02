package net.trivialapps.topstats;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.trivialapps.topstats.Utils.MyAdapter;

import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */

//fragment qui gere laffichage de la liste des statistiques
public class FirstFragment extends Fragment {

    private final MyAdapter adaptater = new MyAdapter();
    private  List<Pair<String, String>> top = null;
    protected static int category = 0;

    public FirstFragment() {
        // Required empty public constructor
    }

    public static FirstFragment newInstance(int cat) {
        category = cat;
        return (new FirstFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        adaptater.setTopList(top);

        final RecyclerView rv = view.findViewById(R.id.recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adaptater);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        switch (category){
            case 0:
                top = Arrays.asList(
                        Pair.create(""+R.mipmap.anime1+"", context.getResources().getString(R.string.Top_100_des_meilleurs_animes_japonais)),
                        Pair.create(""+R.mipmap.anime2+"", context.getResources().getString(R.string.Les_meilleurs_Mangas_de_l_Année_2019)),
                        Pair.create(""+R.mipmap.anime3+"", context.getResources().getString(R.string.Les_meilleurs_Animes_de_l_Année_2019))
                );
                break;
            case 1:
                top = Arrays.asList(
                        Pair.create(""+R.mipmap.cine+"", context.getResources().getString(R.string.Liste_des_plus_gros_succès_du_box_office_mondial)),
                        Pair.create(""+R.mipmap.serie+"", context.getResources().getString(R.string.les_meilleures_series_du_moment)),
                        Pair.create(""+R.mipmap.top_10_acteurs_payes+"", context.getResources().getString(R.string.top_10_des_acteurs_les_mieux_payes))
                );
                break;
            case 2:
                top = Arrays.asList(
                        Pair.create(""+R.mipmap.entreprises+"", context.getResources().getString(R.string.Entreprise_les_plus_fortunees) ),
                        Pair.create(""+R.mipmap.plus_riche+"", context.getResources().getString(R.string.Les_plus_grosses_fortunes_de_la_planete)),
                        Pair.create(""+R.mipmap.pib+"", context.getResources().getString(R.string.Classement_des_pays_par_PIB)),
                        Pair.create(""+R.mipmap.millitaire+"", context.getResources().getString(R.string.Les_plus_grandes_puisssances_Millitaires))
                );
                break;
            case 3:
                top = Arrays.asList(
                        Pair.create(""+R.mipmap.it1+"", context.getResources().getString(R.string.Les_meilleurs_entreprises_IT)),
                        Pair.create(""+R.mipmap.it2+"", context.getResources().getString(R.string.les_meilleures_universités_mondiales_en_technologie))
                );
                break;
            case 4:
                top = Arrays.asList(
                        Pair.create(""+R.mipmap.game1+"", context.getResources().getString(R.string.Liste_des_jeux_vidéo_les_plus_vendus)),
                        Pair.create(""+R.mipmap.game2+"", context.getResources().getString(R.string.Top_25_des_jeux_massivement_multijoueurs)),
                        Pair.create(""+R.mipmap.game3+"", context.getResources().getString(R.string.Liste_des_consoles_de_jeux_vidéo_les_plus_vendues))
                );
                break;
            case 5:
                top = Arrays.asList(
                        Pair.create(""+R.mipmap.music+"", context.getResources().getString(R.string.Liste_des_albums_musicaux_les_plus_vendus)),
                        Pair.create(""+R.mipmap.music1+"", context.getResources().getString(R.string.Classement_Forbes_Les_Musiciens_Les_Mieux_Payés_En_2018))
                );
                break;
            case 6:
                top = Arrays.asList(
                        Pair.create(""+R.mipmap.religion1+"", context.getResources().getString(R.string.Les_religions_les_plus_populaires_au_monde)),
                        Pair.create(""+R.mipmap.religion2+"", context.getResources().getString(R.string.Liste_des_papes))
                );
                break;
            case 7:
                top = Arrays.asList(
                        Pair.create(""+R.mipmap.sante1+"", context.getResources().getString(R.string.classement_des_meilleurs_hôpitaux_du_monde)),
                        Pair.create(""+R.mipmap.sante2+"", context.getResources().getString(R.string.Les_10_principales_causes_de_mortalité))
                );
                break;
            case 8:
                top = Arrays.asList(
                        Pair.create(""+R.mipmap.fifa+"", context.getResources().getString(R.string.classement_fifa)),
                        Pair.create(""+R.mipmap.sport1+"", context.getResources().getString(R.string.sportifs_les_plus_riches))
                );
                break;
            case 9:
                top = Arrays.asList(
                        Pair.create(""+R.mipmap.univ+"", context.getResources().getString(R.string.classement_universite))
                );
                break;
            default:
                break;

        }



       adaptater.setActivity(getActivity());

    }
}
