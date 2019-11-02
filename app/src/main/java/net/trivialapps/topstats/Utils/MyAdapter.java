package net.trivialapps.topstats.Utils;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import net.trivialapps.topstats.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<Pair<String, String>> top;
    private OnItemClickedListener mCallback;
    private int actuelle_position = 0;

    public void setTopList ( List<Pair<String, String>> ext_top){
        this.top = ext_top;
    }

    public void setActivity(Activity activity){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnItemClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnItemClickedListener");
        }
    }

    @Override
    public int getItemCount() {
        return top.size();

    }

    // Declare our interface that will be implemented by any container activity
    public interface OnItemClickedListener {
         void onItemClicked(int imgId);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cell, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Pair<String, String> pair = top.get(position);
        holder.display(pair);
        actuelle_position = position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView description;
        private Pair<String, String> currentPair;


        MyViewHolder(final View itemView) {
            super(itemView);

            img =  itemView.findViewById(R.id.imageViewList);
            description =  itemView.findViewById(R.id.description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallback.onItemClicked(Integer.parseInt(currentPair.first));

                }
            });
        }


        void display(Pair<String, String> pair) {
            currentPair = pair;
            img.setImageResource(Integer.parseInt(pair.first));

            description.setText(pair.second);


        }


    }


}
