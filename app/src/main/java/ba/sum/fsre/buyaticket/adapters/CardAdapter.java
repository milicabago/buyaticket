package ba.sum.fsre.buyaticket.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import ba.sum.fsre.buyaticket.R;
import ba.sum.fsre.buyaticket.models.CardModel;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private List<CardModel> cardList;

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardModel card = cardList.get(position);
        holder.cardDate.setText(card.getDate());
        holder.cardName.setText(card.getCardName());
        holder.cardLocation.setText(card.getLocation());
        holder.cardPrice.setText("Cijena: " + card.getPrice());

        Picasso.get().load(card.getImage()).into(holder.cardImage);
    }

    @Override
    public int getItemCount() {
        return cardList != null ? cardList.size() : 0;
    }

    public void setCardList(List<CardModel> cardList) {
        this.cardList = cardList;
        notifyDataSetChanged();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        TextView cardDate;
        TextView cardName;
        TextView cardLocation;
        TextView cardPrice;

        CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardDate = itemView.findViewById(R.id.cardDate);
            cardName = itemView.findViewById(R.id.cardName);
            cardLocation = itemView.findViewById(R.id.cardLocation);
            cardPrice = itemView.findViewById(R.id.cardPrice);
        }
    }
}
