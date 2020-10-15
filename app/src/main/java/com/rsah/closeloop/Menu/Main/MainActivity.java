package com.rsah.closeloop.Menu.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rsah.closeloop.Menu.Fragment.MainFragment;
import com.rsah.closeloop.Menu.Fragment.PaymentFragment;
import com.rsah.closeloop.Menu.Fragment.ReportFragment;
import com.rsah.closeloop.R;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new PaymentFragment());

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    //Bottom Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_payment:
                    PaymentFragment paymentFragment = new PaymentFragment();
                    loadFragment(paymentFragment);
                    return true;

                case R.id.navigation_report:
                    ReportFragment dateFragment = new ReportFragment();
                    loadFragment(dateFragment);
                    return true;

            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName());
        // transaction.addToBackStack(null);
        transaction.commit();
    }
}