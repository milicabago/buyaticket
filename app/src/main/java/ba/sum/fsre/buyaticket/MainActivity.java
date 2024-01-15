package ba.sum.fsre.buyaticket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;

import ba.sum.fsre.buyaticket.fragments.FavoritesFragment;
import ba.sum.fsre.buyaticket.fragments.HomeFragment;
import ba.sum.fsre.buyaticket.fragments.ProfileFragment;

import android.os.Bundle;

import ba.sum.fsre.buyaticket.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int menuItemId = item.getItemId();

                if (menuItemId == R.id.home) {
                    replaceFragment(new HomeFragment());
                    return true;
                }

                else if (menuItemId == R.id.favorites) {
                    replaceFragment(new FavoritesFragment());
                    return true;
                }

                else if (menuItemId == R.id.profile) {
                    replaceFragment(new ProfileFragment());
                    return true;
                }

                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}