package ba.sum.fsre.buyaticket.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ba.sum.fsre.buyaticket.R;
import ba.sum.fsre.buyaticket.adapters.CardAdapter;
import ba.sum.fsre.buyaticket.models.CardModel;

public class FavoritesFragment extends Fragment {

    private List<CardModel> favoritesList;
    private RecyclerView recyclerView;
    // Change this line:
    // private CardAdapter<CardModel> cardAdapter;
    // To this line:
    private CardAdapter cardAdapter;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favorites, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize list and adapter
        favoritesList = new ArrayList<>();
        // Change this line:
        // cardAdapter = new CardAdapter<>(favoritesList);
        // To this line:
        cardAdapter = new CardAdapter(favoritesList);
        recyclerView.setAdapter(cardAdapter);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance("https://buyaticket-92291-default-rtdb.europe-west1.firebasedatabase.app")
                .getReference("user_favorites").child("userId1"); // Assuming you have a node named "Favorites" in your database

        // Fetch favorite data from Firebase
        fetchFavoriteDataFromFirebase();

        return view;
    }

    private void fetchFavoriteDataFromFirebase() {
        // Fetch data from Firebase database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                favoritesList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Log the content of each DataSnapshot
                    Log.d("FavoritesFragment", "DataSnapshot: " + dataSnapshot.toString());

                    // Use getValue with a specific class (CardModel) to deserialize the data
                    CardModel favoriteCard = dataSnapshot.getValue(CardModel.class);
                    favoritesList.add(favoriteCard);
                }

                // Notify adapter that data has changed
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if there are any issues fetching data
                Log.e("FavoritesFragment", "Error fetching data from Firebase: " + error.getMessage());
            }
        });
    }
}
