package com.danteh.hearthking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danteh.hearthking.CardActivity;
import com.danteh.hearthking.Cards;
import com.danteh.hearthking.GlideApp;
import com.danteh.hearthking.R;

import java.util.ArrayList;
import java.util.List;

public class CardsListAdapter extends RecyclerView.Adapter<CardsListAdapter.MyHolder> implements Filterable {
    private final List<Cards> cardsList;
    private List<Cards> filteredcards;
    private Context context;

    public CardsListAdapter(List<Cards> cardsList, Context context) {
        this.cardsList = cardsList;
        this.filteredcards = cardsList;
        this.context = context;

    }

    @NonNull
    @Override
    public CardsListAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.cards_list, viewGroup, false);
        return new CardsListAdapter.MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardsListAdapter.MyHolder myHolder, int i) {
        GlideApp.with(context).load(filteredcards.get(i).getImage()).placeholder(R.drawable.shimmer).into(myHolder.cardimage);
    }

    @Override
    public int getItemCount() {
        if (filteredcards == null){
            Toast.makeText(context, "its 0", Toast.LENGTH_SHORT).show();
            return 0;}
        else
            return filteredcards.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        ImageView cardimage;
        RelativeLayout cardView;

        private MyHolder(@NonNull View itemView) {
            super(itemView);
            cardimage = itemView.findViewById(R.id.cardimage);

            cardimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent showCard = new Intent(context, CardActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("image_card",filteredcards.get(getAdapterPosition()).getImage());
                    bundle.putString("name_card",filteredcards.get(getAdapterPosition()).getName());
                    bundle.putString("set_card",filteredcards.get(getAdapterPosition()).getSet());
                    bundle.putString("class_card",filteredcards.get(getAdapterPosition()).getCardClass());
                    bundle.putString("type_card",filteredcards.get(getAdapterPosition()).getType());
                    bundle.putString("flavor_card",filteredcards.get(getAdapterPosition()).getFlavor());
                    bundle.putString("text_card",filteredcards.get(getAdapterPosition()).getText());
                    bundle.putString("rarity_card",filteredcards.get(getAdapterPosition()).getRarity());
                    bundle.putString("race_card",filteredcards.get(getAdapterPosition()).getRace());
                    bundle.putInt("attack_card",filteredcards.get(getAdapterPosition()).getAttack());

                    showCard.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    showCard.putExtra("card",bundle);
                    context.startActivity(showCard);
                }
            });
        }

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charSequence.length() == 0) {
                    filteredcards = cardsList;
                } else {
                    List<Cards> filteredList = new ArrayList<>();

                    for (Cards c : cardsList){
                        if(c.getText() != null )
                            if (c.getName().toLowerCase().contains(charString.toLowerCase()) || c.getText().toLowerCase().contains(charString.toLowerCase()) ) {
                                filteredList.add(c);
                            }
                        else {
                            if(c.getName().toLowerCase().contains(charString.toLowerCase()))
                                filteredList.add(c);
                            }
                    }
                        filteredcards = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredcards;
                filterResults.count = filteredcards.size();
                return filterResults;
            }
            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

                filteredcards = (ArrayList<Cards>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }
}
