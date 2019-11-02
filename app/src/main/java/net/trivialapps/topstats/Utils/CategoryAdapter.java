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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private List<Pair<String, String>> top;
    private CategoryAdapter.OnItemClickedListener mCallback;

    public void setTopList(List<Pair<String, String>> ext_top) {
        this.top = ext_top;
    }

    public void setActivity(Activity activity) {
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (CategoryAdapter.OnItemClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString() + " must implement OnItemClickedListener");
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
    public CategoryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.grid_cell, parent, false);
        return new CategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.MyViewHolder holder, int position) {
        Pair<String, String> pair = top.get(position);
        holder.display(pair);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView img;
        private final TextView description;
        private Pair<String, String> currentPair;

        MyViewHolder(final View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.categoryImageViewList);
            description = itemView.findViewById(R.id.categoryDescription);

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
