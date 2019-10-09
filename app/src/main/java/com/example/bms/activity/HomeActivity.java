package com.example.bms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bms.R;
import com.example.bms.fragment.OccupancyByTVFragment;
import com.example.bms.fragment.OccupancyDetailFragment;
import com.example.bms.fragment.OccupancyIndustryFragment;
import com.example.bms.fragment.SummaryFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout mdrawerLayout;
    private NavigationView mNavigation;
    private ActionBarDrawerToggle toggle;
    private String token;

    boolean clickback = false; //untuk kembali klik dua kali

    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate: SavedIntence: " + savedInstanceState);
        setContentView(R.layout.navigation_drawer);

        //token = getIntent().getStringExtra("token");
        token = getIntent().getStringExtra("token");
        Log.e("TOKEN", "onCreate: " + token);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigation = findViewById(R.id.navigation_view);
        mdrawerLayout = findViewById(R.id.drawer_layout);

        mNavigation.setNavigationItemSelectedListener(this);

        navigationDrawer();

        //main layout
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new SummaryFragment(token))
                    .commit();
            getSupportActionBar().setTitle("Summary 4 TV");

            mNavigation.setCheckedItem(R.id.menu_summary);
            //end main layout
        }

    }

    //navigation drawer
    private void navigationDrawer() {

        toggle = new ActionBarDrawerToggle(HomeActivity.this, mdrawerLayout, toolbar,
                R.string.open, R.string.close);
        mdrawerLayout.setDrawerListener(toggle);
        //toolbar.setNavigationIcon(R.drawable.ic_account); //custome icon navigation
        toggle.syncState(); //use navigation icon burger
    }

    //implement method
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = (item.getItemId());

        switch (id) {
            case R.id.menu_summary:
                getSupportActionBar().setTitle("Summary 4 TV");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new SummaryFragment(token))
                        .commit();
                break;

            case R.id.menu_occupancyByTv:
                getSupportActionBar().setTitle("Occupancy By TV");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new OccupancyByTVFragment(token))
                        .commit();
                break;

            case R.id.menu_occupancyDetail:
                getSupportActionBar().setTitle("Occupancy Detail");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new OccupancyDetailFragment(token))
                        .commit();
                break;

            case R.id.menu_occupancy_industry:
                getSupportActionBar().setTitle("Occupancy Industry");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new OccupancyIndustryFragment(token))
                        .commit();
                break;

            case R.id.menu_logout:

                /*getSharedPreferences("TOKEN", 0)
                        .edit()
                        .clear()
                        .apply();*/

                Snackbar.make(findViewById(R.id.relativeId), R.string.main_exit_snackbar, Snackbar.LENGTH_SHORT)
                        .setAction(R.string.main_exit_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                token = null;
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                            }
                        })
                        .show();
        }

        // open or close the drawer if home button is pressed
        mdrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //close navDrawer
    @Override
    public void onBackPressed() {

        //start Press once againt to exit
        if (clickback == true) {
            super.onBackPressed();
            return;
        }
        clickback = true;
        Toast.makeText(getApplicationContext(), "Press once againt to exit", Toast.LENGTH_LONG).show();

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                clickback = false;
            }
        }, 2000);
        //end Press once againt to exit

        //start close navDrawer
        if (mdrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mdrawerLayout.closeDrawer(GravityCompat.START);
        }
//        else {
//            super.onBackPressed();
//        }

        //end close navDrawer

    }
}
