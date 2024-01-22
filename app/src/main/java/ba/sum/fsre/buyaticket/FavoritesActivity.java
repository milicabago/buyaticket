package ba.sum.fsre.buyaticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import ba.sum.fsre.buyaticket.models.CardModel;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView favoritesRecyclerView;
    private FirebaseRecyclerAdapter<CardModel, HomeActivity.CardViewHolder> favoritesAdapter;
    private DatabaseReference favoritesRef;
    private DatabaseReference cartRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoritesRef = FirebaseDatabase.getInstance().getReference().child("Favorites");
        cartRef = FirebaseDatabase.getInstance().getReference().child("Cart");

        FirebaseRecyclerOptions<CardModel> options =
                new FirebaseRecyclerOptions.Builder<CardModel>()
                        .setQuery(favoritesRef, CardModel.class)
                        .build();

        favoritesAdapter = new FirebaseRecyclerAdapter<CardModel, HomeActivity.CardViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HomeActivity.CardViewHolder holder, int position, @NonNull CardModel model) {
                holder.cardDate.setText(model.getDate());
                holder.cardName.setText(model.getCardName());
                holder.cardLocation.setText(model.getLocation());
                holder.cardPrice.setText("Cijena: " + model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.cardImage);


                holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToCart(model);
                    }
                });
            }

            @NonNull
            @Override
            public HomeActivity.CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
                return new HomeActivity.CardViewHolder(view);
            }
        };

        favoritesRecyclerView.setAdapter(favoritesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        favoritesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        favoritesAdapter.stopListening();
    }

    private void addToCart(CardModel cardModel) {
        cartRef.push().setValue(cardModel);
        Toast.makeText(this, "Dodano u košaricu", Toast.LENGTH_SHORT).show();
    }

}
