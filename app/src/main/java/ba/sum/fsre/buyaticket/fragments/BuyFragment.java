package ba.sum.fsre.buyaticket.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

public class BuyFragment extends Fragment {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private Button btnFinishPurchase;

    private DatabaseReference cartReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_checkout, container, false);

        recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        btnFinishPurchase = view.findViewById(R.id.btnFinishPurchase); // Update this line

        // Initialize Firebase
        cartReference = FirebaseDatabase.getInstance("https://buyaticket-92291-default-rtdb.europe-west1.firebasedatabase.app").getReference("user_cart").child("userId1");

        // Initialize RecyclerView and Adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cardAdapter = new CardAdapter();
        recyclerView.setAdapter(cardAdapter);

        // Set up a listener for the "Finish Purchase" button
        btnFinishPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement logic for finishing the purchase
            }
        });

        // Read data from Firebase and update the adapter
        readDataFromFirebase();

        return view;
    }

    private void readDataFromFirebase() {
        cartReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<CardModel> cartItems = new ArrayList<>();
                for (DataSnapshot cardSnapshot : dataSnapshot.getChildren()) {
                    CardModel card = cardSnapshot.getValue(CardModel.class);
                    if (card != null) {
                        cartItems.add(card);
                    }
                }
                cardAdapter.setCardList(cartItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle errors during data retrieval
                Toast.makeText(getActivity(), "Error reading data from Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
