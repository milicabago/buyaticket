package ba.sum.fsre.buyaticket.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ba.sum.fsre.buyaticket.R;
import ba.sum.fsre.buyaticket.models.CardModel;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CardModel> dataList;

    public CardAdapter() {
        this.dataList = new ArrayList<>();
    }

    public CardAdapter(List<CardModel> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        final CardModel card = dataList.get(position);

        holder.cardDate.setText(card.getDate());
        holder.cardName.setText(card.getCardName());
        holder.cardLocation.setText(card.getLocation());
        holder.cardPrice.setText("Cijena: " + card.getPrice());

        Picasso.get().load(card.getImage()).into(holder.cardImage);

        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart(card);
            }
        });

        holder.btnAddToFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavorites(card);
            }
        });
    }

    private void addToCart(CardModel card) {
        DatabaseReference cartReference = FirebaseDatabase.getInstance("https://buyaticket-92291-default-rtdb.europe-west1.firebasedatabase.app").getReference("user_cart");
        cartReference.child("user_id").child(String.valueOf(card.getCardId())).setValue(card);
    }

    private void addToFavorites(CardModel card) {

        Log.d("CardAdapter", "Card ID before adding to favorites: " + card.getCardId());

        DatabaseReference favoritesReference = FirebaseDatabase.getInstance("https://buyaticket-92291-default-rtdb.europe-west1.firebasedatabase.app").getReference("user_favorites");

        // Check if card ID is not null
        if (card.getCardId() != null) {
            // Get a reference to the unique child node for the card under the user's favorites
            DatabaseReference userFavoritesReference = favoritesReference.child("userId1").child(card.getCardId());

            // Set the specific fields for the card
            userFavoritesReference.child("date").setValue(card.getDate());
            userFavoritesReference.child("cardName").setValue(card.getCardName());
            userFavoritesReference.child("location").setValue(card.getLocation());
            userFavoritesReference.child("image").setValue(card.getImage());
            userFavoritesReference.child("price").setValue(card.getPrice());
            // Add other fields as needed
        } else {
            // Handle the case where card ID is null
            // Log an error
            Log.e("CardAdapter", "Card ID is null when adding to favorites");
            // You can also show a message, throw an exception, or take other appropriate action
        }
    }


    public void setCardList(List<CardModel> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        TextView cardDate;
        TextView cardName;
        TextView cardLocation;
        TextView cardPrice;
        Button btnAddToCart;
        Button btnAddToFavorites;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardDate = itemView.findViewById(R.id.cardDate);
            cardName = itemView.findViewById(R.id.cardName);
            cardLocation = itemView.findViewById(R.id.cardLocation);
            cardPrice = itemView.findViewById(R.id.cardPrice);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            btnAddToFavorites = itemView.findViewById(R.id.btnAddToFavorites);
        }
    }
}
