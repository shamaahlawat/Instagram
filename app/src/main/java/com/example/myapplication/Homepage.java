package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.Fragment.AddFragment;
import com.example.myapplication.Fragment.HeartFragment;
import com.example.myapplication.Fragment.HomeFragment;
import com.example.myapplication.Fragment.ProfileFragment;
import com.example.myapplication.Fragment.searchFragment;

public class Homepage extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment fragmentselected=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemselectedListener);

        Bundle intent=getIntent().getExtras();
        if(intent!=null){
            String publisher=intent.getString("publisherid");

            SharedPreferences.Editor editor=getSharedPreferences("PREFS",MODE_PRIVATE).edit();
            editor.putString("profileid",publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ProfileFragment()).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();
        }



    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemselectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            fragmentselected=new HomeFragment();
                            break;
                        case R.id.nav_add:
                            fragmentselected=new AddFragment();
                            startActivity(new Intent(Homepage.this,PostActivity.class));
                            break;
                        case R.id.nav_heart:
                            fragmentselected=new HeartFragment();
                            break;
                        case R.id.nav_person:
                            fragmentselected=new ProfileFragment();
                            break;
                        case R.id.nav_search:
                            fragmentselected=new searchFragment();
                            break;

                    }
                    if(fragmentselected!=null){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmentselected).commit();
                    }
                    return true;
                }
            };
}
