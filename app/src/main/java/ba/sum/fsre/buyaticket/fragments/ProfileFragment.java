package ba.sum.fsre.buyaticket.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import ba.sum.fsre.buyaticket.HelloActivity;
import ba.sum.fsre.buyaticket.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        // Get reference to the logout button
        Button btnLogout = view.findViewById(R.id.btnLogout);

        // Set onClickListener for the logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the logout method
                logoutUser();
            }
        });

        return view;
    }

    private void logoutUser() {
        // Sign out the current user
        firebaseAuth.signOut();

        Intent i = new Intent(getActivity(), HelloActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        getActivity().finish();
    }
}