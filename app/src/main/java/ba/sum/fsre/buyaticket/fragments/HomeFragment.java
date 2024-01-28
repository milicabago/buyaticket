package ba.sum.fsre.buyaticket.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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


public class HomeFragment extends Fragment {

    private List<CardModel> cardList;
    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private DatabaseReference databaseReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_page, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.cardsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize list and adapter
        cardList = new ArrayList<>();
        cardAdapter = new CardAdapter(cardList);
        recyclerView.setAdapter(cardAdapter);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance("https://buyaticket-92291-default-rtdb.europe-west1.firebasedatabase.app").getReference("cards");

        // Fetch card data from Firebase
        fetchCardDataFromFirebase();

        return view;
    }

    private void fetchCardDataFromFirebase() {
        // Fetch data from Firebase database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cardList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Log the content of each DataSnapshot
                    Log.d("HomeFragment", "DataSnapshot: " + dataSnapshot.toString());

                    // Use getValue with a specific class (CardModel) to deserialize the data
                    CardModel card = dataSnapshot.getValue(CardModel.class);
                    cardList.add(card);
                }

                // Notify adapter that data has changed
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors if there are any issues fetching data
                Log.e("HomeFragment", "Error fetching data from Firebase: " + error.getMessage());
            }
        });
    }

}
