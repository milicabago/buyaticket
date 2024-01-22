package ba.sum.fsre.buyaticket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ba.sum.fsre.buyaticket.databinding.ActivityHomePageBinding;

import ba.sum.fsre.buyaticket.models.CardModel;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<CardModel, CardViewHolder> cardAdapterHolder;
    private LinearLayout sortingButtonsLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        recyclerView = findViewById(R.id.cardsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Button btnToday = findViewById(R.id.btnToday);
        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String todayDateString = dateFormat.format(calendar.getTime());

                filterByDate(todayDateString);
            }
        });

        Button btnTomorrow = findViewById(R.id.btnTomorrow);
        btnTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 1);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
                String tomorrowDateString = dateFormat.format(calendar.getTime());

                filterByDate(tomorrowDateString);
            }
        });

        Button btnConcert = findViewById(R.id.btnConcert);
        btnConcert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByEventType("Koncert");
            }
        });

        Button btnGame = findViewById(R.id.btnGame);
        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByEventType("Utakmica");
            }
        });

        Button btnMovie = findViewById(R.id.btnMovie);
        btnMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByEventType("Film");
            }
        });

        Button btnTheater = findViewById(R.id.btnTheater);
        btnTheater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterByEventType("Predstava");
            }
        });

        Query query = FirebaseDatabase.getInstance().getReference().child("CardModel");

        FirebaseRecyclerOptions<CardModel> options =
                new FirebaseRecyclerOptions.Builder<CardModel>()
                        .setQuery(query, CardModel.class)
                        .build();

        cardAdapterHolder = new FirebaseRecyclerAdapter<CardModel, CardViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CardViewHolder holder, int position, @NonNull CardModel model) {
                holder.cardDate.setText(model.getDate());
                holder.cardName.setText(model.getCardName());
                holder.cardLocation.setText(model.getLocation());
                holder.cardPrice.setText("Cijena: " + model.getPrice());
                Picasso.get().load(model.getImage()).into(holder.cardImage);
            }

            @NonNull
            @Override
            public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
                return new CardViewHolder(view);
            }
        };

    }

    private void filterByDate(String date) {
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("CardModel")
                .orderByChild("date")
                .equalTo(date);
    }

    private void filterByEventType(String eventType) {
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("CardModel")
                .orderByChild("cardName")
                .startAt(eventType)
                .endAt(eventType + "\uf8ff");

        updateAdapter(query);
    }

    private void updateAdapter(Query query) {
        FirebaseRecyclerOptions<CardModel> options =
                new FirebaseRecyclerOptions.Builder<CardModel>()
                        .setQuery(query, CardModel.class)
                        .build();

        cardAdapterHolder.updateOptions(options);
    }

    @Override
    protected void onStart() {
        super.onStart();
        cardAdapterHolder.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cardAdapterHolder.stopListening();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        ImageView cardImage;
        TextView cardDate;
        TextView cardName;
        TextView cardLocation;
        TextView cardPrice;

        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImage = itemView.findViewById(R.id.cardImage);
            cardDate = itemView.findViewById(R.id.cardDate);
            cardName = itemView.findViewById(R.id.cardName);
            cardLocation = itemView.findViewById(R.id.cardLocation);
            cardPrice = itemView.findViewById(R.id.cardPrice);
        }
    }

}
